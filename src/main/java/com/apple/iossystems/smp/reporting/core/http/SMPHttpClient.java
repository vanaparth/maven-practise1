package com.apple.iossystems.smp.reporting.core.http;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.interactor.impl.HTTPClient;

/**
 * @author Toch
 */
public class SMPHttpClient
{
    private HTTPClient httpClient = new HTTPClient();

    private SMPHttpClient() throws Exception
    {
    }

    public static SMPHttpClient getInstance() throws Exception
    {
        return new SMPHttpClient();
    }

    public StockholmHTTPResponse request(HttpRequest httpRequest) throws Exception
    {
        return httpClient.postData(httpRequest.getUrl(), httpRequest.getQueryString(), httpRequest.getData(), httpRequest.getContentType(), httpRequest.getHttpMethod(), httpRequest.getHeaders());
    }
}
