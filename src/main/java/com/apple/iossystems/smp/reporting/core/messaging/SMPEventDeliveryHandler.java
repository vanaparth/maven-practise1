package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.Delivery;
import com.apple.cds.messaging.client.DeliveryHandler;
import com.apple.cds.messaging.client.impl.SMPEventSubscriberService;
import com.apple.iossystems.logging.pubsub.LogEvent;

/**
 * @author Toch
 */
public class SMPEventDeliveryHandler implements DeliveryHandler<LogEvent>
{
    private SMPEventSubscriberService smpEventSubscriberService;

    public void setEventHandler(SMPEventSubscriberService smpEventSubscriberService)
    {
        this.smpEventSubscriberService = smpEventSubscriberService;
    }

    @Override
    public void handleDelivery(Delivery<LogEvent> delivery)
    {
        smpEventSubscriberService.handleEvent(delivery.getMessage());
    }
}