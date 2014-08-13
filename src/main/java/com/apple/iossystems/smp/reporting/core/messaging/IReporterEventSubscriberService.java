package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.impl.SMPEventSubscriberService;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterService;

/**
 * @author Toch
 */
class IReporterEventSubscriberService<LogEvent> extends SMPEventSubscriberService<LogEvent>
{
    private IReporterService iReporterService = IReporterService.getInstance();

    private IReporterEventSubscriberService(String queueName)
    {
        super(queueName);
    }

    static IReporterEventSubscriberService getInstance(String queueName)
    {
        return new IReporterEventSubscriberService(queueName);
    }

    @Override
    public void handleEvent(com.apple.iossystems.logging.pubsub.LogEvent logEvent)
    {
        iReporterService.postSMPEvent(EventRecord.getInstance(logEvent.getMetadata()));
    }
}
