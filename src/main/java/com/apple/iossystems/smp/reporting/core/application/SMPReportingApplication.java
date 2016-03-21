package com.apple.iossystems.smp.reporting.core.application;

import com.apple.iossystems.smp.reporting.core.messaging.SMPEventSubscriberServiceManager;

/**
 * @author Toch
 */
public class SMPReportingApplication
{
    private static final SMPReportingApplication INSTANCE = new SMPReportingApplication();

    private final SMPEventSubscriberServiceManager smpEventSubscriberServiceManager = SMPEventSubscriberServiceManager.getInstance();

    private SMPReportingApplication()
    {
    }

    public static SMPReportingApplication getInstance()
    {
        return INSTANCE;
    }

    public void start()
    {
        smpEventSubscriberServiceManager.start();
    }

    public void shutdown()
    {
        smpEventSubscriberServiceManager.shutdown();
    }
}