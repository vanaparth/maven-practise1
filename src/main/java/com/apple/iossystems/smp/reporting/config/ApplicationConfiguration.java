package com.apple.iossystems.smp.reporting.config;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.logging.LogLevel;

/**
 * @author Toch
 */
public abstract class ApplicationConfiguration implements BaseConfiguration
{
    protected static final PropertyManager PROPERTY_MANAGER = PropertyManager.getInstance();

    // Property keys in SMPBroker.properties
    protected static final String KEYSTONE_RABBIT_HOST_KEY = "keystone.rabbit.host";
    protected static final String KEYSTONE_RABBIT_PORT_KEY = "keystone.rabbit.port";
    protected static final String KEYSTONE_RABBIT_USER_KEY = "keystone.rabbit.user";
    protected static final String KEYSTONE_RABBIT_PASS_KEY = "keystone.rabbit.pass";
    protected static final String KEYSTONE_RABBIT_VHOST_KEY = "keystone.rabbit.virtualhost";

    // Property keys not in SMPBroker.properties
    protected static final String RABBIT_CONSUMER_THREADS_KEY = "rabbit.consumerThreads";
    protected static final String RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_KEY = "rabbit.consumerThreads.prefetchCount";
    protected static final String SMP_EVENTS_EXCHANGE_KEY = "smp.events.exchange";
    protected static final String SMP_EVENTS_PUBLISH_ENABLE_KEY = "smp.events.publish.enable";
    protected static final String SMP_REPORTING_APPLICATION_ENABLE_KEY = "smp.reporting.application.enable";

    // Property values in SMPBroker.properties
    protected static final String KEYSTONE_RABBIT_HOST_VALUE = PROPERTY_MANAGER.valueForKey(KEYSTONE_RABBIT_HOST_KEY);
    protected static final String KEYSTONE_RABBIT_PORT_VALUE = PROPERTY_MANAGER.valueForKey(KEYSTONE_RABBIT_PORT_KEY);
    protected static final String KEYSTONE_RABBIT_USER_VALUE = PROPERTY_MANAGER.valueForKey(KEYSTONE_RABBIT_USER_KEY);
    protected static final String KEYSTONE_RABBIT_PASS_VALUE = PROPERTY_MANAGER.valueForKey(KEYSTONE_RABBIT_PASS_KEY);
    protected static final String KEYSTONE_RABBIT_VHOST_VALUE = PROPERTY_MANAGER.valueForKey(KEYSTONE_RABBIT_VHOST_KEY);

    // Property values not in SMPBroker.properties
    protected static final String RABBIT_CONSUMER_THREADS_VALUE = PROPERTY_MANAGER.valueForKeyWithDefault(RABBIT_CONSUMER_THREADS_KEY, "10");
    protected static final String RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_VALUE = PROPERTY_MANAGER.valueForKeyWithDefault(RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_KEY, "1");
    protected static final String SMP_EVENTS_EXCHANGE_VALUE = PROPERTY_MANAGER.valueForKeyWithDefault(SMP_EVENTS_EXCHANGE_KEY, "iossystems.stockholm.events");
    protected static final boolean SMP_EVENTS_PUBLISH_ENABLE_VALUE = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault(SMP_EVENTS_PUBLISH_ENABLE_KEY, true);
    protected static final boolean SMP_REPORTING_APPLICATION_ENABLE_VALUE = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault(SMP_REPORTING_APPLICATION_ENABLE_KEY, true);

    // Other global configuration settings
    protected static final String LOG_LEVEL_EVENT_NAME = LogLevel.EVENT.name();

    // Used by SMPReportingApplication for setting system properties.
    protected static final String RABBIT_HOST_KEY = "rabbit.host";
    protected static final String RABBIT_PORT_KEY = "rabbit.port";
    protected static final String RABBIT_USER_KEY = "rabbit.user";
    protected static final String RABBIT_PASS_KEY = "rabbit.pass";
    protected static final String RABBIT_VHOST_KEY = "rabbit.virtualhost";
}
