package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledEventTaskHandler;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class SMPEventNotificationService
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventNotificationService.class);

    private static final SMPEventNotificationService INSTANCE = new SMPEventNotificationService();

    private final NotificationService OFFLINE_NOTIFICATION_SERVICE = new OfflineNotificationService();

    private NotificationService EVENT_NOTIFICATION_SERVICE = createEventNotificationService();

    private SMPEventNotificationService()
    {
    }

    public static SMPEventNotificationService getInstance()
    {
        return INSTANCE;
    }

    public NotificationService getPublisher()
    {
        return getNotificationService();
    }

    private NotificationService getNotificationService()
    {
        NotificationService notificationService = EVENT_NOTIFICATION_SERVICE;

        if (notificationService == null)
        {
            notificationService = OFFLINE_NOTIFICATION_SERVICE;

            LOGGER.warn("Using offline notification service");
        }

        return notificationService;
    }

    private NotificationService createEventNotificationService()
    {
        NotificationService notificationService = getEventNotificationService();

        if (notificationService == null)
        {
            new TaskHandler();
        }

        return notificationService;
    }

    private NotificationService getEventNotificationService()
    {
        NotificationService notificationService = null;

        // Prevent any side effects
        try
        {
            notificationService = new EventNotificationService();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return notificationService;
    }

    private class TaskHandler extends ScheduledEventTaskHandler
    {
        private TaskHandler()
        {
        }

        @Override
        public void handleEvent()
        {
            if (EVENT_NOTIFICATION_SERVICE != null)
            {
                shutdown();
            }
            else
            {
                NotificationService notificationService = getEventNotificationService();

                if (notificationService != null)
                {
                    EVENT_NOTIFICATION_SERVICE = notificationService;

                    shutdown();
                }
            }
        }
    }
}