package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.ReportsConfigurationService;

/**
 * @author Toch
 */
public class ReportsPublishService extends IReporterPublishService
{
    private ReportsPublishService() throws Exception
    {
    }

    public static ReportsPublishService getInstance() throws Exception
    {
        return new ReportsPublishService();
    }

    @Override
    public IReporterConfigurationService getConfigurationService()
    {
        return ReportsConfigurationService.getInstance();
    }
}
