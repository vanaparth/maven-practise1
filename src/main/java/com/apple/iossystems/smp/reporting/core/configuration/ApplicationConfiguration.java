package com.apple.iossystems.smp.reporting.core.configuration;

import com.apple.cds.keystone.config.PropertyManager;

/**
 * @author Toch
 */
public class ApplicationConfiguration
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
    static final String LOG_SERVICE_CLASS = PROPERTY_MANAGER.valueForKeyWithDefault("smp.reporting.logging.classname", "com.apple.iossystems.logging.impl.pubsub.BufferedPubSub");

    static final int LOG_SERVICE_BDB_BATCH_SIZE = PROPERTY_MANAGER.getIntValueForKeyWithDefault("smp.reporting.logging.bdb.batch.size", 50);
    static final int LOG_SERVICE_BDB_INTERVAL = PROPERTY_MANAGER.getIntValueForKeyWithDefault("smp.reporting.logging.bdb.interval.ms", 500);

    static final String IREPORTER_URL = PROPERTY_MANAGER.valueForKeyWithDefault("icloud.ireporter.url", "https://icloud4-e3.icloud.com");
    static final String HASH_PASSWORD = PROPERTY_MANAGER.valueForKeyWithDefault("icloud.ireporter.pass", "pLijzg2e2QNspdhOyNWdOSScPszmZBryJ0L8BcQ116BhkT6p0iHyNcwnlFIwhLun");

    static final boolean RABBIT_CONSUMERS_ENABLED = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault("smp.reporting.rabbit.consumers", true);

    static final boolean PROVISION_EMAIL_ENABLED = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault("smp.reporting.email.provision", true);
    static final boolean SUSPEND_EMAIL_ENABLED = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault("smp.reporting.email.suspend", true);
    static final boolean UNLINK_EMAIL_ENABLED = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault("smp.reporting.email.unlink", true);
    static final boolean DEFAULT_EMAIL_LOCALE_ENABLED = PROPERTY_MANAGER.getBooleanValueForKeyWithDefault("smp.reporting.email.default.locale", true);

    static final String FMIP_CERTIFICATE = PROPERTY_MANAGER.valueForKeyWithDefault("com.apple.iossystems.internal.fmip.app.cert", "0");
    static final String FMIP_REMOTE_CERTIFICATE = PROPERTY_MANAGER.valueForKeyWithDefault("com.apple.iossystems.internal.fmip.setup.cert", "0");

    private ApplicationConfiguration()
    {
    }

    public static String getKeystoneRabbitHost()
    {
        return ApplicationConfiguration.KEYSTONE_RABBIT_HOST;
    }

    public static String getKeystoneRabbitPort()
    {
        return ApplicationConfiguration.KEYSTONE_RABBIT_PORT;
    }

    public static String getKeystoneRabbitUser()
    {
        return ApplicationConfiguration.KEYSTONE_RABBIT_USER;
    }

    public static String getKeystoneRabbitPassword()
    {
        return ApplicationConfiguration.KEYSTONE_RABBIT_PASSWORD;
    }

    public static String getKeystoneRabbitVirtualHost()
    {
        return ApplicationConfiguration.KEYSTONE_RABBIT_VIRTUAL_HOST;
    }

    public static int getRabbitConsumerThreadsCount()
    {
        return ApplicationConfiguration.RABBIT_CONSUMER_THREADS_COUNT;
    }

    public static int getRabbitConsumerThreadsPrefetchCount()
    {
        return ApplicationConfiguration.RABBIT_CONSUMER_THREADS_PREFETCH_COUNT;
    }

    public static String getSMPEventsExchangeName()
    {
        return ApplicationConfiguration.SMP_EVENTS_EXCHANGE;
    }

    public static String getLogServiceOwner()
    {
        return ApplicationConfiguration.LOG_SERVICE_OWNER;
    }

    public static String getLogServicePath()
    {
        return ApplicationConfiguration.LOG_SERVICE_PATH;
    }

    public static String getLogServiceCategory()
    {
        return ApplicationConfiguration.LOG_SERVICE_CATEGORY;
    }

    public static String getLogServiceStore()
    {
        return ApplicationConfiguration.LOG_SERVICE_STORE;
    }

    public static String getLogServiceClass()
    {
        return ApplicationConfiguration.LOG_SERVICE_CLASS;
    }

    public static int getLogServiceBdbBatchSize()
    {
        return ApplicationConfiguration.LOG_SERVICE_BDB_BATCH_SIZE;
    }

    public static int getLogServiceBdbInterval()
    {
        return ApplicationConfiguration.LOG_SERVICE_BDB_INTERVAL;
    }

    public static String getIReporterUrl()
    {
        return ApplicationConfiguration.IREPORTER_URL;
    }

    public static String getHashPassword()
    {
        return ApplicationConfiguration.HASH_PASSWORD;
    }

    public static boolean isRabbitConsumersEnabled()
    {
        return ApplicationConfiguration.RABBIT_CONSUMERS_ENABLED;
    }

    public static boolean isProvisionEmailEnabled()
    {
        return ApplicationConfiguration.PROVISION_EMAIL_ENABLED;
    }

    public static boolean isSuspendEmailEnabled()
    {
        return ApplicationConfiguration.SUSPEND_EMAIL_ENABLED;
    }

    public static boolean isUnlinkEmailEnabled()
    {
        return ApplicationConfiguration.UNLINK_EMAIL_ENABLED;
    }

    public static boolean isDefaultEmailLocaleEnabled()
    {
        return ApplicationConfiguration.DEFAULT_EMAIL_LOCALE_ENABLED;
    }

    public static String getFmipCertificate()
    {
        return ApplicationConfiguration.FMIP_CERTIFICATE;
    }

    public static String getFmipRemoteCertificate()
    {
        return ApplicationConfiguration.FMIP_REMOTE_CERTIFICATE;
    }
}