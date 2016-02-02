package com.apple.iossystems.smp.reporting.ireporter.configuration;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.reporting.core.http.HttpRequest;
import com.apple.iossystems.smp.reporting.core.http.SMPHttpClient;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class IReporterConfigurationFactory
{
    private static final Logger LOGGER = Logger.getLogger(IReporterConfigurationFactory.class);

    private SMPHttpClient smpHttpClient = SMPHttpClient.getInstance();

    private IReporterConfigurationFactory()
    {
    }

    public static IReporterConfigurationFactory getInstance()
    {
        return new IReporterConfigurationFactory();
    }

    public IReporterConfiguration loadReportsConfiguration()
    {
        return load(IReporterConfiguration.Type.REPORTS);
    }

    public IReporterConfiguration loadAuditConfiguration()
    {
        return load(IReporterConfiguration.Type.AUDIT);
    }

    public IReporterConfiguration loadPaymentReportsConfiguration()
    {
        return load(IReporterConfiguration.Type.PAYMENT_REPORTS);
    }

    public IReporterConfiguration loadPaymentAuditConfiguration()
    {
        return load(IReporterConfiguration.Type.PAYMENT_AUDIT);
    }

    public IReporterConfiguration loadLoyaltyReportsConfiguration()
    {
        return load(IReporterConfiguration.Type.LOYALTY_REPORTS);
    }

    public IReporterConfiguration loadLoyaltyAuditConfiguration()
    {
        return load(IReporterConfiguration.Type.LOYALTY_AUDIT);
    }

    private IReporterConfiguration getDefaultConfiguration(IReporterConfiguration.Type configurationType)
    {
        return IReporterConfiguration.getDefaultConfiguration(configurationType);
    }

    private IReporterConfiguration getConfiguration(IReporterConfiguration.Type configurationType, String json)
    {
        return IReporterConfiguration.getConfiguration(configurationType, json);
    }

    private IReporterConfiguration load(IReporterConfiguration.Type configurationType)
    {
        try
        {
            StockholmHTTPResponse response = smpHttpClient.request(HttpRequest.getInstance(configurationType.getConfigurationUrl(), "GET", null, null, null, null));

            if (response != null)
            {
                String content = response.getContent();

                if (response.isSuccessful() && (content != null))
                {
                    return getConfiguration(configurationType, content);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return getDefaultConfiguration(configurationType);
    }
}