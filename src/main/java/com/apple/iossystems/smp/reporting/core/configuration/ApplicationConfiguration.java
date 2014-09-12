package com.apple.iossystems.smp.reporting.core.configuration;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.logging.LogLevel;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class ApplicationConfiguration
{
    private static final PropertyManager PROPERTY_MANAGER = getPropertyManager();

    private static final String KEYSTONE_RABBIT_HOST_KEY = "keystone.rabbit.host";
    private static final String KEYSTONE_RABBIT_PORT_KEY = "keystone.rabbit.port";
    private static final String KEYSTONE_RABBIT_USER_KEY = "keystone.rabbit.user";
    private static final String KEYSTONE_RABBIT_PASS_KEY = "keystone.rabbit.pass";
    private static final String KEYSTONE_RABBIT_VHOST_KEY = "keystone.rabbit.virtualhost";

    static final String RABBIT_CONSUMER_THREADS_COUNT_KEY = "rabbit.consumerThreads";
    static final String RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_KEY = "rabbit.consumerThreads.prefetchCount";

    private static final String SMP_EVENTS_EXCHANGE_KEY = "smp.reporting.events.exchange";
    private static final String IREPORTER_URL_KEY = "icloud.ireporter.url";
    private static final String HASH_PASS_KEY = "icloud.ireporter.pass";

    static final String KEYSTONE_RABBIT_HOST = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_HOST_KEY, "rabbit-np-amqp.corp.apple.com");
    static final String KEYSTONE_RABBIT_PORT = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_PORT_KEY, "5672");
    static final String KEYSTONE_RABBIT_USER = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_USER_KEY, "SMPQA_User");
    static final String KEYSTONE_RABBIT_PASS = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_PASS_KEY, "SMPQA_User123");
    static final String KEYSTONE_RABBIT_VHOST = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_VHOST_KEY, "SMPQA1");

    static final int RABBIT_CONSUMER_THREADS_COUNT = PROPERTY_MANAGER.getIntValueForKeyWithDefault(RABBIT_CONSUMER_THREADS_COUNT_KEY, 10);
    static final int RABBIT_CONSUMER_THREADS_PREFETCH_COUNT = PROPERTY_MANAGER.getIntValueForKeyWithDefault(RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_KEY, 1);

    static final String SMP_EVENTS_EXCHANGE = PROPERTY_MANAGER.valueForKeyWithDefault(SMP_EVENTS_EXCHANGE_KEY, "iossystems.stockholm.events");
    static final String IREPORTER_URL = PROPERTY_MANAGER.valueForKeyWithDefault(IREPORTER_URL_KEY, "https://icloud4-e3.icloud.com");
    static final String HASH_PASS = PROPERTY_MANAGER.valueForKeyWithDefault(HASH_PASS_KEY, "pLijzg2e2QNspdhOyNWdOSScPszmZBryJ0L8BcQ116BhkT6p0iHyNcwnlFIwhLun");

    static final String LOG_LEVEL_EVENT_NAME = LogLevel.EVENT.name();

    static final String RABBIT_HOST_KEY = "rabbit.host";
    static final String RABBIT_PORT_KEY = "rabbit.port";
    static final String RABBIT_USER_KEY = "rabbit.user";
    static final String RABBIT_PASS_KEY = "rabbit.pass";
    static final String RABBIT_VHOST_KEY = "rabbit.vhost";

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