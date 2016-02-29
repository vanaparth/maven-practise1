package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.hubble.HubblePublisher;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class BacklogEventPublisher
{
    private static final Logger LOGGER = Logger.getLogger(BacklogEventPublisher.class);

    private final NotificationService notificationService = SMPEventNotificationService.getInstance().getPublisher();

    private final HubblePublisher hubblePublisher = HubblePublisher.getInstance();

    private BacklogEventPublisher()
    {
    }

    public static BacklogEventPublisher getInstance()
    {
        return new BacklogEventPublisher();
    }

    public void publishEvents(EventRecords records)
    {
        // Prevent any side effects
        try
        {
            notificationService.publishEvents(records);

            hubblePublisher.incrementCountForEvent(Metric.PUBLISH_BACKLOG_EVENT_QUEUE, records.size());
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);

            hubblePublisher.incrementCountForEvent(Metric.PUBLISH_BACKLOG_EVENT_QUEUE_FAILED, records.size());
        }
    }
}