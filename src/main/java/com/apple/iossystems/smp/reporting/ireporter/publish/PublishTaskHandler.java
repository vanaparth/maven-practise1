package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.reporting.core.analytics.Statistics;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledNotification;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTaskHandler;
import com.apple.iossystems.smp.reporting.core.email.EmailService;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.core.hubble.HubblePublisher;
import com.apple.iossystems.smp.reporting.core.timer.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Toch
 */
public class PublishTaskHandler implements ScheduledTaskHandler
{
    private IReporterPublishService reportsPublishService = ReportsPublishService.getInstance();
    private IReporterPublishService auditPublishService = AuditPublishService.getInstance();
    private IReporterPublishService paymentReportsPublishService = PaymentReportsPublishService.getInstance();
    private IReporterPublishService paymentAuditPublishService = PaymentAuditPublishService.getInstance();

    private HubblePublisher hubblePublisher = HubblePublisher.getInstance();

    private EmailService emailService = EmailService.getInstance();

    private Statistics statistics = Statistics.getInstance();
    private StopWatch stopWatch = StopWatch.getInstance();

    private BlockingQueue<EventRecord> reportsQueue = new LinkedBlockingQueue<>(1000);
    private BlockingQueue<EventRecord> paymentReportsQueue = new LinkedBlockingQueue<>(1000);
    private BlockingQueue<EventRecord> emailReportsQueue = new LinkedBlockingQueue<>(1000);

    private PublishMetric reportsMetrics = PublishMetric.getReportsMetrics();
    private PublishMetric paymentReportsMetrics = PublishMetric.getPaymentReportsMetrics();

    private PublishTaskHandler()
    {
    }

    public static PublishTaskHandler getInstance()
    {
        PublishTaskHandler publishTaskHandler = new PublishTaskHandler();

        publishTaskHandler.init();

        return publishTaskHandler;
    }

    private void init()
    {
        startScheduledTasks();
    }

    private void startScheduledTasks()
    {
        ScheduledNotification.getInstance(this, 60 * 1000);
    }

    @Override
    public final void handleEvent()
    {
        handleEmailEvent();
        handlePublishEvent();
        handleAuditEvent();
    }

    private boolean reportsReady(IReporterPublishService service, BlockingQueue<EventRecord> queue)
    {
        int available = queue.size();

        return ((service.isEnabled() && (available >= service.getConfiguration().getMaxBatchSize())) ||
                ((available > 0) && service.publishReady()));
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

    private int publish(IReporterPublishService service, BlockingQueue<EventRecord> queue)
    {
        int count = 0;

        if (reportsReady(service, queue))
        {
            EventRecords records = emptyQueue(queue);
            count = records.size();

            if (count > 0)
            {
                List<EventRecord> list = records.getList();

                if (!service.sendRequest(IReporterJsonBuilder.toJson(list)))
                {
                    queue.addAll(list);

                    count = -count;
                }
            }
        }

        return count;
    }

    private void handlePublishEvent()
    {
        handlePublishEvent(reportsPublishService, reportsQueue, reportsMetrics);
        handlePublishEvent(paymentReportsPublishService, paymentReportsQueue, paymentReportsMetrics);
    }

    private void handleAuditEvent()
    {
        handleAuditEvent(auditPublishService, reportsMetrics);
        handleAuditEvent(paymentAuditPublishService, paymentReportsMetrics);
    }

    private void handlePublishEvent(IReporterPublishService publishService, BlockingQueue<EventRecord> queue, PublishMetric publishMetric)
    {
        stopWatch.start();

        int count = publish(publishService, queue);

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
            // Hubble
            hubblePublisher.incrementCountForEvent(publishMetric.getMessagesFailedMetric());
            hubblePublisher.incrementCountForEvent(publishMetric.getRecordsFailedMetric(), count);
            // Hubble for SMP
            hubblePublisher.logTimingForEvent(publishMetric.getIReporterTiming(), stopWatch.getTimeInMilliseconds());
            // IReporter
            statistics.increment(publishMetric.getIReporterRecordsFailed(), count);
        }
    }

    private void handleAuditEvent(IReporterPublishService auditService, PublishMetric publishMetric)
    {
        if (auditService.publishReady())
        {
            int sent = statistics.getIntValue(publishMetric.getIReporterRecordsSent());
            int failed = statistics.getIntValue(publishMetric.getIReporterRecordsFailed());
            int pending = statistics.getIntValue(publishMetric.getIReporterRecordsPending());
            int lost = statistics.getIntValue(publishMetric.getIReporterRecordsLost());

            List<AuditRecord> auditRecords = new ArrayList<>();
            auditRecords.add(new AuditRecord(sent, failed, pending, lost));

            AuditRequest auditRequest = new AuditRequest(auditRecords);

            if (auditService.sendRequest(GsonBuilderFactory.getInstance().toJson(auditRequest, AuditRequest.class)))
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

    private void handleEmailEvent()
    {
        EventRecords records = emptyQueue(emailReportsQueue);

        if (records.size() > 0)
        {
            emailService.send(records);
        }
    }

    public boolean add(EventRecord record)
    {
        String value = record.removeAttribute(EventAttribute.EVENT_TYPE.key());
        EventType eventType = EventType.getEventType(value);

        if (eventType == EventType.REPORTS)
        {
            return reportsQueue.offer(IReporterEvent.processEventRecord(record));
        }
        else if (eventType == EventType.PAYMENT)
        {
            return paymentReportsQueue.offer(IReporterEvent.processEventRecord(record));
        }
        else if (eventType == EventType.EMAIL)
        {
            return emailReportsQueue.offer(record);
        }
        else
        {
            return true;
        }
    }
}