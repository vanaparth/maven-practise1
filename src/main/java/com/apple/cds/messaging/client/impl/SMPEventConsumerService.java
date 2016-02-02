package com.apple.cds.messaging.client.impl;

import com.apple.cds.messaging.client.ConsumerServiceProperties;
import com.apple.cds.messaging.client.events.AbstractConsumerServiceEventListener;
import com.apple.cds.messaging.client.events.Event;
import com.apple.cds.messaging.client.events.EventId;
import com.apple.iossystems.logging.pubsub.LogEvent;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledEventTaskHandler;
import com.apple.iossystems.smp.reporting.core.messaging.SMPEventDeliveryHandler;
import com.apple.iossystems.smp.reporting.core.messaging.SMPLogEventSerializer;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class SMPEventConsumerService extends BasicConsumerService<LogEvent>
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventConsumerService.class);

    public SMPEventConsumerService(ConsumerServiceProperties properties, SMPEventDeliveryHandler deliveryHandler, SMPLogEventSerializer serializer)
    {
        super(properties, deliveryHandler, serializer);

        setEventListener(getEventListener());
    }

    private AbstractConsumerServiceEventListener<LogEvent> getEventListener()
    {
        return new AbstractConsumerServiceEventListener<LogEvent>()
        {
            @Override
            public void onEvent(EventId.ConsumerEventId eventId, Event<LogEvent> event)
            {
                handleConsumerServiceEvent(eventId);
            }

            @Override
            public void onEvent(EventId.ServiceEventId eventId, Event<LogEvent> event)
            {
                handleConsumerServiceEvent(eventId);
            }
        };
    }

    public void handleConsumerServiceEvent(EventId eventId)
    {
        if ((eventId == EventId.ConsumerEventId.CONSUMER_CANCELED) || (eventId == EventId.ConsumerEventId.CONSUMER_STOPPED))
        {
            new TaskHandler(eventId);
        }
    }

    private boolean restartConsumer(EventId eventId)
    {
        if (isQuiescent() || isStopped())
        {
            try
            {
                LOGGER.info("Attempting to restart consumer for event " + ((eventId != null) ? eventId.getName() : "Unknown"));

                start();
            }
            catch (Exception e)
            {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return isStarted();
    }

    private class TaskHandler extends ScheduledEventTaskHandler
    {
        private final EventId eventId;

        private TaskHandler(EventId eventId)
        {
            this.eventId = eventId;
        }

        @Override
        public void handleEvent()
        {
            if (restartConsumer(eventId))
            {
                shutdown();
            }
        }
    }
}