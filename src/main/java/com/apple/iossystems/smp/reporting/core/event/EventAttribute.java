package com.apple.iossystems.smp.reporting.core.event;

/**
 * @author Toch
 */
public enum EventAttribute
{
    // Reserved
    EVENT_TYPE("event_type"),
    // Stockholm
    ATHENA_COLOR("ast"),
    CARD_BIN("bin"),
    CARD_EVENT("evt"),
    CARD_ID("cid"),
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
    MERCHANT_ID("mid"),
    TRANSACTION_AMOUNT("tmt"),
    TRANSACTION_ID("tid"),
    TRANSACTION_STATUS("tst");

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