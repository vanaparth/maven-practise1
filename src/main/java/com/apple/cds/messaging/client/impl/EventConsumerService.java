package com.apple.cds.messaging.client.impl;

import com.apple.cds.messaging.client.ConsumerServiceProperties;
import com.apple.cds.messaging.client.events.AbstractConsumerServiceEventListener;
import com.apple.cds.messaging.client.events.Event;
import com.apple.cds.messaging.client.events.EventId;
import com.apple.iossystems.logging.pubsub.LogEvent;
import com.apple.iossystems.smp.reporting.core.messaging.SMPEventDeliveryHandler;
import com.apple.iossystems.smp.reporting.core.messaging.SMPLogEventSerializer;

/**
 * @author Toch
 */
class EventConsumerService extends BasicConsumerService<LogEvent>
{
    EventConsumerService(ConsumerServiceProperties properties, SMPEventDeliveryHandler deliveryHandler, SMPLogEventSerializer serializer)
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
            }

            @Override
            public void onEvent(EventId.ServiceEventId eventId, Event<LogEvent> event)
            {
            }
        };
    }
}