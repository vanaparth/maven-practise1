package com.apple.iossystems.smp.reporting.application;

import com.apple.cds.messaging.client.impl.SMPEventExchangeManager;
import com.apple.iossystems.smp.reporting.config.SMPReportingApplicationConfiguration;

/**
 * @author Toch
 */
public class SMPReportingApplication
{
    private SMPReportingApplication()
    {
        setSystemProperties();

        SMPEventExchangeManager.start();
    }

    private static SMPReportingApplication getInstance()
    {
        return new SMPReportingApplication();
    }

    public static void start()
    {
        if (SMPReportingApplicationConfiguration.smpReportingApplicationEnabled())
        {
            getInstance();
        }
    }

    private void setSystemProperties()
    {
        System.setProperty(SMPReportingApplicationConfiguration.getSMPEventsPublishEnableKey(), String.valueOf(SMPReportingApplicationConfiguration.smpEventsPublishEnabled()));
        System.setProperty(SMPReportingApplicationConfiguration.getRabbitHostKey(), SMPReportingApplicationConfiguration.getKeystoneRabbitHost());
        System.setProperty(SMPReportingApplicationConfiguration.getRabbitPortKey(), SMPReportingApplicationConfiguration.getKeystoneRabbitPort());
        System.setProperty(SMPReportingApplicationConfiguration.getRabbitUserKey(), SMPReportingApplicationConfiguration.getKeystoneRabbitUser());
        System.setProperty(SMPReportingApplicationConfiguration.getRabbitPassKey(), SMPReportingApplicationConfiguration.getKeystoneRabbitPass());
        System.setProperty(SMPReportingApplicationConfiguration.getRabbitVHostKey(), SMPReportingApplicationConfiguration.getKeystoneRabbitVHost());
        System.setProperty(SMPReportingApplicationConfiguration.getRabbitConsumerThreadsKey(), String.valueOf(SMPReportingApplicationConfiguration.getRabbitConsumerThreadsCount()));
        System.setProperty(SMPReportingApplicationConfiguration.getRabbitConsumerThreadsPrefetchCountKey(), String.valueOf(SMPReportingApplicationConfiguration.getRabbitConsumerThreadsPrefetchCount()));
    }
}
