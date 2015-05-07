package com.apple.iossystems.smp.reporting.core.eventhandler;

import com.apple.cds.keystone.config.PropertyManager;
import org.apache.commons.lang.StringUtils;
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

    private static EventListener getEventListener(String propertyKey, String defaultClassName)
    {
        EventListener eventListener = null;

        try
        {
            Class eventListenerClass = Class.forName(getFullClassName(PropertyManager.getInstance().valueForKeyWithDefault(propertyKey, defaultClassName)));

            eventListener = (EventListener) eventListenerClass.newInstance();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return eventListener;
    }

    private static String getFullClassName(String className)
    {
        return ((StringUtils.isNotBlank(className) && (!className.contains("."))) ? "com.apple.iossystems.smp.reporting.core.eventhandler." + className : className);
    }

    public static EventListener getEmailEventListener()
    {
        return getEventListener("smp.reporting.emailEventListener", "EmailEventListener");
    }

    public static EventListener getSMPNotificationEventListener()
    {
        return getEventListener("smp.reporting.smpNotificationEventListener", "SMPNotificationEventListener");
    }
}