package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class SMPEventNotificationService
{
    private static final SMPEventNotificationService INSTANCE = new SMPEventNotificationService();

    private NotificationService publisher = getEventNotificationService();

    private SMPEventNotificationService()
    {
    }

    public static SMPEventNotificationService getInstance()
    {
        return INSTANCE;
    }

    public NotificationService getPublisher()
    {
        return publisher;
    }

    private NotificationService getEventNotificationService()
    {
        NotificationService eventNotificationService = null;

        // Prevent any side effects
        try
        {
            eventNotificationService = doGetEventNotificationService();
        }
        catch (Exception e)
        {
            Logger.getLogger(SMPEventNotificationService.class).error(e.getMessage(), e);
        }

        return eventNotificationService;
    }

    private NotificationService doGetEventNotificationService()
    {
        String eventNotificationServiceClassName = PropertyManager.getInstance().valueForKey("smp.reporting.eventNotificationService.classname");

        NotificationServiceFactory notificationServiceFactory = NotificationServiceFactory.getInstance();

        NotificationService eventNotificationService = (eventNotificationServiceClassName != null) ? notificationServiceFactory.getNotificationService(eventNotificationServiceClassName) : notificationServiceFactory.getEventNotificationService();

        if (!isOnline(eventNotificationService))
        {
            eventNotificationService = OfflineNotificationService.getInstance();

            Logger.getLogger(SMPEventNotificationService.class).warn("Using offline notification service");
        }

        if (!ApplicationConfiguration.publishEventsEnabled())
        {
            Logger.getLogger(SMPEventNotificationService.class).warn("Publish events is disabled");
        }

        return eventNotificationService;
    }

    private boolean isOnline(NotificationService notificationService)
    {
        return ((notificationService != null) && notificationService.isOnline());
    }
}