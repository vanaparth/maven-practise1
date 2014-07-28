package com.apple.cds.messaging.client.impl;

/**
 * @author Toch
 */
class EventSubscriberServiceFactory
{
    private EventSubscriberServiceFactory()
    {
    }

    static IReporterEventSubscriberService getIReporterEventSubscriberService(String queueName) throws Exception
    {
        return IReporterEventSubscriberService.getInstance(queueName);
    }
}