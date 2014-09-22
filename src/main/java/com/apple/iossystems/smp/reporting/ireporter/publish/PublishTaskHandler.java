package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.analytics.Statistics;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledNotification;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTaskHandler;
import com.apple.iossystems.smp.reporting.core.email.EmailPublishService;
import com.apple.iossystems.smp.reporting.core.event.*;
import com.apple.iossystems.smp.reporting.core.hubble.HubbleAnalytics;
import com.apple.iossystems.smp.reporting.ireporter.configuration.ConfigurationEvent;
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
    private EmailPublishService emailPublishService = EmailPublishService.getInstance();

    private Statistics statistics = Statistics.getInstance();

    private BlockingQueue<EventRecord> reportsQueue = new LinkedBlockingQueue<EventRecord>(1000);
    private BlockingQueue<EventRecord> paymentReportsQueue = new LinkedBlockingQueue<EventRecord>(1000);
    private BlockingQueue<EventRecord> emailReportsQueue = new LinkedBlockingQueue<EventRecord>(1000);

    private static final Metric[] AUDIT_METRICS =
            {
                    Metric.IREPORTER_REPORTS_RECORDS_SENT,
                    Metric.IREPORTER_REPORTS_RECORDS_FAILED,
                    Metric.IREPORTER_REPORTS_RECORDS_PENDING,
                    Metric.IREPORTER_REPORTS_RECORDS_LOST
            };

    private PublishTaskHandler() throws Exception
    {
    }

    public static PublishTaskHandler getInstance() throws Exception
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
        handlePublishReportsEvent();
        handlePublishPaymentReportsEvent();
        handleAuditEvent();
        handleConfigurationEvent();
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

    private int handlePublishEvent(IReporterPublishService service, BlockingQueue<EventRecord> queue)
    {
        int count = 0;

        if (reportsReady(service, queue))
        {
            EventRecords records = emptyQueue(queue);
            count = records.size();

            if (count > 0)
            {
                List<EventRecord> list = records.getList();

                if (!service.sendRequest(IReporterJsonBuilder.toJson(records.getList())))
                {
                    queue.addAll(list);

                    count = -count;
                }
            }
        }

        return count;
    }

    private void handlePublishReportsEvent()
    {
        int count = handlePublishEvent(reportsPublishService, reportsQueue);

        if (count > 0)
        {
            // Hubble
            HubbleAnalytics.incrementCountForEvent(Metric.REPORTS_MESSAGES_SENT);
            HubbleAnalytics.incrementCountForEvent(Metric.REPORTS_RECORDS_SENT, count);
            // IReporter
            statistics.increment(Metric.IREPORTER_REPORTS_RECORDS_SENT, count);
        }
        else if (count < 0)
        {
            count = -count;
            // Hubble
            HubbleAnalytics.incrementCountForEvent(Metric.REPORTS_MESSAGES_FAILED);
            HubbleAnalytics.incrementCountForEvent(Metric.REPORTS_RECORDS_FAILED, count);
            // IReporter
            statistics.increment(Metric.IREPORTER_REPORTS_RECORDS_FAILED, count);
        }
    }

    private void handlePublishPaymentReportsEvent()
    {
        handlePublishEvent(paymentReportsPublishService, paymentReportsQueue);
    }

    private void handleAuditEvent()
    {
        IReporterPublishService service = auditPublishService;

        if (service.publishReady())
        {
            int sent = statistics.getIntValue(Metric.IREPORTER_REPORTS_RECORDS_SENT);
            int failed = statistics.getIntValue(Metric.IREPORTER_REPORTS_RECORDS_FAILED);
            int backLog = statistics.getIntValue(Metric.IREPORTER_REPORTS_RECORDS_PENDING);
            int lost = statistics.getIntValue(Metric.IREPORTER_REPORTS_RECORDS_LOST);

            IReporterAudit auditData = IReporterAudit.getBuilder().sentCount(sent).failedCount(failed).backlogCount(backLog).lostCount(lost).build();

            if (service.sendRequest(auditData.toJson()))
            {
                HubbleAnalytics.incrementCountForEvent(Metric.AUDIT_RECORDS_SENT);

                statistics.clear(AUDIT_METRICS);
            }
            else
            {
                HubbleAnalytics.incrementCountForEvent(Metric.AUDIT_RECORDS_FAILED);
            }
        }
    }

    private void handleConfigurationEvent()
    {
        logConfigurationEvent(reportsPublishService.getConfigurationService().getConfigurationEvent(), Metric.REPORTS_CONFIGURATION_REQUESTED, Metric.REPORTS_CONFIGURATION_CHANGED);

        logConfigurationEvent(auditPublishService.getConfigurationService().getConfigurationEvent(), Metric.AUDIT_CONFIGURATION_REQUESTED, Metric.AUDIT_CONFIGURATION_CHANGED);
    }

    private void logConfigurationEvent(ConfigurationEvent configurationEvent, Metric configurationRequestedMetric, Metric configurationChangedMetric)
    {
        if (configurationEvent.isUpdated())
        {
            HubbleAnalytics.incrementCountForEvent(configurationRequestedMetric);
        }

        if (configurationEvent.isModified())
        {
            HubbleAnalytics.incrementCountForEvent(configurationChangedMetric);
        }
    }

    private void handleEmailEvent()
    {
        EventRecords records = emptyQueue(emailReportsQueue);

        if (records.size() > 0)
        {
            emailPublishService.send(records);
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