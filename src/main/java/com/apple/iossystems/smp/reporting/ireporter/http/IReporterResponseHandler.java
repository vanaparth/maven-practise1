package com.apple.iossystems.smp.reporting.ireporter.http;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.reporting.core.http.HttpResponseAction;
import com.apple.iossystems.smp.reporting.core.http.HttpResponseHandler;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class IReporterResponseHandler implements HttpResponseHandler
{
    private static final Logger LOGGER = Logger.getLogger(IReporterResponseHandler.class);

    private IReporterResponseHandler()
    {
    }

    public static HttpResponseAction getAction(StockholmHTTPResponse response)
    {
        int responseCode = response.getStatus();

        switch (responseCode)
        {
            case 200:
                return handleResponseCode200();

            case 400:
                return handleResponseCode400();

            case 403:
                return handleResponseCode403();

            case 404:
                return handleResponseCode404();

            case 405:
                return handleResponseCode405();

            case 415:
                return handleResponseCode415();

            default:
                return handleResponseCodeUnknown(responseCode);
        }
    }

    private static HttpResponseAction handleResponseCode200()
    {
        return HttpResponseAction.NO_ACTION_SUCCESS;
    }

    private static HttpResponseAction handleResponseCode400()
    {
        LOGGER.error("Invalid Content-Type|URL format");

        return HttpResponseAction.NO_ACTION_ERROR;
    }

    private static HttpResponseAction handleResponseCode403()
    {
        LOGGER.error("No PList|ShouldPublish disabled|Invalid Publish Key");

        return HttpResponseAction.RELOAD_CONFIGURATION;
    }

    private static HttpResponseAction handleResponseCode404()
    {
        LOGGER.error("Invalid Request URL");

        return HttpResponseAction.RELOAD_CONFIGURATION;
    }

    private static HttpResponseAction handleResponseCode405()
    {
        LOGGER.error("Invalid Method in Header");

        return HttpResponseAction.NO_ACTION_ERROR;
    }

    private static HttpResponseAction handleResponseCode415()
    {
        LOGGER.error("Invalid Content-Encoding in Header");

        return HttpResponseAction.NO_ACTION_ERROR;
    }

    private static HttpResponseAction handleResponseCodeUnknown(int responseCode)
    {
        LOGGER.error("Unknown Response Code " + responseCode);

        return HttpResponseAction.RETRY_PUBLISH;
    }
}