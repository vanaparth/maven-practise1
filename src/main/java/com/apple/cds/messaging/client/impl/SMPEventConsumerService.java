package com.apple.cds.messaging.client.impl;

import com.apple.cds.messaging.client.ConsumerServiceProperties;
import com.apple.cds.messaging.client.DeliveryHandler;
import com.apple.cds.messaging.client.events.AbstractConsumerServiceEventListener;
import com.apple.cds.messaging.client.events.Event;
import com.apple.cds.messaging.client.events.EventId;
import com.apple.iossystems.logging.pubsub.LogEventSerializer;

/**
 * @author Toch
 */
class SMPEventConsumerService<LogEvent> extends BasicConsumerService<LogEvent>
{
    public SMPEventConsumerService(ConsumerServiceProperties properties, DeliveryHandler<LogEvent> deliveryHandler, LogEventSerializer<LogEvent> serializer)
    {
        super(properties, deliveryHandler, serializer);

        setEventListener(getEventListener());
    }

    private AbstractConsumerServiceEventListener getEventListener()
    {
        return new AbstractConsumerServiceEventListener<com.apple.iossystems.logging.pubsub.LogEvent>()
        {
            @Override
            public void onEvent(EventId.ConsumerEventId eventId, Event<com.apple.iossystems.logging.pubsub.LogEvent> event)
            {
            }

            @Override
            public void onEvent(EventId.ServiceEventId eventId, Event<com.apple.iossystems.logging.pubsub.LogEvent> event)
            {
            }
        };
    }
}