package com.apple.iossystems.smp.reporting.httpclient;

import com.apple.iossystems.smp.StockholmHTTPResponse;

import java.util.Map;

/**
 * @author Toch
 */
public interface HTTPClientReporter
{
    public static final String HTTP_METHOD_KEY = "httpMethod";
    public static final String URL_KEY = "url";
    public static final String CONTENT_TYPE_KEY = "contentType";
    public static final String HEADERS_KEY = "headers";
    public static final String QUERY_STRING = "queryString";
    public static final String DATA_KEY = "data";

    public StockholmHTTPResponse request(Map<String, Object> parameters) throws Exception;
}
