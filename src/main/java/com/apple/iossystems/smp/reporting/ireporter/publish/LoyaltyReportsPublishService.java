package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.LoyaltyReportsConfigurationService;

/**
 * Created by scottblakesley on 12/15/15.
 */
public class LoyaltyReportsPublishService extends IReporterPublishService {

    private LoyaltyReportsPublishService() { }

    public static LoyaltyReportsPublishService getInstance(){
        return new LoyaltyReportsPublishService();
    }

    @Override
    public IReporterConfigurationService getConfigurationService() {
        return LoyaltyReportsConfigurationService.getInstance();
    }
}
