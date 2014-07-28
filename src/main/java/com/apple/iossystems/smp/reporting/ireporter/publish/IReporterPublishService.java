package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.reporting.core.http.HttpRequestParameter;
import com.apple.iossystems.smp.reporting.core.http.HttpResponseAction;
import com.apple.iossystems.smp.reporting.core.http.SMPHttpClient;
import com.apple.iossystems.smp.reporting.core.timer.Timer;
import com.apple.iossystems.smp.reporting.ireporter.analytics.IReporterAnalytics;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfiguration;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.http.IReporterResponseHandler;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
abstract class IReporterPublishService
{
    private static final Logger LOGGER = Logger.getLogger(IReporterPublishService.class);

    private IReporterConfigurationService configurationService = getConfigurationService();

    private SMPHttpClient httpClient = SMPHttpClient.getInstance();

    private IReporterAnalytics analytics;

    IReporterPublishService(IReporterAnalytics analytics) throws Exception
    {
        this.analytics = analytics;
    }

    abstract IReporterConfigurationService getConfigurationService();

    abstract boolean publishReady();

    abstract long getLastPublishTime();

    final IReporterConfiguration getConfiguration()
    {
        return configurationService.getConfiguration();
    }

    final boolean sendRequest(String data)
    {
        int maxRetryCount = 3;
        int retryCount = 0;

        boolean success = false;

        while ((!success) && (retryCount < maxRetryCount))
        {
            try
            {
                StockholmHTTPResponse response = httpClient.request(getHTTPRequest(data));

                success = isRequestSuccessful(IReporterResponseHandler.getAction(response));
            }
            catch (Exception e)
            {
                LOGGER.error(e);
            }

            retryCount++;
        }

        return success;
    }

    private Map<HttpRequestParameter, Object> getHTTPRequest(String data)
    {
        IReporterConfiguration configuration = getConfiguration();
        Map<HttpRequestParameter, Object> request = new HashMap<HttpRequestParameter, Object>();

        request.put(HttpRequestParameter.HTTP_METHOD, "POST");
        request.put(HttpRequestParameter.URL, configuration.getPublishURL());
        request.put(HttpRequestParameter.CONTENT_TYPE, configuration.getContentType());
        request.put(HttpRequestParameter.HEADERS, configuration.getRequestHeaders());
        request.put(HttpRequestParameter.DATA, data);

        return request;
    }

    private boolean isRequestSuccessful(HttpResponseAction action)
    {
        return (action.equals(HttpResponseAction.NO_ACTION_SUCCESS));
    }

    private boolean publishEnabled()
    {
        return getConfiguration().isPublishEnabled();
    }

    final boolean publishDelayExpired()
    {
        return Timer.delayExpired(getLastPublishTime(), getConfiguration().getPublishFrequency());
    }

    final boolean doPublish()
    {
        return (publishEnabled() && publishReady());
    }

    final IReporterAnalytics getAnalytics()
    {
        return analytics;
    }
}
