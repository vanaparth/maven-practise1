package com.apple.iossystems.smp.reporting.ireporter.http;

import com.apple.iossystems.smp.reporting.core.http.HttpResponseAction;

/**
 * @author Toch
 */
public enum IReporterResponseAction
{
    SUCCESS(HttpResponseAction.NO_ACTION_SUCCESS, "Success"),
    INVALID_CONTENT_TYPE(HttpResponseAction.NO_ACTION_ERROR, "Invalid Content-Type|URL format"),
    INVALID_PLIST(HttpResponseAction.RELOAD_CONFIGURATION, "No PList|ShouldPublish disabled|Invalid Publish Key"),
    INVALID_REQUEST_URL(HttpResponseAction.RELOAD_CONFIGURATION, "Invalid Request URL"),
    INVALID_METHOD(HttpResponseAction.NO_ACTION_ERROR, "Invalid Method in Header"),
    INVALID_CONTENT_ENCODING(HttpResponseAction.NO_ACTION_ERROR, "Invalid Content-Encoding in Header"),
    UNKNOWN(HttpResponseAction.RETRY, "Unknown Response Code");

    private final HttpResponseAction httpResponseAction;
    private final String message;

    IReporterResponseAction(HttpResponseAction httpResponseAction, String message)
    {
        this.httpResponseAction = httpResponseAction;
        this.message = message;
    }

    public HttpResponseAction getAction()
    {
        return httpResponseAction;
    }

    public String getMessage()
    {
        return message;
    }
}