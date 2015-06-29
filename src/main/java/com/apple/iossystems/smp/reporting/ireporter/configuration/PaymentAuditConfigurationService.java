package com.apple.iossystems.smp.reporting.ireporter.configuration;

/**
 * @author Toch
 */
public class PaymentAuditConfigurationService extends IReporterConfigurationService
{
    private PaymentAuditConfigurationService()
    {
    }

    public static PaymentAuditConfigurationService getInstance()
    {
        return new PaymentAuditConfigurationService();
    }

    @Override
    IReporterConfiguration loadConfiguration()
    {
        return getIReporterConfigurationFactory().loadPaymentAuditConfiguration();
    }

    @Override
    ConfigurationMetric getConfigurationMetric()
    {
        return ConfigurationMetric.PAYMENT_AUDIT;
    }
}