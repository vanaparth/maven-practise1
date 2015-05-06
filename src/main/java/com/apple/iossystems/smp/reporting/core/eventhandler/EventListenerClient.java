package com.apple.iossystems.smp.reporting.core.eventhandler;

import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class EventListenerClient
{
    private static final Logger LOGGER = Logger.getLogger(EventListenerClient.class);

    public EventListenerClient()
    {
    }

    public static EventListener getEmailEventListener()
    {
        EventListener eventListener;

        try
        {
            //return (EventListener) Class.forName(PropertyManager.getInstance().valueForKeyWithDefault("smp.reporting.emailEventListener", "EmailEventListener"));
        }
        catch (Exception e)
        {
            LOGGER.error(e);

            eventListener = new EmailEventListener();
        }

        return null;
    }

    public static EventListener getSMPNotificationEventListener()
    {
        SMPNotificationEventListener eventListener;

        try
        {
            //return (EventListener) Class.forName(PropertyManager.getInstance().valueForKeyWithDefault("smp.reporting.emailEventListener", "EmailEventListener"));
        }
        catch (Exception e)
        {
            LOGGER.error(e);

            eventListener = new SMPNotificationEventListener();
        }

        return null;
    }
}