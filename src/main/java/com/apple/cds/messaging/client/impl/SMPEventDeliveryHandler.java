package com.apple.cds.messaging.client.impl;

import com.apple.cds.messaging.client.Delivery;
import com.apple.cds.messaging.client.DeliveryHandler;

/**
 * @author Toch
 */
class SMPEventDeliveryHandler<LogEvent> implements DeliveryHandler<LogEvent>
{
    private SMPEventSubscriberService<LogEvent> smpEventSubscriberService;

    public SMPEventDeliveryHandler()
    {
    }

    public void setEventHandler(SMPEventSubscriberService<LogEvent> smpEventSubscriberService)
    {
        this.smpEventSubscriberService = smpEventSubscriberService;
    }

    @Override
    public void handleDelivery(Delivery<LogEvent> delivery)
    {
        smpEventSubscriberService.handleEvent((com.apple.iossystems.logging.pubsub.LogEvent) delivery.getMessage());
    }
}