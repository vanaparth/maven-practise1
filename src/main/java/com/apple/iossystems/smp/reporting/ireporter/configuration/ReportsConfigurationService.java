package com.apple.iossystems.smp.reporting.ireporter.configuration;

/**
 * @author Toch
 */
public class ReportsConfigurationService extends IReporterConfigurationService
{
    private ReportsConfigurationService()
    {
    }

    public static ReportsConfigurationService getInstance()
    {
        return new ReportsConfigurationService();
    }

    @Override
    IReporterConfiguration loadConfiguration()
    {
        return IReporterConfigurationFactory.loadReportsConfiguration();
    }
}
