package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.reporting.core.analytics.PublishStatistics;
import com.apple.iossystems.smp.reporting.core.analytics.Statistics;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledNotification;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.core.hubble.HubblePublisher;
import com.apple.iossystems.smp.reporting.core.messaging.BacklogEventPublisher;
import com.apple.iossystems.smp.reporting.core.timer.StopWatch;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Toch
 */
class PublishTaskHandler implements EventTaskHandler
{
    private static final Logger LOGGER = Logger.getLogger(PublishTaskHandler.class);

    private final EventType publishEventType;
    private final PublishMetric publishMetric;
    private final IReporterPublishService reportsPublishService;
    private final IReporterPublishService auditPublishService;

    private final Statistics statistics = Statistics.getInstance();
    private final PublishStatistics publishStatistics = PublishStatistics.getInstance();
    private final StopWatch stopWatch = StopWatch.getInstance();

    private final BacklogEventPublisher backlogEventPublisher = BacklogEventPublisher.getInstance();
    private final HubblePublisher hubblePublisher = HubblePublisher.getInstance();

    private BlockingQueue<EventRecord> reportsQueue = new LinkedBlockingQueue<>();

    public PublishTaskHandler(EventType publishEventType, PublishMetric publishMetric, IReporterPublishService reportsPublishService, IReporterPublishService auditPublishService)
    {
        this.publishEventType = publishEventType;
        this.publishMetric = publishMetric;
        this.reportsPublishService = reportsPublishService;
        this.auditPublishService = auditPublishService;

        init();
    }

    private void init()
    {
        publishStatistics.updatePublishTime();

        startScheduledTasks();
    }

    private void startScheduledTasks()
    {
        ScheduledNotification.getInstance(this, 60 * 1000);
    }

    @Override
    public final void handleEvent()
    {
        handlePublishEvent();
        handleAuditEvent();
    }

    private boolean reportsReady(int available)
    {
        return ((reportsPublishService.isEnabled() && (available >= reportsPublishService.getConfiguration().getMaxBatchSize())) || ((available > 0) && reportsPublishService.publishReady()));
    }

    private EventRecords emptyQueue(BlockingQueue<EventRecord> queue)
    {
        EventRecords records;

        if (queue.isEmpty())
        {
            records = EventRecords.getEmpty();
        }
        else
        {
            records = EventRecords.getInstance();

            queue.drainTo(records.getList());
        }

        return records;
    }

    private int publish()
    {
        int count = 0;

        long lastPublishTime = publishStatistics.getPublishTime();
        publishStatistics.updatePublishTime();

        if (reportsReady(reportsQueue.size()))
        {
            EventRecords records = emptyQueue(reportsQueue);

            count = records.size();

            if (count > 0)
            {
                EventRecords copy = records.getCopy();

                records = processEventRecords(records);

                if (!reportsPublishService.sendRequest(IReporterJsonBuilder.toJson(records.getList())))
                {
                    publishStatistics.setPublishTime(lastPublishTime);

                    count = -count;

                    handleFailedPublishEvent(copy);
                }
            }
        }

        return count;
    }

    private void handleFailedPublishEvent(EventRecords records)
    {
        backlogEventPublisher.publishEvents(records, publishStatistics);
    }

    private EventRecords processEventRecords(EventRecords records)
    {
        EventRecords result = EventRecords.getInstance();

        for (EventRecord record : records.getList())
        {
            String value = record.removeAttribute(EventAttribute.EVENT_TYPE.key());
            EventType eventType = EventType.getEventType(value);

            if (eventType != EventType.LOYALTY)
            {
                record = IReporterEvent.processEventRecord(record);
            }

            if (eventType != publishEventType)
            {
                LOGGER.warn("Expected publishEventType=" + publishEventType + " received eventType=" + eventType);
            }

            result.add(record);
        }

        return result;
    }

    private void handlePublishEvent()
    {
        stopWatch.start();

        int count = publish();

        stopWatch.stop();

        if (count > 0)
        {
            // Hubble for IReporter
            hubblePublisher.incrementCountForEvent(publishMetric.getMessagesSentMetric());
            hubblePublisher.incrementCountForEvent(publishMetric.getRecordsSentMetric(), count);
            // Hubble for SMP
            hubblePublisher.logTimingForEvent(publishMetric.getIReporterTiming(), stopWatch.getTimeInMilliseconds());
            // IReporter
            statistics.increment(publishMetric.getIReporterRecordsSent(), count);
        }
        else if (count < 0)
        {
            count = -count;
            // Hubble for IReporter
            hubblePublisher.incrementCountForEvent(publishMetric.getMessagesFailedMetric());
            hubblePublisher.incrementCountForEvent(publishMetric.getRecordsFailedMetric(), count);
            // Hubble for SMP
            hubblePublisher.logTimingForEvent(publishMetric.getIReporterTiming(), stopWatch.getTimeInMilliseconds());
            // IReporter
            statistics.increment(publishMetric.getIReporterRecordsFailed(), count);
        }
    }

    private void handleAuditEvent()
    {
        if (auditPublishService.publishReady())
        {
            int sent = statistics.getIntValue(publishMetric.getIReporterRecordsSent());
            int failed = statistics.getIntValue(publishMetric.getIReporterRecordsFailed());
            int pending = statistics.getIntValue(publishMetric.getIReporterRecordsPending());
            int lost = statistics.getIntValue(publishMetric.getIReporterRecordsLost());

            List<AuditRecord> auditRecords = new ArrayList<>();
            auditRecords.add(new AuditRecord(sent, failed, pending, lost));

            AuditRequest auditRequest = new AuditRequest(auditRecords);

            if (auditPublishService.sendRequest(GsonBuilderFactory.getInstance().toJson(auditRequest, AuditRequest.class)))
            {
                hubblePublisher.incrementCountForEvent(publishMetric.getAuditRecordsSent());

                statistics.clear(publishMetric.getAuditMetrics());
            }
            else
            {
                hubblePublisher.incrementCountForEvent(publishMetric.getAuditRecordsFailed());
            }
        }
    }

    @Override
    public void processEventRecord(EventRecord record)
    {
        reportsQueue.offer(record);

        hubblePublisher.logCountForEvent(publishMetric.getIReporterRecordsPending(), reportsQueue.size());
    }
}