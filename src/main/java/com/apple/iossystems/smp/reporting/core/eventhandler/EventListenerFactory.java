package com.apple.iossystems.smp.reporting.core.eventhandler;

/**
 * @author Toch
 */
public class EventListenerFactory
{
    private EventListenerFactory()
    {
    }

    public static EventListenerFactory getInstance()
    {
        return new EventListenerFactory();
    }

    public EventListener getEmailEventListener()
    {
        return new EmailEventListener();
    }

    public EventListener getSMPPublishEventListener()
    {
        return new SMPPublishEventListener();
    }

    public EventListener getSMPConsumeEventListener()
    {
        return new SMPConsumeEventListener();
    }

    public EventListener getSMPKistaEventListener()
    {
        return new SMPKistaEventListener();
    }
}