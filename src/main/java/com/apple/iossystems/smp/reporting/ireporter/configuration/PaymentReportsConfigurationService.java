package com.apple.iossystems.smp.reporting.ireporter.configuration;

/**
 * @author Toch
 */
public class PaymentReportsConfigurationService extends IReporterConfigurationService
{
    private PaymentReportsConfigurationService()
    {
    }

    public static PaymentReportsConfigurationService getInstance()
    {
        return new PaymentReportsConfigurationService();
    }

    @Override
    IReporterConfiguration loadConfiguration()
    {
        return IReporterConfigurationFactory.loadPaymentReportsConfiguration();
    }
}