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
    private static final Map<Integer, IReporterHttpResponse> ACTION_MAP = getActionMap();

    private IReporterResponseHandler()
    {
    }

    public static IReporterResponseHandler getInstance()
    {
        return new IReporterResponseHandler();
    }

    private static Map<Integer, IReporterHttpResponse> getActionMap()
    {
        Map<Integer, IReporterHttpResponse> map = new HashMap<>();

        addToMap(map, IReporterHttpResponse.SUCCESS);
        addToMap(map, IReporterHttpResponse.INVALID_CONTENT_TYPE);
        addToMap(map, IReporterHttpResponse.INVALID_PLIST);
        addToMap(map, IReporterHttpResponse.INVALID_REQUEST_URL);
        addToMap(map, IReporterHttpResponse.INVALID_REQUEST_METHOD);
        addToMap(map, IReporterHttpResponse.INVALID_CONTENT_ENCODING);
        addToMap(map, IReporterHttpResponse.NO_RESPONSE);
        addToMap(map, IReporterHttpResponse.UNKNOWN);

        return map;
    }

    private static void addToMap(Map<Integer, IReporterHttpResponse> map, IReporterHttpResponse response)
    {
        map.put(response.getCode(), response);
    }

    @Override
    public SMPHttpResponse getSMPHttpResponse(StockholmHTTPResponse response)
    {
        int responseCode = (response != null) ? response.getStatus() : IReporterHttpResponse.NO_RESPONSE.getCode();

        IReporterHttpResponse iReporterHttpResponse = ACTION_MAP.get(responseCode);
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