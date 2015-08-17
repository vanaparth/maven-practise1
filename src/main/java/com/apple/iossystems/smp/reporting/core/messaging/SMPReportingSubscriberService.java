package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.impl.SMPEventSubscriberService;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledEventTaskHandler;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.eventhandler.EventListener;
import com.apple.iossystems.smp.reporting.core.eventhandler.EventListenerFactory;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class SMPReportingSubscriberService<LogEvent> extends SMPEventSubscriberService<LogEvent>
{
    private static final Logger LOGGER = Logger.getLogger(SMPReportingSubscriberService.class);

    private EventListener eventListener = EventListenerFactory.getInstance().getSMPConsumeEventListener();

    private SMPReportingService smpReportingService;

    private SMPReportingSubscriberService(String queueName, SMPReportingService smpReportingService)
    {
        super(queueName);

        this.smpReportingService = smpReportingService;
    }

    static SMPReportingSubscriberService getInstance(String queueName, SMPReportingService smpReportingService)
    {
        return new SMPReportingSubscriberService(queueName, smpReportingService);
    }

    @Override
    public void handleEvent(com.apple.iossystems.logging.pubsub.LogEvent logEvent)
    {
        EventRecord record = EventRecord.getInstance();

        record.putAll(logEvent.getMetadata());

        if (smpReportingService.postSMPEvent(record))
        {
            notifyEventListener(record);
        }
        else
        {
            handleFailedRequest(record);
        }
    }

    private void handleFailedRequest(EventRecord record)
    {
        try
        {
            pause();

            new TaskHandler(record);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void resumeService()
    {
        try
        {
            resume();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void notifyEventListener(EventRecord record)
    {
        try
        {
            EventRecords records = EventRecords.getInstance();

            records.add(record);

            eventListener.handleEvent(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private class TaskHandler extends ScheduledEventTaskHandler
    {
        private final EventRecord record;

        private TaskHandler(EventRecord record)
        {
            this.record = record;
        }

        @Override
        public void handleEvent()
        {
            if (smpReportingService.postSMPEvent(record))
            {
                resumeService();

                shutdown();
            }
        }
    }
}