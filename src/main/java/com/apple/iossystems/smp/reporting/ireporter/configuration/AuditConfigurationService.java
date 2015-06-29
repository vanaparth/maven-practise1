package com.apple.iossystems.smp.reporting.ireporter.configuration;

/**
 * @author Toch
 */
public class AuditConfigurationService extends IReporterConfigurationService
{
    private AuditConfigurationService()
    {
    }

    public static AuditConfigurationService getInstance()
    {
        return new AuditConfigurationService();
    }

    @Override
    IReporterConfiguration loadConfiguration()
    {
        return getIReporterConfigurationFactory().loadAuditConfiguration();
    }

    @Override
    ConfigurationMetric getConfigurationMetric()
    {
        return ConfigurationMetric.AUDIT;
    }
}