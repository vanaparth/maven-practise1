package com.apple.cds.messaging.client.impl;

import com.apple.cds.messaging.client.ConsumerServiceProperties;
import com.apple.cds.messaging.client.DeliveryHandler;
import com.apple.iossystems.logging.pubsub.LogEventSerializer;

/**
 * @author Toch
 */
class SMPEventConsumerService<LogEvent> extends BasicConsumerService<LogEvent>
{
    public SMPEventConsumerService(ConsumerServiceProperties properties, DeliveryHandler<LogEvent> deliveryHandler, LogEventSerializer<LogEvent> serializer)
    {
        super(properties, deliveryHandler, serializer);
    }
}