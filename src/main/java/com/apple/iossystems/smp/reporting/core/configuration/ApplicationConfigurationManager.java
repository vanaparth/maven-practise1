package com.apple.iossystems.smp.reporting.core.configuration;

/**
 * @author Toch
 */
public class ApplicationConfigurationManager
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

    public static String getIReporterURL()
    {
        return ApplicationConfiguration.IREPORTER_URL;
    }

    public static String getHashPassword()
    {
        return ApplicationConfiguration.HASH_PASS;
    }

    public static boolean getEmailProvision()
    {
        return ApplicationConfiguration.EMAIL_PROVISION;
    }

    public static boolean getEmailSuspend()
    {
        return ApplicationConfiguration.EMAIL_SUSPEND;
    }

    public static boolean getEmailUnlink()
    {
        return ApplicationConfiguration.EMAIL_UNLINK;
    }

    public static boolean getEmailLocale()
    {
        return ApplicationConfiguration.EMAIL_LOCALE;
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