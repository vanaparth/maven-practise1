package com.apple.iossystems.smp.reporting.core.http;

/**
 * @author Toch
 */
public class SMPHttpResponse
{
    private final HttpResponseAction httpResponseAction;
    private final String message;

    public SMPHttpResponse(HttpResponseAction httpResponseAction, String message)
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