package com.apple.iossystems.smp.reporting.core.configuration;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.logging.LogLevel;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
abstract class ApplicationConfiguration
{
    private static final PropertyManager PROPERTY_MANAGER = getPropertyManager();

    // Rabbit
    private static final String KEYSTONE_RABBIT_HOST_KEY = "keystone.rabbit.host";
    private static final String KEYSTONE_RABBIT_PORT_KEY = "keystone.rabbit.port";
    private static final String KEYSTONE_RABBIT_USER_KEY = "keystone.rabbit.user";
    private static final String KEYSTONE_RABBIT_PASS_KEY = "keystone.rabbit.pass";
    private static final String KEYSTONE_RABBIT_VHOST_KEY = "keystone.rabbit.virtualhost";

    static final String RABBIT_CONSUMER_THREADS_COUNT_KEY = "rabbit.consumerThreads";
    static final String RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_KEY = "rabbit.consumerThreads.prefetchCount";

    // Event Publishing
    static final String SMP_EVENTS_PUBLISH_ENABLE_KEY = "smp.reporting.events.publish.enable";
    private static final String SMP_REPORTING_APPLICATION_ENABLE_KEY = "smp.reporting.application.enable";
    private static final String SMP_EVENTS_EXCHANGE_KEY = "smp.reporting.events.exchange";

    // IReporter
    private static final String IREPORTER_URL_KEY = "icloud.ireporter.url";
    private static final String IREPORTER_REPORTS_CONFIGURATION_URL_KEY = "ireporter.reports.configuration.url";
    private static final String IREPORTER_AUDIT_CONFIGURATION_URL_KEY = "ireporter.audit.configuration.url";
    private static final String IREPORTER_REPORTS_URL_KEY = "ireporter.reports.url";
    private static final String IREPORTER_AUDIT_URL_KEY = "ireporter.audit.url";
    private static final String IREPORTER_PUBLISH_KEY_KEY = "ireporter.publish.key";
    private static final String IREPORTER_PUBLISH_ENABLE_KEY = "ireporter.publish.enable";
    private static final String IREPORTER_CONTENT_TYPE_KEY = "ireporter.content.type";
    private static final String IREPORTER_MAX_BATCH_SIZE_KEY = "ireporter.max.batch.size";
    private static final String IREPORTER_PUBLISH_FREQUENCY_KEY = "ireporter.publish.frequency";
    private static final String IREPORTER_CONFIGURATION_RELOAD_FREQUENCY_KEY = "ireporter.configuration.reload.frequency";

    // Rabbit
    static final String KEYSTONE_RABBIT_HOST = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_HOST_KEY, "rabbit-np-amqp.corp.apple.com");
    static final String KEYSTONE_RABBIT_PORT = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_PORT_KEY, "5672");
    static final String KEYSTONE_RABBIT_USER = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_USER_KEY, "SMPQA_User");
    static final String KEYSTONE_RABBIT_PASS = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_PASS_KEY, "SMPQA_User123");
    static final String KEYSTONE_RABBIT_VHOST = PROPERTY_MANAGER.valueForKeyWithDefault(KEYSTONE_RABBIT_VHOST_KEY, "SMPQA1");

    static final int RABBIT_CONSUMER_THREADS_COUNT = PROPERTY_MANAGER.getIntValueForKeyWithDefault(RABBIT_CONSUMER_THREADS_COUNT_KEY, 10);
    static final int RABBIT_CONSUMER_THREADS_PREFETCH_COUNT = PROPERTY_MANAGER.getIntValueForKeyWithDefault(RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_KEY, 1);

    // Event Publishing
    static final String SMP_EVENTS_EXCHANGE = PROPERTY_MANAGER.valueForKeyWithDefault(SMP_EVENTS_EXCHANGE_KEY, "iossystems.stockholm.events");
    static final boolean SMP_EVENTS_PUBLISH_ENABLE = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault(SMP_EVENTS_PUBLISH_ENABLE_KEY, true);
    static final boolean SMP_REPORTING_APPLICATION_ENABLE = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault(SMP_REPORTING_APPLICATION_ENABLE_KEY, true);

    // IReporter
    static final String IREPORTER_URL = PROPERTY_MANAGER.valueForKeyWithDefault(IREPORTER_URL_KEY, "https://icloud4-e3.icloud.com");
    static final String IREPORTER_REPORTS_CONFIGURATION_URL = PROPERTY_MANAGER.valueForKeyWithDefault(IREPORTER_REPORTS_CONFIGURATION_URL_KEY, "/e3/rest/1/config/stockholm");
    static final String IREPORTER_AUDIT_CONFIGURATION_URL = PROPERTY_MANAGER.valueForKeyWithDefault(IREPORTER_AUDIT_CONFIGURATION_URL_KEY, "/e3/rest/1/config/stockholm_audit");
    static final String IREPORTER_REPORTS_URL = PROPERTY_MANAGER.valueForKeyWithDefault(IREPORTER_REPORTS_URL_KEY, "/e3/rest/1/stockholm");
    static final String IREPORTER_AUDIT_URL = PROPERTY_MANAGER.valueForKeyWithDefault(IREPORTER_AUDIT_URL_KEY, "/e3/rest/1/stockholm_audit");
    static final String IREPORTER_PUBLISH_KEY = PROPERTY_MANAGER.valueForKeyWithDefault(IREPORTER_PUBLISH_KEY_KEY, "QWERTYUIOPASDF12");
    static final String IREPORTER_CONTENT_TYPE = PROPERTY_MANAGER.valueForKeyWithDefault(IREPORTER_CONTENT_TYPE_KEY, "application/json");
    static final boolean IREPORTER_PUBLISH_ENABLE = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault(IREPORTER_PUBLISH_ENABLE_KEY, true);
    static final int IREPORTER_MAX_BATCH_SIZE = PROPERTY_MANAGER.getIntValueForKeyWithDefault(IREPORTER_MAX_BATCH_SIZE_KEY, 100);
    static final int IREPORTER_PUBLISH_FREQUENCY = PROPERTY_MANAGER.getIntValueForKeyWithDefault(IREPORTER_PUBLISH_FREQUENCY_KEY, 2 * 60 * 1000);
    static final int IREPORTER_CONFIGURATION_RELOAD_FREQUENCY = PROPERTY_MANAGER.getIntValueForKeyWithDefault(IREPORTER_CONFIGURATION_RELOAD_FREQUENCY_KEY, 60 * 60 * 1000);

    // Other configuration settings
    static final String LOG_LEVEL_EVENT_NAME = LogLevel.EVENT.name();

    // Used to set system properties
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