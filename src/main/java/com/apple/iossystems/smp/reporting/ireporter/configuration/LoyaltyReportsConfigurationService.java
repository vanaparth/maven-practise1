package com.apple.iossystems.smp.reporting.ireporter.configuration;

/**
 * Created by scottblakesley on 12/15/15.
 */
public class LoyaltyReportsConfigurationService extends IReporterConfigurationService
{
    private LoyaltyReportsConfigurationService()
    {
    }

    public static LoyaltyReportsConfigurationService getInstance()
    {
        return new LoyaltyReportsConfigurationService();
    }

    @Override
    IReporterConfiguration loadConfiguration()
    {
        return getIReporterConfigurationFactory().loadLoyaltyReportsConfiguration();
    }

    @Override
    ConfigurationMetric getConfigurationMetric()
    {
        return ConfigurationMetric.LOYALTY_REPORTS;
    }
}