package com.apple.cds.messaging.client.impl;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.logging.LogLevel;

/**
 * @author Toch
 */
public class SMPEventConfiguration
{
    public static final String EXCHANGE_NAME = PropertyManager.getInstance().valueForKeyWithDefault("smp.events.exchange", "iossystems.stockholm.events");

    public static final String LOG_LEVEL_EVENT = LogLevel.EVENT.name();
}
