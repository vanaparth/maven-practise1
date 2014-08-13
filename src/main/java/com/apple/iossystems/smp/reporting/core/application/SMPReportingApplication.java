package com.apple.iossystems.smp.reporting.core.application;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
import com.apple.iossystems.smp.reporting.core.messaging.SMPEventExchangeManager;

/**
 * @author Toch
 */
public class SMPReportingApplication
{
    private static final SMPReportingApplication INSTANCE = new SMPReportingApplication();

    private boolean started = false;

    private SMPReportingApplication()
    {
        setSystemProperties();
    }

    private static SMPReportingApplication getInstance()
    {
        return INSTANCE;
    }

    private void setSystemProperties()
    {
        System.setProperty(ApplicationConfigurationManager.getRabbitHostKey(), ApplicationConfigurationManager.getKeystoneRabbitHost());
        System.setProperty(ApplicationConfigurationManager.getRabbitPortKey(), ApplicationConfigurationManager.getKeystoneRabbitPort());
        System.setProperty(ApplicationConfigurationManager.getRabbitUserKey(), ApplicationConfigurationManager.getKeystoneRabbitUser());
        System.setProperty(ApplicationConfigurationManager.getRabbitPassKey(), ApplicationConfigurationManager.getKeystoneRabbitPass());
        System.setProperty(ApplicationConfigurationManager.getRabbitVHostKey(), ApplicationConfigurationManager.getKeystoneRabbitVHost());
        System.setProperty(ApplicationConfigurationManager.getRabbitConsumerThreadsCountKey(), String.valueOf(ApplicationConfigurationManager.getRabbitConsumerThreadsCount()));
        System.setProperty(ApplicationConfigurationManager.getRabbitConsumerThreadsPrefetchCountKey(), String.valueOf(ApplicationConfigurationManager.getRabbitConsumerThreadsPrefetchCount()));
        System.setProperty(ApplicationConfigurationManager.getSMPEventsPublishEnableKey(), String.valueOf(ApplicationConfigurationManager.getSMPEventsPublishEnable()));
    }

    private boolean isEnabled()
    {
        return ApplicationConfigurationManager.getSMPReportingApplicationEnable();
    }

    private synchronized void startApplication()
    {
        if (isEnabled() && !started)
        {
            startSMPEventExchangeManager();

            started = true;
        }
    }

    private void startSMPEventExchangeManager()
    {
        SMPEventExchangeManager.getInstance().start();
    }

    public static void start()
    {
        getInstance().startApplication();
    }
}
