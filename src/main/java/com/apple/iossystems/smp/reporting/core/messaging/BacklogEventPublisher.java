package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.analytics.PublishStatistics;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.timer.Timer;

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

    private boolean publishToBacklogQueue(PublishStatistics publishStatistics)
    {
        return ((!publishStatistics.getPublishStatus()) && Timer.delayExpired(publishStatistics.getPublishTime(), maxPublishDownTime) && (publishStatistics.getPublishCount() >= 5));
    }

    public void handleShutdownEvent(EventRecords records)
    {
        eventNotificationService.publishEvents(records);
    }

    public void publishEvents(EventRecords records, PublishStatistics publishStatistics)
    {
        if (publishToBacklogQueue(publishStatistics))
        {
            backlogEventNotificationService.publishEvents(records);
        }
        else
        {
            eventNotificationService.publishEvents(records);
        }
    }
}