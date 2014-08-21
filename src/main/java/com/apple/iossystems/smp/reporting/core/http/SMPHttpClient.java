package com.apple.iossystems.smp.reporting.core.http;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.interactor.impl.HTTPClient;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class SMPHttpClient
{
    private static final Logger LOGGER = Logger.getLogger(SMPHttpClient.class);

    private HTTPClient httpClient = new HTTPClient();

    private SMPHttpClient() throws Exception
    {
    }

    public static SMPHttpClient getInstance() throws Exception
    {
        return new SMPHttpClient();
    }

    private StockholmHTTPResponse sendRequest(HttpRequest httpRequest) throws Exception
    {
        return httpClient.postData(httpRequest.getUrl(), httpRequest.getQueryString(), httpRequest.getData(), httpRequest.getContentType(), httpRequest.getHttpMethod(), httpRequest.getHeaders());
    }

    private StockholmHTTPResponse requestWithRetries(HttpRequest httpRequest, int maxRetryCount)
    {
        StockholmHTTPResponse response = null;
        int retryCount = 0;
        int sleepTimeMillis = 3 * 1000;

        while (retryCount <= maxRetryCount)
        {
            try
            {
                response = sendRequest(httpRequest);

                if (response.isSuccessful())
                {
                    return response;
                }

                if (retryCount < maxRetryCount)
                {
                    Thread.sleep(retryCount * sleepTimeMillis);
                }
            }
            catch (Exception e)
            {
                LOGGER.error(e);
            }

            retryCount++;
        }

        return response;
    }

    public StockholmHTTPResponse request(HttpRequest httpRequest) throws Exception
    {
        return requestWithRetries(httpRequest, 0);
    }
}