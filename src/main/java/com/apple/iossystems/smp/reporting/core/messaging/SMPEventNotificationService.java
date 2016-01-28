package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.keystone.config.PropertyManager;
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
        initPublisher();
    }

    public static SMPEventNotificationService getInstance()
    {
        return INSTANCE;
    }

    public NotificationService getPublisher()
    {
        return publisher;
    }

    private void initPublisher()
    {
        // Prevent any side effects
        try
        {
            doInitPublisher();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void doInitPublisher()
    {
        String notificationServiceClassName = PropertyManager.getInstance().valueForKey("smp.reporting.eventNotificationService.classname");

        NotificationService notificationService = getEventNotificationService(notificationServiceClassName);

        if (!isOnline(notificationService))
        {
            notificationService = getEventNotificationService();
        }

        if (!isOnline(notificationService))
        {
            notificationService = OFFLINE_NOTIFICATION_SERVICE;

            LOGGER.warn("Using offline notification service");

            new TaskHandler(notificationServiceClassName);
        }

        publisher = notificationService;
    }

    private NotificationService getEventNotificationService()
    {
        NotificationService notificationService = null;

        try
        {
            notificationService = EventNotificationService.getInstance();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return notificationService;
    }

    private NotificationService getEventNotificationService(String className)
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
                LOGGER.error(e.getMessage(), e);
            }
        }

        return notificationService;
    }

    private boolean isOnline(NotificationService notificationService)
    {
        return ((notificationService != null) && notificationService.isOnline());
    }

    private class TaskHandler extends ScheduledEventTaskHandler
    {
        private final String notificationServiceClassName;

        private TaskHandler(String notificationServiceClassName)
        {
            this.notificationServiceClassName = notificationServiceClassName;
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
                NotificationService notificationService = (notificationServiceClassName != null) ? getEventNotificationService(notificationServiceClassName) : getEventNotificationService();

                if (isOnline(notificationService))
                {
                    publisher = notificationService;

                    shutdown();
                }
            }
        }
    }
}