package com.apple.cds.messaging.client.impl;

import com.apple.iossystems.logging.pubsub.LoggingSubscriberServiceBase;

/**
 * @author Toch
 */
abstract class EventSubscriberService<LogEvent> extends LoggingSubscriberServiceBase<LogEvent>
{
    abstract void begin();

    abstract void handleEvent(com.apple.iossystems.logging.pubsub.LogEvent logEvent);
}
