package com.apple.iossystems.smp.reporting.core.http;

import com.apple.iossystems.smp.StockholmHTTPResponse;

/**
 * @author Toch
 */
public interface HttpResponseHandler
{
    SMPHttpResponse getSMPHttpResponse(StockholmHTTPResponse response);
}