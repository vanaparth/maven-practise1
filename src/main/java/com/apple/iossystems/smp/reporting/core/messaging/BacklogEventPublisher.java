package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.analytics.ResultMetric;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class BacklogEventPublisher
{
    private static final Logger LOGGER = Logger.getLogger(BacklogEventPublisher.class);

    private final NotificationService notificationService = SMPEventNotificationService.getInstance().getPublisher();

    private final EventHubblePublisher eventHubblePublisher = EventHubblePublisher.getInstance(getMetricMap());

    private BacklogEventPublisher()
    {
    }

    public static BacklogEventPublisher getInstance()
    {
        return new BacklogEventPublisher();
    }

    private Map<EventType, ResultMetric> getMetricMap()
    {
        Map<EventType, ResultMetric> map = new HashMap<>();

        map.put(EventType.REPORTS, new ResultMetric(Metric.PUBLISH_REPORTS_BACKLOG_EVENT_QUEUE, Metric.PUBLISH_REPORTS_BACKLOG_EVENT_QUEUE_FAILED));
        map.put(EventType.PAYMENT, new ResultMetric(Metric.PUBLISH_PAYMENT_BACKLOG_EVENT_QUEUE, Metric.PUBLISH_PAYMENT_BACKLOG_EVENT_QUEUE_FAILED));
        map.put(EventType.LOYALTY, new ResultMetric(Metric.PUBLISH_LOYALTY_BACKLOG_EVENT_QUEUE, Metric.PUBLISH_LOYALTY_BACKLOG_EVENT_QUEUE_FAILED));

        return map;
    }

    private void notifyHubbleForSuccessEvent(EventRecords records)
    {
        try
        {
            for (EventRecord record : records.getList())
            {
                eventHubblePublisher.incrementCountForSuccessEvent(EventType.getEventType(record));
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void notifyHubbleForFailedEvent(EventRecords records)
    {
        try
        {
            for (EventRecord record : records.getList())
            {
                eventHubblePublisher.incrementCountForFailedEvent(EventType.getEventType(record));
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public void publishEvents(EventRecords records)
    {
        // Prevent any side effects
        try
        {
            notificationService.publishEvents(records);

            notifyHubbleForSuccessEvent(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);

            notifyHubbleForFailedEvent(records);
        }
    }
}