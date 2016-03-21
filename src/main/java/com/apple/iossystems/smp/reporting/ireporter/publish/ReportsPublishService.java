package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.ReportsConfigurationService;

/**
 * @author Toch
 */
class ReportsPublishService extends IReporterPublishService
{
    private ReportsPublishService()
    {
    }

    static ReportsPublishService getInstance()
    {
        return new ReportsPublishService();
    }

    @Override
    public IReporterConfigurationService getConfigurationService()
    {
        return ReportsConfigurationService.getInstance();
    }
}