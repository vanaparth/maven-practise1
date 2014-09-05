package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.PaymentReportsConfigurationService;

/**
 * @author Toch
 */
public class PaymentReportsPublishService extends IReporterPublishService
{
    private PaymentReportsPublishService() throws Exception
    {
    }

    public static PaymentReportsPublishService getInstance() throws Exception
    {
        return new PaymentReportsPublishService();
    }

    @Override
    public IReporterConfigurationService getConfigurationService()
    {
        return PaymentReportsConfigurationService.getInstance();
    }
}