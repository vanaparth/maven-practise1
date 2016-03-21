package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.ireporter.configuration.AuditConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;

/**
 * @author Toch
 */
class AuditPublishService extends IReporterPublishService
{
    private AuditPublishService()
    {
    }

    static AuditPublishService getInstance()
    {
        return new AuditPublishService();
    }

    @Override
    public IReporterConfigurationService getConfigurationService()
    {
        return AuditConfigurationService.getInstance();
    }
}