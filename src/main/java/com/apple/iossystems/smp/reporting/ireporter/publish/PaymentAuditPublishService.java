package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.PaymentAuditConfigurationService;

/**
 * @author Toch
 */
public class PaymentAuditPublishService extends IReporterPublishService
{
    private PaymentAuditPublishService()
    {
    }

    public static PaymentAuditPublishService getInstance()
    {
        return new PaymentAuditPublishService();
    }

    @Override
    public IReporterConfigurationService getConfigurationService()
    {
        return PaymentAuditConfigurationService.getInstance();
    }
}