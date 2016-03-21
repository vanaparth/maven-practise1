package com.apple.iossystems.smp.reporting.core.http;

import java.util.Map;

/**
 * @author Toch
 */
public class HttpRequest
{
    private final String url;
    private final String httpMethod;
    private final String queryString;
    private final String contentType;
    private final String data;
    private final Map<String, String> headers;

    private HttpRequest(String url, String httpMethod, String queryString, String contentType, String data, Map<String, String> headers)
    {
        this.url = url;
        this.httpMethod = httpMethod;
        this.queryString = queryString;
        this.contentType = contentType;
        this.data = data;
        this.headers = headers;
    }

    public static HttpRequest getInstance(String url, String httpMethod, String queryString, String contentType, String data, Map<String, String> headers)
    {
        return new HttpRequest(url, httpMethod, queryString, contentType, data, headers);
    }

    String getUrl()
    {
        return url;
    }

    String getHttpMethod()
    {
        return httpMethod;
    }

    String getQueryString()
    {
        return queryString;
    }

    String getContentType()
    {
        return contentType;
    }

    String getData()
    {
        return data;
    }

    Map<String, String> getHeaders()
    {
        return headers;
    }
}