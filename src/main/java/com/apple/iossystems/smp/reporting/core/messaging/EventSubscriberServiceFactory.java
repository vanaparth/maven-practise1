package com.apple.iossystems.smp.reporting.core.messaging;

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