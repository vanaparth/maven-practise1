package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class NotificationServiceFactory
{
    private NotificationServiceFactory()
    {
    }

    static NotificationServiceFactory getInstance()
    {
        return new NotificationServiceFactory();
    }

    NotificationService getEventNotificationService()
    {
        return ApplicationConfiguration.publishEventsEnabled() ? EventNotificationService.getInstance() : NoOpNotificationService.getInstance();
    }

    NotificationService getBacklogEventNotificationService()
    {
        return ApplicationConfiguration.publishEventsEnabled() ? BacklogEventNotificationService.getInstance() : NoOpNotificationService.getInstance();
    }

    NotificationService getNotificationService(String className)
    {
        return ApplicationConfiguration.publishEventsEnabled() ? doGetNotificationService(className) : NoOpNotificationService.getInstance();
    }

    private NotificationService doGetNotificationService(String className)
    {
        NotificationService notificationService = null;

        if (className != null)
        {
            try
            {
                notificationService = (NotificationService) Class.forName(className).newInstance();
            }
            catch (Exception e)
            {
                Logger.getLogger(NotificationServiceFactory.class).error(e.getMessage(), e);
            }
        }

        return notificationService;
    }
}