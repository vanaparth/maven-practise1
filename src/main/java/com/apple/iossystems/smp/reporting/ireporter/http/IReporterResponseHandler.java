package com.apple.iossystems.smp.reporting.ireporter.http;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.reporting.core.http.HttpResponseHandler;
import com.apple.iossystems.smp.reporting.core.http.SMPHttpResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class IReporterResponseHandler implements HttpResponseHandler
{
    private static final Map<Integer, IReporterHttpResponse> HTTP_RESPONSE_MAP = getHttpResponseMap();

    private IReporterResponseHandler()
    {
    }

    public static IReporterResponseHandler getInstance()
    {
        return new IReporterResponseHandler();
    }

    private static Map<Integer, IReporterHttpResponse> getHttpResponseMap()
    {
        Map<Integer, IReporterHttpResponse> map = new HashMap<>();

        for (IReporterHttpResponse response : IReporterHttpResponse.values())
        {
            map.put(response.getCode(), response);
        }

        return map;
    }

    @Override
    public SMPHttpResponse getSMPHttpResponse(StockholmHTTPResponse response)
    {
        int responseCode = (response != null) ? response.getStatus() : IReporterHttpResponse.NO_RESPONSE.getCode();

        IReporterHttpResponse iReporterHttpResponse = HTTP_RESPONSE_MAP.get(responseCode);
        String message;

        if (iReporterHttpResponse != null)
        {
            message = iReporterHttpResponse.getMessage();
        }
        else
        {
            iReporterHttpResponse = IReporterHttpResponse.UNKNOWN;
            message = iReporterHttpResponse.getMessage() + " " + responseCode;
        }

        return new SMPHttpResponse(iReporterHttpResponse.getAction(), message);
    }
}