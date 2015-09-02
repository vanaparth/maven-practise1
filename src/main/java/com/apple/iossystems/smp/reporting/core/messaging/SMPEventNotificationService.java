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

    private final NotificationService OFFLINE_NOTIFICATION_SERVICE = OfflineNotificationService.getInstance();

    private NotificationService publisher;

    private SMPEventNotificationService()
    {
        initNotificationService();
    }

    public static SMPEventNotificationService getInstance()
    {
        return INSTANCE;
    }

    public NotificationService getPublisher()
    {
        return publisher;
    }

    private void initNotificationService()
    {
        NotificationService notificationService = getEventNotificationService();

        if (notificationService == null)
        {
            notificationService = OFFLINE_NOTIFICATION_SERVICE;

            LOGGER.warn("Using offline notification service");

            new TaskHandler();
        }

        publisher = notificationService;
    }

    private NotificationService getEventNotificationService()
    {
        NotificationService notificationService = null;

        // Prevent any side effects
        try
        {
            notificationService = EventNotificationService.getInstance();
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
            if (publisher != null)
            {
                shutdown();
            }
            else
            {
                NotificationService notificationService = getEventNotificationService();

                if (notificationService != null)
                {
                    publisher = notificationService;

                    shutdown();
                }
            }
        }
    }
}