package com.apple.iossystems.smp.reporting.ireporter.http;

import com.apple.iossystems.smp.reporting.core.http.HttpResponseAction;

/**
 * @author Toch
 */
public enum IReporterHttpResponse
{
    SUCCESS(200, HttpResponseAction.NO_ACTION_SUCCESS, "Success"),
    INVALID_CONTENT_TYPE(400, HttpResponseAction.NO_ACTION_ERROR, "Invalid Content Type or URI"),
    INVALID_PLIST(403, HttpResponseAction.RELOAD_CONFIGURATION, "No PList|ShouldPublish disabled|Invalid Publish Key"),
    INVALID_REQUEST_URL(404, HttpResponseAction.RELOAD_CONFIGURATION, "Invalid Request URL"),
    INVALID_REQUEST_METHOD(405, HttpResponseAction.NO_ACTION_ERROR, "Invalid Request Method"),
    INVALID_CONTENT_ENCODING(415, HttpResponseAction.NO_ACTION_ERROR, "Invalid Content Encoding"),
    NO_RESPONSE(1000, HttpResponseAction.NO_ACTION_ERROR, "No Response"),
    UNKNOWN(2000, HttpResponseAction.NO_ACTION_ERROR, "Unknown Response Code");

    private final int code;
    private final HttpResponseAction httpResponseAction;
    private final String message;

    IReporterHttpResponse(int code, HttpResponseAction httpResponseAction, String message)
    {
        this.code = code;
        this.httpResponseAction = httpResponseAction;
        this.message = message;
    }

    public int getCode()
    {
        return code;
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