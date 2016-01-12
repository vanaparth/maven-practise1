package com.apple.iossystems.smp.reporting.ireporter.configuration;

/**
 * Created by scottblakesley on 12/15/15.
 */
public class LoyaltyAuditConfigurationService extends IReporterConfigurationService {
    private LoyaltyAuditConfigurationService() {
    }

    public static LoyaltyAuditConfigurationService getInstance() {
        return new LoyaltyAuditConfigurationService();
    }

    @Override
    IReporterConfiguration loadConfiguration() {
        return getIReporterConfigurationFactory().loadLoyaltyAuditConfiguration();
    }

    @Override
    ConfigurationMetric getConfigurationMetric() {
        return ConfigurationMetric.LOYALTY_AUDIT;
    }
}
