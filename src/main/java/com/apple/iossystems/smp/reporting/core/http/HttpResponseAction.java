package com.apple.iossystems.smp.reporting.core.http;

/**
 * @author Toch
 */
public enum HttpResponseAction
{
    NO_ACTION_SUCCESS,
    NO_ACTION_ERROR,
    RETRY_PUBLISH,
    RELOAD_CONFIGURATION;

    private HttpResponseAction()
    {
    }
}
