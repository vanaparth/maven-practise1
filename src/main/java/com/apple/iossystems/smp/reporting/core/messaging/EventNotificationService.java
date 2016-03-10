package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.logging.LogService;
import com.apple.iossystems.logging.LogServiceFactory2;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.analytics.ResultMetric;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.email.EmailEventService;
import com.apple.iossystems.smp.reporting.core.email.EmailServiceFactory;
import com.apple.iossystems.smp.reporting.core.email.SMPEmailEvent;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.core.eventhandler.EventListener;
import com.apple.iossystems.smp.reporting.core.eventhandler.EventListenerFactory;
import com.apple.iossystems.smp.reporting.core.util.MapToPair;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Toch
 */
class EventNotificationService implements NotificationService
{
    private static final Logger LOGGER = Logger.getLogger(EventNotificationService.class);

    private final EventNotificationServiceThreadPool threadPool = EventNotificationServiceThreadPool.getInstance();
    private final EmailEventService emailService = EmailServiceFactory.getInstance().getEmailService();
    private final EventListener eventListener = EventListenerFactory.getInstance().getSMPPublishEventListener();
    private final EventListener kistaEventListener = EventListenerFactory.getInstance().getSMPKistaEventListener();
    private final EventHubblePublisher eventHubblePublisher = EventHubblePublisher.getInstance(getMetricMap());
    private final LogService logService = getLogService();

    private final boolean publishEventsEnabled = ApplicationConfiguration.publishEventsEnabled();

    private EventNotificationService()
    {
    }

    public static EventNotificationService getInstance()
    {
        return new EventNotificationService();
    }

    private Map<EventType, ResultMetric> getMetricMap()
    {
        Map<EventType, ResultMetric> map = new HashMap<>();

        map.put(EventType.REPORTS, new ResultMetric(Metric.PUBLISH_REPORTS_QUEUE, Metric.PUBLISH_REPORTS_QUEUE_FAILED));
        map.put(EventType.PAYMENT, new ResultMetric(Metric.PUBLISH_PAYMENT_QUEUE, Metric.PUBLISH_PAYMENT_QUEUE_FAILED));
        map.put(EventType.LOYALTY, new ResultMetric(Metric.PUBLISH_LOYALTY_QUEUE, Metric.PUBLISH_LOYALTY_QUEUE_FAILED));

        return map;
    }

    private LogService getLogService()
    {
        LogService logService = null;

        try
        {
            logService = LogServiceFactory2.getInstance().createLogService(SMPEventLogServiceConfigurator.getInstance(), SMPEventLogServiceFactoryStrategy.getInstance());
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
            logService.logEvent("event", EventType.getLogLevel(record), MapToPair.toPairs(record.getData()));

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

            notifyListeners(records);
        }
    }

    private void doPublishEventRecords(EventRecords records)
    {
        for (EventRecord record : records.getList())
        {
            publishEventRecord(record);
        }
    }

    private void publishEmailRecords(EventRecords records)
    {
        try
        {
            emailService.send(SMPEmailEvent.getEventRecords(records));
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void notifyListeners(EventRecords records)
    {
        notifyEventListener(records);

        notifyKista(records);
    }

    private void notifyEventListener(EventRecords records)
    {
        try
        {
            eventListener.handleEvent(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void notifyKista(EventRecords records)
    {
        try
        {
            kistaEventListener.handleEvent(records);
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

            publishEmailRecords(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void publishEvents(EventRecords records)
    {
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