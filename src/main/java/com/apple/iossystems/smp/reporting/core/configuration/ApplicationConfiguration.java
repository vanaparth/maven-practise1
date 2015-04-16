package com.apple.iossystems.smp.reporting.core.configuration;

import com.apple.cds.keystone.config.PropertyManager;

/**
 * @author Toch
 */
class ApplicationConfiguration
{
    private static final PropertyManager PROPERTY_MANAGER = PropertyManager.getInstance();

    static final String KEYSTONE_RABBIT_HOST = PROPERTY_MANAGER.valueForKeyWithDefault("keystone.rabbit.host", "rabbit-np-amqp.corp.apple.com");
    static final String KEYSTONE_RABBIT_PORT = PROPERTY_MANAGER.valueForKeyWithDefault("keystone.rabbit.port", "5672");
    static final String KEYSTONE_RABBIT_USER = PROPERTY_MANAGER.valueForKeyWithDefault("keystone.rabbit.user", "SMPQA_User");
    static final String KEYSTONE_RABBIT_PASSWORD = PROPERTY_MANAGER.valueForKeyWithDefault("keystone.rabbit.pass", "SMPQA_User123");
    static final String KEYSTONE_RABBIT_VIRTUAL_HOST = PROPERTY_MANAGER.valueForKeyWithDefault("keystone.rabbit.virtualhost", "SMPQA1");

    static final int RABBIT_CONSUMER_THREADS_COUNT = PROPERTY_MANAGER.getIntValueForKeyWithDefault("rabbit.consumerThreads", 10);
    static final int RABBIT_CONSUMER_THREADS_PREFETCH_COUNT = PROPERTY_MANAGER.getIntValueForKeyWithDefault("rabbit.consumerThreads.prefetchCount", 1);

    static final String SMP_EVENTS_EXCHANGE = PROPERTY_MANAGER.valueForKeyWithDefault("smp.reporting.events.exchange", "iossystems.stockholm.events");

    static final String LOG_SERVICE_OWNER = PROPERTY_MANAGER.valueForKeyWithDefault("smp.reporting.logging.owner", "iossystems");
    static final String LOG_SERVICE_PATH = PROPERTY_MANAGER.valueForKeyWithDefault("smp.reporting.logging.path", "stockholm");
    static final String LOG_SERVICE_CATEGORY = PROPERTY_MANAGER.valueForKeyWithDefault("smp.reporting.logging.category", "events");
    static final String LOG_SERVICE_STORE = PROPERTY_MANAGER.valueForKeyWithDefault("smp.reporting.logging.store", "reporting");
    static final String LOG_SERVICE_EVENT_FINDER_CLASS = PROPERTY_MANAGER.valueForKeyWithDefault("smp.reporting.logging.eventFinderClass", "com.apple.iossystems.logging.BufferedPubSubEventFinder");
    static final String LOG_SERVICE_CLASS = PROPERTY_MANAGER.valueForKeyWithDefault("smp.reporting.logging.classname", "com.apple.iossystems.logging.impl.pubsub.BufferedPubSub");

    static final String IREPORTER_URL = PROPERTY_MANAGER.valueForKeyWithDefault("icloud.ireporter.url", "https://icloud4-e3.icloud.com");
    static final String HASH_PASSWORD = PROPERTY_MANAGER.valueForKeyWithDefault("icloud.ireporter.pass", "pLijzg2e2QNspdhOyNWdOSScPszmZBryJ0L8BcQ116BhkT6p0iHyNcwnlFIwhLun");

    static final boolean EMAIL_LOGGING_ENABLED = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault("smp.reporting.email.log", false);
    static final boolean PROVISION_EMAIL_ENABLED = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault("smp.reporting.email.provision", true);
    static final boolean SUSPEND_EMAIL_ENABLED = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault("smp.reporting.email.suspend", true);
    static final boolean UNLINK_EMAIL_ENABLED = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault("smp.reporting.email.unlink", true);
    static final boolean DEFAULT_EMAIL_LOCALE_ENABLED = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault("smp.reporting.email.locale", true);

    static final String FMIP_CERTIFICATE = PROPERTY_MANAGER.valueForKeyWithDefault("com.apple.iossystems.internal.fmip.app.cert", "0");
    static final String FMIP_REMOTE_CERTIFICATE = PROPERTY_MANAGER.valueForKeyWithDefault("com.apple.iossystems.internal.fmip.setup.cert", "0");

    private ApplicationConfiguration()
    {
    }
}