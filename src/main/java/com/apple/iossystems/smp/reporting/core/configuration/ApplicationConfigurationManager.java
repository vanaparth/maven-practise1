package com.apple.iossystems.smp.reporting.core.configuration;

/**
 * @author Toch
 */
public abstract class ApplicationConfigurationManager
{
    private ApplicationConfigurationManager()
    {
    }

    public static String getRabbitHostKey()
    {
        return ApplicationConfiguration.RABBIT_HOST_KEY;
    }

    public static String getRabbitPortKey()
    {
        return ApplicationConfiguration.RABBIT_PORT_KEY;
    }

    public static String getRabbitUserKey()
    {
        return ApplicationConfiguration.RABBIT_USER_KEY;
    }

    public static String getRabbitPassKey()
    {
        return ApplicationConfiguration.RABBIT_PASS_KEY;
    }

    public static String getRabbitVHostKey()
    {
        return ApplicationConfiguration.RABBIT_VHOST_KEY;
    }

    public static String getRabbitConsumerThreadsCountKey()
    {
        return ApplicationConfiguration.RABBIT_CONSUMER_THREADS_COUNT_KEY;
    }

    public static String getRabbitConsumerThreadsPrefetchCountKey()
    {
        return ApplicationConfiguration.RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_KEY;
    }

    public static String getSMPEventsPublishEnableKey()
    {
        return ApplicationConfiguration.SMP_EVENTS_PUBLISH_ENABLE_KEY;
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

    public static String getKeystoneRabbitPass()
    {
        return ApplicationConfiguration.KEYSTONE_RABBIT_PASS;
    }

    public static String getKeystoneRabbitVHost()
    {
        return ApplicationConfiguration.KEYSTONE_RABBIT_VHOST;
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

    public static boolean getSMPEventsPublishEnable()
    {
        return ApplicationConfiguration.SMP_EVENTS_PUBLISH_ENABLE;
    }

    public static boolean getSMPReportingApplicationEnable()
    {
        return ApplicationConfiguration.SMP_REPORTING_APPLICATION_ENABLE;
    }

    public static String getLogLevelEventName()
    {
        return ApplicationConfiguration.LOG_LEVEL_EVENT_NAME;
    }

    public static String getIReporterURL()
    {
        return ApplicationConfiguration.IREPORTER_URL;
    }

    public static String getIReporterReportsConfigurationURL()
    {
        return ApplicationConfiguration.IREPORTER_REPORTS_CONFIGURATION_URL;
    }

    public static String getIReporterAuditConfigurationURL()
    {
        return ApplicationConfiguration.IREPORTER_AUDIT_CONFIGURATION_URL;
    }

    public static String getIReporterReportsURL()
    {
        return ApplicationConfiguration.IREPORTER_REPORTS_URL;
    }

    public static String getIReporterAuditURL()
    {
        return ApplicationConfiguration.IREPORTER_AUDIT_URL;
    }

    public static String getIReporterPublishKey()
    {
        return ApplicationConfiguration.IREPORTER_PUBLISH_KEY;
    }

    public static String getIReporterContentType()
    {
        return ApplicationConfiguration.IREPORTER_CONTENT_TYPE;
    }

    public static boolean getIReporterPublishEnable()
    {
        return ApplicationConfiguration.IREPORTER_PUBLISH_ENABLE;
    }

    public static int getIReporterMaxBatchSize()
    {
        return ApplicationConfiguration.IREPORTER_MAX_BATCH_SIZE;
    }

    public static int getIReporterPublishFrequency()
    {
        return ApplicationConfiguration.IREPORTER_PUBLISH_FREQUENCY;
    }

    public static int getIReporterConfigurationReloadFrequency()
    {
        return ApplicationConfiguration.IREPORTER_CONFIGURATION_RELOAD_FREQUENCY;
    }
}
