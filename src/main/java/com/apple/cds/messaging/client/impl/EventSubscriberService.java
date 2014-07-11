package com.apple.cds.messaging.client.impl;

import com.apple.iossystems.logging.pubsub.LoggingSubscriberServiceBase;

/**
 * @author Toch
 */
public abstract class EventSubscriberService<LogEvent> extends LoggingSubscriberServiceBase<LogEvent>
{
    protected abstract void begin();

    protected abstract void handleEvent(com.apple.iossystems.logging.pubsub.LogEvent logEvent);
}
