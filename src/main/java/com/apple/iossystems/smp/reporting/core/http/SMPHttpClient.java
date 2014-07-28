package com.apple.iossystems.smp.reporting.core.http;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.interactor.impl.HTTPClient;

import java.util.Map;

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

    public StockholmHTTPResponse request(Map<HttpRequestParameter, Object> parameters) throws Exception
    {
        String url = (String) parameters.get(HttpRequestParameter.URL);
        String httpMethod = (String) parameters.get(HttpRequestParameter.HTTP_METHOD);

        String contentType = (String) parameters.get(HttpRequestParameter.CONTENT_TYPE);
        Map<String, String> headers = (Map<String, String>) parameters.get(HttpRequestParameter.HEADERS);

        String queryString = (String) parameters.get(HttpRequestParameter.QUERY_STRING);
        String data = (String) parameters.get(HttpRequestParameter.DATA);

        return httpClient.postData(url, queryString, data, contentType, httpMethod, headers);
    }
}
