package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.analytics.PublishStatistics;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
public class BacklogEventPublisher
{
    private final NotificationService eventNotificationService = SMPEventNotificationService.getInstance().getPublisher();
    private final NotificationService backlogEventNotificationService = NotificationServiceFactory.getInstance().getBacklogEventNotificationService();

    private final int maxPublishDownTime = ApplicationConfiguration.getMaxPublishDownTime();

    private BacklogEventPublisher()
    {
    }

    public static BacklogEventPublisher getInstance()
    {
        return new BacklogEventPublisher();
    }

    private boolean publishToEventQueue(PublishStatistics publishStatistics)
    {
        return ((System.currentTimeMillis() - publishStatistics.getPublishTime()) <= maxPublishDownTime);
    }

    public void handleShutdownEvent(EventRecords records)
    {
        eventNotificationService.publishEvents(records);
    }

    public void publishEvents(EventRecords records, PublishStatistics publishStatistics)
    {
        if (publishToEventQueue(publishStatistics))
        {
            eventNotificationService.publishEvents(records);
        }
        else
        {
            backlogEventNotificationService.publishEvents(records);
        }
    }
}