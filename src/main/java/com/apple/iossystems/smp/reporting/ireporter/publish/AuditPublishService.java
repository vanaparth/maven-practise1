package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.ireporter.configuration.AuditConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;

/**
 * @author Toch
 */
public class AuditPublishService extends IReporterPublishService
{
    private AuditPublishService() throws Exception
    {
    }

    public static AuditPublishService getInstance() throws Exception
    {
        return new AuditPublishService();
    }

    @Override
    public IReporterConfigurationService getConfigurationService()
    {
        return AuditConfigurationService.getInstance();
    }
}