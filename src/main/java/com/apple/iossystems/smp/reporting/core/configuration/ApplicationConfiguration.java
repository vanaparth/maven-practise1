package com.apple.iossystems.smp.reporting.core.configuration;

import com.apple.cds.keystone.config.PropertyManager;

/**
 * @author Toch
 */
public class ApplicationConfiguration
{
    static final String KEYSTONE_RABBIT_HOST = PropertyManager.getInstance().valueForKeyWithDefault("keystone.rabbit.host", "rabbit-np-amqp.corp.apple.com");
    static final String KEYSTONE_RABBIT_PORT = PropertyManager.getInstance().valueForKeyWithDefault("keystone.rabbit.port", "5672");
    static final String KEYSTONE_RABBIT_USER = PropertyManager.getInstance().valueForKeyWithDefault("keystone.rabbit.user", "SMPQA_User");
    static final String KEYSTONE_RABBIT_PASSWORD = PropertyManager.getInstance().valueForKeyWithDefault("keystone.rabbit.pass", "SMPQA_User123");
    static final String KEYSTONE_RABBIT_VIRTUAL_HOST = PropertyManager.getInstance().valueForKeyWithDefault("keystone.rabbit.virtualhost", "SMPQA1");

    static final int RABBIT_CONSUMER_THREADS_COUNT = PropertyManager.getInstance().getIntValueForKeyWithDefault("rabbit.consumerThreads", 10);
    static final int RABBIT_CONSUMER_THREADS_PREFETCH_COUNT = PropertyManager.getInstance().getIntValueForKeyWithDefault("rabbit.consumerThreads.prefetchCount", 1);

    static final String SMP_EVENTS_EXCHANGE = PropertyManager.getInstance().valueForKeyWithDefault("smp.reporting.events.exchange", "iossystems.stockholm.events");

    static final String LOG_SERVICE_OWNER = PropertyManager.getInstance().valueForKeyWithDefault("smp.reporting.logging.owner", "iossystems");
    static final String LOG_SERVICE_PATH = PropertyManager.getInstance().valueForKeyWithDefault("smp.reporting.logging.path", "stockholm");
    static final String LOG_SERVICE_CATEGORY = PropertyManager.getInstance().valueForKeyWithDefault("smp.reporting.logging.category", "events");
    static final String LOG_SERVICE_STORE = PropertyManager.getInstance().valueForKeyWithDefault("smp.reporting.logging.store", "reporting");
    static final String LOG_SERVICE_CLASS = PropertyManager.getInstance().valueForKeyWithDefault("smp.reporting.logging.classname", "com.apple.iossystems.logging.impl.pubsub.BufferedPubSub");

    static final int LOG_SERVICE_BDB_BATCH_SIZE = PropertyManager.getInstance().getIntValueForKeyWithDefault("smp.reporting.logging.bdb.batch.size", 50);
    static final int LOG_SERVICE_BDB_INTERVAL = PropertyManager.getInstance().getIntValueForKeyWithDefault("smp.reporting.logging.bdb.interval.ms", 500);

    static final String BACKLOG_BDB_STORE = PropertyManager.getInstance().valueForKeyWithDefault("smp.reporting.backlog.bdb.store", "backlogReporting");

    static final int MAX_PUBLISH_DOWN_TIME = PropertyManager.getInstance().getIntValueForKeyWithDefault("smp.reporting.maxPublishDownTime", 5 * 60 * 1000);

    static final String IREPORTER_URL = PropertyManager.getInstance().valueForKeyWithDefault("icloud.ireporter.url", "https://icloud4-e3.icloud.com");
    static final String HASH_PASSWORD = PropertyManager.getInstance().valueForKeyWithDefault("icloud.ireporter.pass", "pLijzg2e2QNspdhOyNWdOSScPszmZBryJ0L8BcQ116BhkT6p0iHyNcwnlFIwhLun");

    static final boolean RABBIT_CONSUMERS_ENABLED = PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.rabbit.consumers", true);

    static final boolean PUBLISH_EVENTS_ENABLED = PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.publish.events", true);
    static final boolean EMAIL_EVENTS_ENABLED = PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.email.events", true);

    static final boolean PROVISION_EMAIL_ENABLED = PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.email.provision", true);
    static final boolean SUSPEND_EMAIL_ENABLED = PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.email.suspend", true);
    static final boolean UNLINK_EMAIL_ENABLED = PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.email.unlink", true);
    static final boolean DEFAULT_EMAIL_LOCALE_ENABLED = PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.email.default.locale", true);

    static final String FMIP_CERTIFICATE = PropertyManager.getInstance().valueForKeyWithDefault("com.apple.iossystems.internal.fmip.app.cert", "0");
    static final String FMIP_REMOTE_CERTIFICATE = PropertyManager.getInstance().valueForKeyWithDefault("com.apple.iossystems.internal.fmip.setup.cert", "0");

    private ApplicationConfiguration()
    {
    }

    public static String getKeystoneRabbitHost()
    {
        return KEYSTONE_RABBIT_HOST;
    }

    public static String getKeystoneRabbitPort()
    {
        return KEYSTONE_RABBIT_PORT;
    }

    public static String getKeystoneRabbitUser()
    {
        return KEYSTONE_RABBIT_USER;
    }

    public static String getKeystoneRabbitPassword()
    {
        return KEYSTONE_RABBIT_PASSWORD;
    }

    public static String getKeystoneRabbitVirtualHost()
    {
        return KEYSTONE_RABBIT_VIRTUAL_HOST;
    }

    public static int getRabbitConsumerThreadsCount()
    {
        return RABBIT_CONSUMER_THREADS_COUNT;
    }

    public static int getRabbitConsumerThreadsPrefetchCount()
    {
        return RABBIT_CONSUMER_THREADS_PREFETCH_COUNT;
    }

    public static String getSMPEventsExchangeName()
    {
        return SMP_EVENTS_EXCHANGE;
    }

    public static String getLogServiceOwner()
    {
        return LOG_SERVICE_OWNER;
    }

    public static String getLogServicePath()
    {
        return LOG_SERVICE_PATH;
    }

    public static String getLogServiceCategory()
    {
        return LOG_SERVICE_CATEGORY;
    }

    public static String getLogServiceStore()
    {
        return LOG_SERVICE_STORE;
    }

    public static String getLogServiceClass()
    {
        return LOG_SERVICE_CLASS;
    }

    public static int getLogServiceBdbBatchSize()
    {
        return LOG_SERVICE_BDB_BATCH_SIZE;
    }

    public static int getLogServiceBdbInterval()
    {
        return LOG_SERVICE_BDB_INTERVAL;
    }

    public static String getBacklogBdbStore()
    {
        return BACKLOG_BDB_STORE;
    }

    public static int getMaxPublishDownTime()
    {
        return MAX_PUBLISH_DOWN_TIME;
    }

    public static String getIReporterUrl()
    {
        return IREPORTER_URL;
    }

    public static String getHashPassword()
    {
        return HASH_PASSWORD;
    }

    public static boolean rabbitConsumersEnabled()
    {
        return RABBIT_CONSUMERS_ENABLED;
    }

    public static boolean publishEventsEnabled()
    {
        return PUBLISH_EVENTS_ENABLED;
    }

    public static boolean emailEventsEnabled()
    {
        return EMAIL_EVENTS_ENABLED;
    }

    public static boolean provisionEmailEnabled()
    {
        return PROVISION_EMAIL_ENABLED;
    }

    public static boolean suspendEmailEnabled()
    {
        return SUSPEND_EMAIL_ENABLED;
    }

    public static boolean unlinkEmailEnabled()
    {
        return UNLINK_EMAIL_ENABLED;
    }

    public static boolean defaultEmailLocaleEnabled()
    {
        return DEFAULT_EMAIL_LOCALE_ENABLED;
    }

    public static String getFmipCertificate()
    {
        return FMIP_CERTIFICATE;
    }

    public static String getFmipRemoteCertificate()
    {
        return FMIP_REMOTE_CERTIFICATE;
    }
}