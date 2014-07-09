package com.apple.iossystems.smp.reporting.core;

/**
 * @author Toch
 */
public class ReporterManagerFactory
{
    public SMPReporterManager newIReporterManager() throws Exception
    {
        return IReporterManager.newInstance();
    }
}
