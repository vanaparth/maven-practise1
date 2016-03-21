package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.PaymentReportsConfigurationService;

/**
 * @author Toch
 */
class PaymentReportsPublishService extends IReporterPublishService
{
    private PaymentReportsPublishService()
    {
    }

    static PaymentReportsPublishService getInstance()
    {
        return new PaymentReportsPublishService();
    }

    @Override
    public IReporterConfigurationService getConfigurationService()
    {
        return PaymentReportsConfigurationService.getInstance();
    }
}