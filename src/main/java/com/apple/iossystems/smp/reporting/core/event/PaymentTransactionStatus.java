package com.apple.iossystems.smp.reporting.core.event;

/**
 * @author Toch
 */
public enum PaymentTransactionStatus
{
    FAILED("0"),
    SUCCESS("1");

    private final String code;

    PaymentTransactionStatus(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
}