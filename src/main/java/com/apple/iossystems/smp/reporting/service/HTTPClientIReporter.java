package com.apple.iossystems.smp.reporting.service;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.interactor.impl.HTTPClient;
import com.apple.iossystems.smp.reporting.util.Utils;

import java.util.Map;

/**
 * @author Toch
 */
public class HTTPClientIReporter implements HTTPClientSMPReporter
{
    private HTTPClient client = new HTTPClient();

    private HTTPClientIReporter() throws Exception
    {
    }

    public static HTTPClientIReporter newInstance() throws Exception
    {
        return new HTTPClientIReporter();
    }

    /**
     * See code from iCloudServiceImpl
     * TODO: do something with the response
     */
    public StockholmHTTPResponse request(Map<String, Object> parameters) throws Exception
    {
        // url and httpMethod are required, the rest are optional
        String url = (String) parameters.get(HTTPClientSMPReporter.URL_KEY);
        String httpMethod = (String) parameters.get(HTTPClientSMPReporter.HTTP_METHOD_KEY);

        // Optional parameters
        String data = (String) parameters.get(HTTPClientSMPReporter.DATA_KEY);
        String contentType = (String) parameters.get(HTTPClientSMPReporter.CONTENT_TYPE_KEY);
        Map<String, String> headers = (Map<String, String>) parameters.get(HTTPClientSMPReporter.HEADERS_KEY);

        // Send the request
        return client.postData(url, null, data, contentType, httpMethod, headers);
    }
}
