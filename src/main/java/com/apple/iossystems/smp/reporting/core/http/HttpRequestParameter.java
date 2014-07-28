package com.apple.iossystems.smp.reporting.core.http;

/**
 * @author Toch
 */
public enum HttpRequestParameter
{
    CONTENT_TYPE("contentType"),
    DATA("data"),
    HEADERS("headers"),
    HTTP_METHOD("httpMethod"),
    QUERY_STRING("queryString"),
    URL("url");

    private String key;

    private HttpRequestParameter(String key)
    {
        this.key = key;
    }
}
