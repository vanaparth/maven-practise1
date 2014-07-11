package com.apple.cds.messaging.client.impl;

/**
 * @author Toch
 */
public class EventSubscriberServiceFactory
{
    public static EventSubscriberServiceIReporter newIReporterSubscriber(String queueName)
    {
        return EventSubscriberServiceIReporter.getInstance(queueName);
    }
}
