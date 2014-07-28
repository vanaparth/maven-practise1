package com.apple.iossystems.smp.reporting.ireporter.configuration;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.reporting.core.http.HttpRequestParameter;
import com.apple.iossystems.smp.reporting.core.http.SMPHttpClient;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class IReporterConfigurationFactory
{
    private static final Logger LOGGER = Logger.getLogger(IReporterConfigurationFactory.class);

    private IReporterConfigurationFactory()
    {
    }

    private static IReporterConfiguration getDefaultConfiguration(IReporterConfiguration.Type configurationType)
    {
        return IReporterConfiguration.Builder.fromDefault(configurationType);
    }

    private static IReporterConfiguration getConfigurationFromJson(String json)
    {
        return IReporterConfiguration.Builder.fromJson(json);
    }

    static IReporterConfiguration loadReportsConfiguration()
    {
        return load(IReporterConfiguration.Type.REPORTS);
    }

    static IReporterConfiguration loadAuditConfiguration()
    {
        return load(IReporterConfiguration.Type.AUDIT);
    }

    private static IReporterConfiguration load(IReporterConfiguration.Type configurationType)
    {
        try
        {
            SMPHttpClient httpClient = SMPHttpClient.getInstance();

            Map<HttpRequestParameter, Object> request = new HashMap<HttpRequestParameter, Object>();

            request.put(HttpRequestParameter.HTTP_METHOD, "GET");
            request.put(HttpRequestParameter.URL, configurationType.getConfigurationURL());

            StockholmHTTPResponse response = httpClient.request(request);
            String content = response.getContent();

            if (response.isSuccessful() && content != null)
            {
                return getConfigurationFromJson(content);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return getDefaultConfiguration(configurationType);
    }
}
