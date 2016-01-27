package com.apple.cds.messaging.client.impl;

import com.apple.cds.messaging.client.Delivery;
import com.apple.cds.messaging.client.DeliveryHandler;
import com.apple.iossystems.logging.pubsub.LogEvent;

/**
 * @author Toch
 */
class SMPEventDeliveryHandler<T> implements DeliveryHandler<T>
{
    private SMPEventSubscriberService<T> smpEventSubscriberService;

    public void setEventHandler(SMPEventSubscriberService<T> smpEventSubscriberService)
    {
        this.smpEventSubscriberService = smpEventSubscriberService;
    }

    @Override
    public void handleDelivery(Delivery<T> delivery)
    {
        smpEventSubscriberService.handleEvent((LogEvent) delivery.getMessage());
    }
}