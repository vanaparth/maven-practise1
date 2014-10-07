package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.analytics.Statistics;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledNotification;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTaskHandler;
import com.apple.iossystems.smp.reporting.core.email.EmailPublishService;
import com.apple.iossystems.smp.reporting.core.event.*;
import com.apple.iossystems.smp.reporting.core.hubble.HubbleAnalytics;
import com.apple.iossystems.smp.reporting.ireporter.json.IReporterJsonBuilder;

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

    private Statistics statistics = Statistics.getInstance();

    private BlockingQueue<EventRecord> reportsQueue = new LinkedBlockingQueue<EventRecord>(1000);
    private BlockingQueue<EventRecord> paymentReportsQueue = new LinkedBlockingQueue<EventRecord>(1000);
    private BlockingQueue<EventRecord> emailReportsQueue = new LinkedBlockingQueue<EventRecord>(1000);

    private static final PublishMetric REPORTS_METRICS = PublishMetric.getReportsMetrics();
    private static final PublishMetric PAYMENT_REPORTS_METRICS = PublishMetric.getPaymentReportsMetrics();

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
        handlePublishEvent(reportsPublishService, reportsQueue, REPORTS_METRICS);
        handlePublishEvent(paymentReportsPublishService, paymentReportsQueue, PAYMENT_REPORTS_METRICS);
    }

    private void handleAuditEvent()
    {
        handleAuditEvent(auditPublishService, REPORTS_METRICS);
    }

    private void handlePublishEvent(IReporterPublishService publishService, BlockingQueue<EventRecord> queue, PublishMetric publishMetric)
    {
        int count = publish(publishService, queue);

        if (count > 0)
        {
            // Hubble
            HubbleAnalytics.incrementCountForEvent(publishMetric.getMessagesSentMetric());
            HubbleAnalytics.incrementCountForEvent(publishMetric.getRecordsSentMetric(), count);
            // IReporter
            statistics.increment(publishMetric.getIReporterRecordsSent(), count);
        }
        else if (count < 0)
        {
            count = -count;
            // Hubble
            HubbleAnalytics.incrementCountForEvent(publishMetric.getMessagesFailedMetric());
            HubbleAnalytics.incrementCountForEvent(publishMetric.getRecordsFailedMetric(), count);
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
            int backLog = statistics.getIntValue(publishMetric.getIReporterRecordsPending());
            int lost = statistics.getIntValue(publishMetric.getIReporterRecordsLost());

            IReporterAudit auditData = IReporterAudit.getBuilder().sentCount(sent).failedCount(failed).backlogCount(backLog).lostCount(lost).build();

            if (auditService.sendRequest(auditData.toJson()))
            {
                HubbleAnalytics.incrementCountForEvent(publishMetric.getAuditRecordsSent());

                statistics.clear(publishMetric.getAuditMetrics());
            }
            else
            {
                HubbleAnalytics.incrementCountForEvent(publishMetric.getAuditRecordsFailed());
            }
        }
    }

    private void handleEmailEvent()
    {
        EventRecords records = emptyQueue(emailReportsQueue);

        if (records.size() > 0)
        {
            EmailPublishService.send(records);
        }
    }

    public boolean add(EventRecord record)
    {
        String value = record.removeAttributeValue(EventAttribute.EVENT_TYPE.key());
        EventType eventType = EventType.getEventType(value);

        if (eventType == EventType.REPORTS)
        {
            return reportsQueue.offer(SMPEventRecord.maskSelectedAttributes(SMPEventRecord.removeEmailAttributes(record)));
        }
        else if (eventType == EventType.PAYMENT)
        {
            return paymentReportsQueue.offer(SMPEventRecord.maskSelectedAttributes(SMPEventRecord.removeEmailAttributes(record)));
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