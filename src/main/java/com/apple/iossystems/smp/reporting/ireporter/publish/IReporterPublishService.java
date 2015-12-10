package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.reporting.core.http.HttpRequest;
import com.apple.iossystems.smp.reporting.core.http.HttpResponseAction;
import com.apple.iossystems.smp.reporting.core.http.SMPHttpClient;
import com.apple.iossystems.smp.reporting.core.timer.Timer;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfiguration;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.http.IReporterResponseHandler;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public abstract class IReporterPublishService
{
    private static final Logger LOGGER = Logger.getLogger(IReporterPublishService.class);

    private IReporterConfigurationService configurationService = getConfigurationService();

    private SMPHttpClient smpHttpClient = SMPHttpClient.getInstance();

    private IReporterResponseHandler iReporterResponseHandler = IReporterResponseHandler.getInstance();

    private long lastRequestTime;

    IReporterPublishService()
    {
    }

    public abstract IReporterConfigurationService getConfigurationService();

    private HttpRequest getHTTPRequest(String data)
    {
        IReporterConfiguration configuration = getConfiguration();

        return HttpRequest.getInstance(configuration.getPublishUrl(), "POST", null, configuration.getContentType(), data, configuration.getRequestHeaders());
    }

    private boolean isRequestSuccessful(HttpResponseAction action)
    {
        return (action == HttpResponseAction.NO_ACTION_SUCCESS);
    }

    public final IReporterConfiguration getConfiguration()
    {
        return configurationService.getConfiguration();
    }

    private boolean publishDelayExpired()
    {
        return Timer.delayExpired(lastRequestTime, getConfiguration().getPublishFrequency());
    }

    public final boolean isEnabled()
    {
        return (getConfiguration().isPublishEnabled());
    }

    public final boolean publishReady()
    {
        return (isEnabled() && publishDelayExpired());
    }

    public final boolean sendRequest(String data)
    {
        boolean success = false;

        try
        {
            StockholmHTTPResponse response = smpHttpClient.request(getHTTPRequest(data));

            if (response != null)
            {
                success = isRequestSuccessful(iReporterResponseHandler.getAction(response));
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        lastRequestTime = System.currentTimeMillis();

        return success;
    }
}