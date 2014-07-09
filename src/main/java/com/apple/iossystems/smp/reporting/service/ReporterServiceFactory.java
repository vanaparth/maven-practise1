package com.apple.iossystems.smp.reporting.service;

/**
 * @author Toch
 */
public class ReporterServiceFactory
{
    public static ReporterService newIReporter() throws Exception
    {
        return ReporterServiceIReporter.newInstance();
    }
}
