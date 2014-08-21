package com.apple.iossystems.smp.reporting.core.event;

/**
 * @author Toch
 */
public enum EventAttribute
{
    ATHENA_COLOR("ast"),
    CARD_BIN("bin"),
    CARD_EVENT("evt"),
    CARD_ISSUER("bnm"),
    CARD_SOURCE("ptp"),
    CARD_STATUS("cst"),
    CARD_TYPE("ctp"),
    CONVERSATION_ID("con"),
    DEVICE_LANGUAGE("lcd"),
    DEVICE_LOCATION("llg"),
    DEVICE_SERIAL_NUMBER("sno"),
    DPAN_ID("dpn"),
    DSID("did"),
    FPAN_ID("fpn"),
    PNO("pno"),
    PROVISION_STATUS("pst"),
    SUPPORTS_IN_APP_PAYMENT("osl"),
    TIMESTAMP("tim"),
    USE_CASE_TYPE("psr"),
    USER_AGENT("dua"),
    // Oslo
    APPLICATION_ID("application_id"),
    MERCHANT_ID("merchant_id"),
    TRANSACTION_AMOUNT("transaction_amount"),
    TRANSACTION_STATUS("transaction_status");

    private final String key;

    private EventAttribute(String key)
    {
        this.key = key;
    }

    public String key()
    {
        return key;
    }
}