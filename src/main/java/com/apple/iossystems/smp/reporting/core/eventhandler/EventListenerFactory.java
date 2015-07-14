package com.apple.iossystems.smp.reporting.core.eventhandler;

import com.apple.cds.keystone.config.PropertyManager;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class EventListenerFactory
{
    private static final Logger LOGGER = Logger.getLogger(EventListenerFactory.class);

    private EventListenerFactory()
    {
    }

    public static EventListenerFactory getInstance()
    {
        return new EventListenerFactory();
    }

    private EventListener getEventListener(String propertyKey, String defaultClassName)
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

    private String getFullClassName(String className)
    {
        return (StringUtils.isNotBlank(className) && (!className.contains("."))) ? "com.apple.iossystems.smp.reporting.core.eventhandler." + className : className;
    }

    public EventListener getEmailEventListener()
    {
        return getEventListener("smp.reporting.emailEventListener", "EmailEventListener");
    }

    public EventListener getSMPPublishEventListener()
    {
        return getEventListener("smp.reporting.smpPublishEventListener", "SMPPublishEventListener");
    }

    public EventListener getSMPConsumeEventListener()
    {
        return getEventListener("smp.reporting.smpConsumeEventListener", "SMPConsumeEventListener");
    }

    public EventListener getSMPKistaEventListener()
    {
        return new SMPKistaEventListener();
    }
}