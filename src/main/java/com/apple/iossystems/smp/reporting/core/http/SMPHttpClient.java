package com.apple.iossystems.smp.reporting.core.http;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.interactor.impl.HTTPClient;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.hubble.HubblePublisher;
import com.apple.iossystems.smp.reporting.ireporter.http.IReporterHttpResponse;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class SMPHttpClient
{
    private static final Logger LOGGER = Logger.getLogger(SMPHttpClient.class);

    private final HubblePublisher hubblePublisher = HubblePublisher.getInstance();
    private final HTTPClient httpClient = getHttpClientInstance();

    private SMPHttpClient()
    {
    }

    public static SMPHttpClient getInstance()
    {
        return new SMPHttpClient();
    }

    private HTTPClient getHttpClientInstance()
    {
        HTTPClient httpClient = null;

        try
        {
            httpClient = new HTTPClient();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return httpClient;
    }

    private HTTPClient getHttpClient()
    {
        return (httpClient != null) ? httpClient : getHttpClientInstance();
    }

    private StockholmHTTPResponse sendRequest(HttpRequest httpRequest) throws Exception
    {
        return getHttpClient().postData(httpRequest.getUrl(), httpRequest.getQueryString(), httpRequest.getData(), httpRequest.getContentType(), httpRequest.getHttpMethod(), httpRequest.getHeaders());
    }

    public StockholmHTTPResponse request(HttpRequest httpRequest)
    {
        StockholmHTTPResponse response = null;

        try
        {
            response = sendRequest(httpRequest);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        notifyListeners(httpRequest, response);

        return response;
    }

    private void notifyListeners(HttpRequest httpRequest, StockholmHTTPResponse response)
    {
        int responseCode;
        boolean success;

        if (response != null)
        {
            responseCode = response.getStatus();
            success = response.isSuccessful();
        }
        else
        {
            responseCode = IReporterHttpResponse.NO_RESPONSE.getCode();
            success = false;
        }

        if (!success)
        {
            LOGGER.error("Http request failed url=" + httpRequest.getUrl() + " responseCode=" + responseCode);

            hubblePublisher.incrementCountForEvent(Metric.HTTP_REQUEST_FAILED);
        }
    }
}