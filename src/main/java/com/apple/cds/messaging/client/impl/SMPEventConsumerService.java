package com.apple.cds.messaging.client.impl;

import com.apple.cds.messaging.client.ConsumerServiceProperties;
import com.apple.cds.messaging.client.DeliveryHandler;
import com.apple.cds.messaging.client.events.AbstractConsumerServiceEventListener;
import com.apple.cds.messaging.client.events.Event;
import com.apple.cds.messaging.client.events.EventId;
import com.apple.iossystems.logging.pubsub.LogEventSerializer;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledEventTaskHandler;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class SMPEventConsumerService<T> extends BasicConsumerService<T>
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventConsumerService.class);

    public SMPEventConsumerService(ConsumerServiceProperties properties, DeliveryHandler<T> deliveryHandler, LogEventSerializer<T> serializer)
    {
        super(properties, deliveryHandler, serializer);

        setEventListener(getEventListener());
    }

    private AbstractConsumerServiceEventListener<T> getEventListener()
    {
        return new AbstractConsumerServiceEventListener<T>()
        {
            @Override
            public void onEvent(EventId.ConsumerEventId eventId, Event<T> event)
            {
                handleConsumerServiceEvent(eventId);
            }

            @Override
            public void onEvent(EventId.ServiceEventId eventId, Event<T> event)
            {
                handleConsumerServiceEvent(eventId);
            }
        };
    }

    private void handleConsumerServiceEvent(EventId eventId)
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
                LOGGER.warn("Attempting to restart consumer for event " + ((eventId != null) ? eventId.getName() : " Unknown"));

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