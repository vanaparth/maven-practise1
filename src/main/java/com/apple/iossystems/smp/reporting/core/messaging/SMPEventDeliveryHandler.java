package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.Delivery;
import com.apple.cds.messaging.client.DeliveryHandler;
import com.apple.cds.messaging.client.impl.EventSubscriberService;
import com.apple.iossystems.logging.pubsub.LogEvent;

/**
 * @author Toch
 */
public class SMPEventDeliveryHandler implements DeliveryHandler<LogEvent>
{
    private EventSubscriberService eventSubscriberService;

    public SMPEventDeliveryHandler()
    {
    }

    public void setEventHandler(EventSubscriberService eventSubscriberService)
    {
        this.eventSubscriberService = eventSubscriberService;
    }

    @Override
    public void handleDelivery(Delivery<LogEvent> delivery)
    {
        eventSubscriberService.handleEvent(delivery.getMessage());
    }
}