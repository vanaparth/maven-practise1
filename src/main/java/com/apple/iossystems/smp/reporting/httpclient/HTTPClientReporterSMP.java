package com.apple.iossystems.smp.reporting.httpclient;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.interactor.impl.HTTPClient;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * @author Toch
 */
public class HTTPClientReporterSMP implements HTTPClientReporter
{
    private static final Logger LOGGER = Logger.getLogger(HTTPClientReporterSMP.class);

    private HTTPClient httpClient;

    private HTTPClientReporterSMP()
    {
        init();
    }

    private void init()
    {
        try
        {
            httpClient = new HTTPClient();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    public static HTTPClientReporterSMP getInstance()
    {
        return new HTTPClientReporterSMP();
    }

    public StockholmHTTPResponse request(Map<String, Object> parameters) throws Exception
    {
        // url and httpMethod are required, the rest are optional
        String url = (String) parameters.get(HTTPClientReporter.URL_KEY);
        String httpMethod = (String) parameters.get(HTTPClientReporter.HTTP_METHOD_KEY);

        // Optional parameters
        String contentType = (String) parameters.get(HTTPClientReporter.CONTENT_TYPE_KEY);
        Map<String, String> headers = (Map<String, String>) parameters.get(HTTPClientReporter.HEADERS_KEY);
        String queryString = (String) parameters.get(HTTPClientReporter.QUERY_STRING);
        String data = (String) parameters.get(HTTPClientReporter.DATA_KEY);

        // Send the request
        return httpClient.postData(url, queryString, data, contentType, httpMethod, headers);
    }
}
