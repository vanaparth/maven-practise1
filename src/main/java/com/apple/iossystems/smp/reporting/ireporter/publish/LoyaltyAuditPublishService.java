package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.LoyaltyAuditConfigurationService;

/**
 * Created by scottblakesley on 12/15/15.
 */
public class LoyaltyAuditPublishService extends IReporterPublishService {
    private LoyaltyAuditPublishService() {
    }

    public static LoyaltyAuditPublishService getInstance()
    {
        return new LoyaltyAuditPublishService();
    }

    @Override
    public IReporterConfigurationService getConfigurationService()
    {
        return LoyaltyAuditConfigurationService.getInstance();
    }
}
