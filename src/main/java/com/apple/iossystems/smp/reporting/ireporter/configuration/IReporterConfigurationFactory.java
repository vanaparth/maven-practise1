package com.apple.iossystems.smp.reporting.ireporter.configuration;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.reporting.core.http.HttpRequest;
import com.apple.iossystems.smp.reporting.core.http.SMPHttpClient;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class IReporterConfigurationFactory
{
    private static final Logger LOGGER = Logger.getLogger(IReporterConfigurationFactory.class);

    private static final SMPHttpClient SMP_HTTP_CLIENT = SMPHttpClient.getInstance();

    private IReporterConfigurationFactory()
    {
    }

    public static IReporterConfigurationFactory getInstance()
    {
        return new IReporterConfigurationFactory();
    }

    private static IReporterConfiguration getDefaultConfiguration(IReporterConfiguration.Type configurationType)
    {
        return IReporterConfiguration.getDefaultConfiguration(configurationType);
    }

    private static IReporterConfiguration getConfiguration(IReporterConfiguration.Type configurationType, String json)
    {
        return IReporterConfiguration.getConfiguration(configurationType, json);
    }

    static IReporterConfiguration loadReportsConfiguration()
    {
        return load(IReporterConfiguration.Type.REPORTS);
    }

    static IReporterConfiguration loadAuditConfiguration()
    {
        return load(IReporterConfiguration.Type.AUDIT);
    }

    static IReporterConfiguration loadPaymentReportsConfiguration()
    {
        return load(IReporterConfiguration.Type.PAYMENT_REPORTS);
    }

    static IReporterConfiguration loadPaymentAuditConfiguration()
    {
        return load(IReporterConfiguration.Type.PAYMENT_AUDIT);
    }

    private static IReporterConfiguration load(IReporterConfiguration.Type configurationType)
    {
        try
        {
            StockholmHTTPResponse response = SMP_HTTP_CLIENT.request(HttpRequest.getInstance(configurationType.getConfigurationURL(), "GET", null, null, null, null));

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
            LOGGER.error(e);
        }

        return getDefaultConfiguration(configurationType);
    }
}