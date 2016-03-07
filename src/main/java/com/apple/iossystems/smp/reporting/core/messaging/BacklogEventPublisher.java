package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.smp.reporting.core.analytics.PublishStatistics;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
public class BacklogEventPublisher
{
    private final int MAX_PUBLISH_DOWN_TIME = PropertyManager.getInstance().getIntValueForKeyWithDefault("smp.reporting.maxPublishDownTime", 5 * 60 * 1000);

    private final NotificationService notificationService = SMPEventNotificationService.getInstance().getPublisher();
    private final NotificationService backlogNotificationService = BacklogNotificationService.getInstance();

    private BacklogEventPublisher()
    {
    }

    public static BacklogEventPublisher getInstance()
    {
        return new BacklogEventPublisher();
    }

    private boolean publishToEventQueue(PublishStatistics publishStatistics)
    {
        return (System.currentTimeMillis() - publishStatistics.getPublishTime()) <= MAX_PUBLISH_DOWN_TIME;
    }

    public void publishEvents(EventRecords records, PublishStatistics publishStatistics)
    {
        if (publishToEventQueue(publishStatistics))
        {
            notificationService.publishEvents(records);
        }
        else
        {
            backlogNotificationService.publishEvents(records);
        }
    }
}