package com.apple.iossystems.smp.reporting.core.application;

import com.apple.iossystems.smp.reporting.core.messaging.SMPReportingService;

/**
 * @author Toch
 */
public class SMPReportingApplication
{
    private static final SMPReportingApplication INSTANCE = new SMPReportingApplication();

    private SMPReportingApplication()
    {
        SMPReportingService.getInstance().start();
    }

    public static SMPReportingApplication getInstance()
    {
        return INSTANCE;
    }

    public void start()
    {
    }
}