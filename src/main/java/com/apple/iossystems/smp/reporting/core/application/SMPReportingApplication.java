package com.apple.iossystems.smp.reporting.core.application;

import com.apple.cds.messaging.client.impl.SMPEventExchangeManager;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;

/**
 * @author Toch
 */
public class SMPReportingApplication
{
    private static SMPReportingApplication INSTANCE = new SMPReportingApplication();

    private boolean isStarted = false;

    private SMPReportingApplication()
    {
        setSystemProperties();
    }

    public static SMPReportingApplication getInstance()
    {
        return INSTANCE;
    }

    private void setSystemProperties()
    {
        System.setProperty(ApplicationConfiguration.RABBIT_HOST_KEY, ApplicationConfiguration.KEYSTONE_RABBIT_HOST_VALUE);
        System.setProperty(ApplicationConfiguration.RABBIT_PORT_KEY, ApplicationConfiguration.KEYSTONE_RABBIT_PORT_VALUE);
        System.setProperty(ApplicationConfiguration.RABBIT_USER_KEY, ApplicationConfiguration.KEYSTONE_RABBIT_USER_VALUE);
        System.setProperty(ApplicationConfiguration.RABBIT_PASS_KEY, ApplicationConfiguration.KEYSTONE_RABBIT_PASS_VALUE);
        System.setProperty(ApplicationConfiguration.RABBIT_VHOST_KEY, ApplicationConfiguration.KEYSTONE_RABBIT_VHOST_VALUE);
        System.setProperty(ApplicationConfiguration.RABBIT_CONSUMER_THREADS_KEY, ApplicationConfiguration.RABBIT_CONSUMER_THREADS_VALUE);
        System.setProperty(ApplicationConfiguration.RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_KEY, ApplicationConfiguration.RABBIT_CONSUMER_THREADS_PREFETCH_COUNT_VALUE);
        System.setProperty(ApplicationConfiguration.SMP_EVENTS_PUBLISH_ENABLE_KEY, String.valueOf(ApplicationConfiguration.SMP_EVENTS_PUBLISH_ENABLE_VALUE));
    }

    private boolean isEnabled()
    {
        return ApplicationConfiguration.SMP_REPORTING_APPLICATION_ENABLE_VALUE;
    }

    private synchronized void begin()
    {
        if (isEnabled() && !isStarted)
        {
            SMPEventExchangeManager.getInstance().start();

            isStarted = true;
        }
    }

    public static void start()
    {
        getInstance().begin();
    }
}
