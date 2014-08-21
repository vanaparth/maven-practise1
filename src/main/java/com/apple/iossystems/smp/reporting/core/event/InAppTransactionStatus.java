package com.apple.iossystems.smp.reporting.core.event;

/**
 * @author Toch
 */
public enum InAppTransactionStatus
{
    FAILED("0"),
    SUCCESS("1");

    private final String code;

    private InAppTransactionStatus(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
}
