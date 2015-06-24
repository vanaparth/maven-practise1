package com.apple.iossystems.smp.reporting.core.application;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.messaging.SMPReportingService;

/**
 * @author Toch
 */
public class SMPReportingApplication
{
    private static final SMPReportingApplication INSTANCE = new SMPReportingApplication();

    private SMPReportingApplication()
    {
        init();
    }

    public static SMPReportingApplication getInstance()
    {
        return INSTANCE;
    }

    private void init()
    {
        setSystemProperties();

        SMPReportingService.getInstance().start();
    }

    private void setSystemProperties()
    {
        System.setProperty("rabbit.host", ApplicationConfiguration.getKeystoneRabbitHost());
        System.setProperty("rabbit.port", ApplicationConfiguration.getKeystoneRabbitPort());
        System.setProperty("rabbit.user", ApplicationConfiguration.getKeystoneRabbitUser());
        System.setProperty("rabbit.pass", ApplicationConfiguration.getKeystoneRabbitPassword());
        System.setProperty("rabbit.vhost", ApplicationConfiguration.getKeystoneRabbitVirtualHost());
        System.setProperty("rabbit.consumerThreads", String.valueOf(ApplicationConfiguration.getRabbitConsumerThreadsCount()));
        System.setProperty("rabbit.consumerThreads.prefetchCount", String.valueOf(ApplicationConfiguration.getRabbitConsumerThreadsPrefetchCount()));
    }

    public void start()
    {
    }
}