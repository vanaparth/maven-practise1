package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.logging.LogService;
import com.apple.iossystems.logging.LogServiceFactory2;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.analytics.ResultMetric;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.core.util.MapToPair;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Toch
 */
public class BacklogNotificationService implements NotificationService
{
    private static final Logger LOGGER = Logger.getLogger(EventNotificationService.class);

    private final EventNotificationServiceThreadPool threadPool = EventNotificationServiceThreadPool.getInstance();
    private final EventHubblePublisher eventHubblePublisher = EventHubblePublisher.getInstance(getMetricMap());

    private final LogService logService = getLogService();

    private final boolean publishEventsEnabled = ApplicationConfiguration.publishEventsEnabled();

    private BacklogNotificationService()
    {
    }

    public static BacklogNotificationService getInstance()
    {
        return new BacklogNotificationService();
    }

    private Map<EventType, ResultMetric> getMetricMap()
    {
        Map<EventType, ResultMetric> map = new HashMap<>();

        map.put(EventType.REPORTS, new ResultMetric(Metric.PUBLISH_REPORTS_BACKLOG_EVENT_QUEUE, Metric.PUBLISH_REPORTS_BACKLOG_EVENT_QUEUE_FAILED));
        map.put(EventType.PAYMENT, new ResultMetric(Metric.PUBLISH_PAYMENT_BACKLOG_EVENT_QUEUE, Metric.PUBLISH_PAYMENT_BACKLOG_EVENT_QUEUE_FAILED));
        map.put(EventType.LOYALTY, new ResultMetric(Metric.PUBLISH_LOYALTY_BACKLOG_EVENT_QUEUE, Metric.PUBLISH_LOYALTY_BACKLOG_EVENT_QUEUE_FAILED));

        return map;
    }

    private LogService getLogService()
    {
        LogService logService = null;

        try
        {
            logService = LogServiceFactory2.getInstance().createLogService(new SMPEventLogServiceConfigurator(), new SMPEventLogServiceFactoryStrategy());
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return logService;
    }

    private void publishEventRecord(EventRecord record)
    {
        try
        {
            logService.logEvent("event", EventType.BACKLOG.getLogLevel(), MapToPair.toPairs(record.getData()));

            eventHubblePublisher.incrementCountForSuccessEvent(EventType.getEventType(record));
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);

            eventHubblePublisher.incrementCountForFailedEvent(EventType.getEventType(record));
        }
    }

    private void publishEventRecords(EventRecords records)
    {
        if (publishEventsEnabled)
        {
            doPublishEventRecords(records);
        }
    }

    private void doPublishEventRecords(EventRecords records)
    {
        try
        {
            for (EventRecord record : records.getList())
            {
                publishEventRecord(record);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void publishEventTask(EventRecords records)
    {
        try
        {
            publishEventRecords(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void publishEvents(EventRecords records)
    {
        // Prevent any side effects
        try
        {
            threadPool.submit(new Task(records));
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean isOnline()
    {
        return (logService != null);
    }

    private class Task implements Callable<Boolean>
    {
        private final EventRecords records;

        private Task(EventRecords records)
        {
            this.records = records;
        }

        @Override
        public Boolean call() throws Exception
        {
            publishEventTask(records);

            return true;
        }
    }
}