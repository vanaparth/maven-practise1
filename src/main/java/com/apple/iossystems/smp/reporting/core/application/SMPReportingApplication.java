package com.apple.iossystems.smp.reporting.core.application;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
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
        System.setProperty("rabbit.host", ApplicationConfigurationManager.getKeystoneRabbitHost());
        System.setProperty("rabbit.port", ApplicationConfigurationManager.getKeystoneRabbitPort());
        System.setProperty("rabbit.user", ApplicationConfigurationManager.getKeystoneRabbitUser());
        System.setProperty("rabbit.pass", ApplicationConfigurationManager.getKeystoneRabbitPassword());
        System.setProperty("rabbit.vhost", ApplicationConfigurationManager.getKeystoneRabbitVirtualHost());
        System.setProperty("rabbit.consumerThreads", String.valueOf(ApplicationConfigurationManager.getRabbitConsumerThreadsCount()));
        System.setProperty("rabbit.consumerThreads.prefetchCount", String.valueOf(ApplicationConfigurationManager.getRabbitConsumerThreadsPrefetchCount()));
    }

    public void start()
    {
    }
}