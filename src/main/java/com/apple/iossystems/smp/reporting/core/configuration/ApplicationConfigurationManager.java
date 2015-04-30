package com.apple.iossystems.smp.reporting.core.configuration;

/**
 * @author Toch
 */
public class ApplicationConfigurationManager
{
    private ApplicationConfigurationManager()
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

    public static String getIReporterURL()
    {
        return ApplicationConfiguration.IREPORTER_URL;
    }

    public static String getHashPassword()
    {
        return ApplicationConfiguration.HASH_PASSWORD;
    }

    public static boolean isEmailLoggingEnabled()
    {
        return ApplicationConfiguration.EMAIL_LOGGING_ENABLED;
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