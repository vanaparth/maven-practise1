package com.apple.iossystems.smp.reporting.ireporter.http;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.reporting.core.http.HttpResponseAction;
import com.apple.iossystems.smp.reporting.core.http.HttpResponseHandler;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class IReporterResponseHandler implements HttpResponseHandler
{
    private static final Logger LOGGER = Logger.getLogger(IReporterResponseHandler.class);

    private static final Map<Integer, IReporterResponseAction> ACTION_MAP = getActionMap();

    private IReporterResponseHandler()
    {
    }

    public static IReporterResponseHandler getInstance()
    {
        return new IReporterResponseHandler();
    }

    private static Map<Integer, IReporterResponseAction> getActionMap()
    {
        Map<Integer, IReporterResponseAction> map = new HashMap<>();

        map.put(200, IReporterResponseAction.SUCCESS);
        map.put(400, IReporterResponseAction.INVALID_CONTENT_TYPE);
        map.put(403, IReporterResponseAction.INVALID_PLIST);
        map.put(404, IReporterResponseAction.INVALID_REQUEST_URL);
        map.put(405, IReporterResponseAction.INVALID_METHOD);
        map.put(415, IReporterResponseAction.INVALID_CONTENT_ENCODING);

        return map;
    }

    @Override
    public HttpResponseAction getAction(StockholmHTTPResponse response)
    {
        int responseCode = (response != null) ? response.getStatus() : 200;

        IReporterResponseAction iReporterResponseAction = ACTION_MAP.get(responseCode);
        String message;

        if (iReporterResponseAction != null)
        {
            message = iReporterResponseAction.getMessage();
        }
        else
        {
            iReporterResponseAction = IReporterResponseAction.UNKNOWN;
            message = iReporterResponseAction.getMessage() + " " + responseCode;
        }

        if (iReporterResponseAction != IReporterResponseAction.SUCCESS)
        {
            LOGGER.error(message);
        }

        return iReporterResponseAction.getAction();
    }
}