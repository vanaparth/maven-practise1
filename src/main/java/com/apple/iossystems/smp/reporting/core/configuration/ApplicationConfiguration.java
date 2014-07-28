package com.apple.iossystems.smp.reporting.core.configuration;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.logging.LogLevel;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public abstract class ApplicationConfiguration
{
    public static final PropertyManager PROPERTY_MANAGER = getPropertyManager();

    // Property keys in SMPBroker.properties
    public static final String KEYSTONE_RABBIT_HOST_KEY = "keystone.rabbit.host";
    public static final String KEYSTONE_RABBIT_PORT_KEY = "keystone.rabbit.port";
    public static final String KEYSTONE_RABBIT_USER_KEY = "keystone.rabbit.user";
    public static final String KEYSTONE_RABBIT_PASS_KEY = "keystone.rabbit.pass";
    public static final String KEYSTONE_RABBIT_VHOST_KEY = "keystone.rabbit.virtualhost";

    // Property keys not in SMPBroker.properties
    public static final String RABBIT_CONSUMER_THREADS_KEY = "rabbit.consumerThreads";
    public static final String RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_KEY = "rabbit.consumerThreads.prefetchCount";
    public static final String SMP_EVENTS_EXCHANGE_KEY = "smp.events.exchange";
    public static final String SMP_EVENTS_PUBLISH_ENABLE_KEY = "smp.events.publish.enable";
    public static final String SMP_REPORTING_APPLICATION_ENABLE_KEY = "smp.reporting.application.enable";

    // Property values in SMPBroker.properties
    public static final String KEYSTONE_RABBIT_HOST_VALUE = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_HOST_KEY, "rabbit-np-amqp.corp.apple.com");
    public static final String KEYSTONE_RABBIT_PORT_VALUE = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_PORT_KEY, "5672");
    public static final String KEYSTONE_RABBIT_USER_VALUE = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_USER_KEY, "SMPQA_User");
    public static final String KEYSTONE_RABBIT_PASS_VALUE = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_PASS_KEY, "SMPQA_User123");
    public static final String KEYSTONE_RABBIT_VHOST_VALUE = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_VHOST_KEY, "SMPQA1");

    // Property values not in SMPBroker.properties
    public static final String RABBIT_CONSUMER_THREADS_VALUE = PROPERTY_MANAGER.valueForKeyWithDefault(RABBIT_CONSUMER_THREADS_KEY, "10");
    public static final String RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_VALUE = PROPERTY_MANAGER.valueForKeyWithDefault(RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_KEY, "1");
    public static final String SMP_EVENTS_EXCHANGE_VALUE = PROPERTY_MANAGER.valueForKeyWithDefault(SMP_EVENTS_EXCHANGE_KEY, "iossystems.stockholm.events");
    public static final boolean SMP_EVENTS_PUBLISH_ENABLE_VALUE = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault(SMP_EVENTS_PUBLISH_ENABLE_KEY, true);
    public static final boolean SMP_REPORTING_APPLICATION_ENABLE_VALUE = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault(SMP_REPORTING_APPLICATION_ENABLE_KEY, true);

    // Other global configuration settings
    public static final String LOG_LEVEL_EVENT_NAME = LogLevel.EVENT.name();

    // Used by SMPReportingApplication to set system properties.
    public static final String RABBIT_HOST_KEY = "rabbit.host";
    public static final String RABBIT_PORT_KEY = "rabbit.port";
    public static final String RABBIT_USER_KEY = "rabbit.user";
    public static final String RABBIT_PASS_KEY = "rabbit.pass";
    public static final String RABBIT_VHOST_KEY = "rabbit.vhost";

    private ApplicationConfiguration()
    {
    }

    private static PropertyManager getPropertyManager()
    {
        PropertyManager propertyManager = null;

        try
        {
            propertyManager = PropertyManager.getInstance();
            propertyManager.initializeProperties(true);
        }
        catch (Exception e)
        {
            Logger.getLogger(ApplicationConfiguration.class).error(e);
        }

        return propertyManager;
    }
}
