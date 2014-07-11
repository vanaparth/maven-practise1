package com.apple.iossystems.smp.reporting.config;

import com.apple.cds.keystone.config.PropertyManager;

/**
 * @author Toch
 */
public class SMPReportingApplicationConfiguration extends ApplicationConfiguration
{
    private SMPReportingApplicationConfiguration()
    {
    }

    public static PropertyManager getPropertyManager()
    {
        return ApplicationConfiguration.PROPERTY_MANAGER;
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

    public static String getRabbitConsumerThreadsKey()
    {
        return ApplicationConfiguration.RABBIT_CONSUMER_THREADS_KEY;
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
        return ApplicationConfiguration.KEYSTONE_RABBIT_HOST_VALUE;
    }

    public static String getKeystoneRabbitPort()
    {
        return ApplicationConfiguration.KEYSTONE_RABBIT_PORT_VALUE;
    }

    public static String getKeystoneRabbitUser()
    {
        return ApplicationConfiguration.KEYSTONE_RABBIT_USER_VALUE;
    }

    public static String getKeystoneRabbitPass()
    {
        return ApplicationConfiguration.KEYSTONE_RABBIT_PASS_VALUE;
    }

    public static String getKeystoneRabbitVHost()
    {
        return ApplicationConfiguration.KEYSTONE_RABBIT_VHOST_VALUE;
    }

    public static int getRabbitConsumerThreadsCount()
    {
        return Integer.valueOf(ApplicationConfiguration.RABBIT_CONSUMER_THREADS_VALUE);
    }

    public static int getRabbitConsumerThreadsPrefetchCount()
    {
        return Integer.valueOf(ApplicationConfiguration.RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_VALUE);
    }

    public static String getSMPEventsExchangeName()
    {
        return ApplicationConfiguration.SMP_EVENTS_EXCHANGE_VALUE;
    }

    public static boolean smpEventsPublishEnabled()
    {
        return ApplicationConfiguration.SMP_EVENTS_PUBLISH_ENABLE_VALUE;
    }

    public static boolean smpReportingApplicationEnabled()
    {
        return ApplicationConfiguration.SMP_REPORTING_APPLICATION_ENABLE_VALUE;
    }

    public static String getLogLevelEventName()
    {
        return ApplicationConfiguration.LOG_LEVEL_EVENT_NAME;
    }
}
