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

        publisher = getEventNotificationService(notificationServiceClassName);

        if (!isOnline(publisher))
        {
            publisher = getEventNotificationService();
        }

        if (!isOnline(publisher))
        {
            publisher = OfflineNotificationService.getInstance();

            LOGGER.warn("Using offline notification service");

            new TaskHandler(notificationServiceClassName);
        }
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
            if (isOnline(publisher))
            {
                shutdown();
            }
            else
            {
                LOGGER.info("Attempting to restart notification service");

                publisher = (notificationServiceClassName != null) ? getEventNotificationService(notificationServiceClassName) : getEventNotificationService();

                if (isOnline(publisher))
                {
                    LOGGER.info("Successfully started notification service");

                    shutdown();
                }
            }
        }
    }
}