package com.apple.cds.messaging.client.impl;

import com.apple.cds.keystone.core.OperationalAnalyticsManager;
import com.apple.iossystems.logging.pubsub.LoggingSubscriberServiceBase;

/**
 * @author Toch
 */
public abstract class EventSubscriberServiceSMP<LogEvent> extends LoggingSubscriberServiceBase<LogEvent>
{
    public abstract void setupAndStart() throws Exception;
}
