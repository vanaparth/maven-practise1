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
    COMPANION_USER_AGENT("cua"),
    CONVERSATION_ID("con"),
    DEVICE_LANGUAGE("lcd"),
    DEVICE_LOCATION("llg"),
    DEVICE_SERIAL_NUMBER("sno"),
    DEVICE_TYPE("dty"),
    DPAN_ID("dpn"),
    DSID("did"),
    FPAN_ID("fpn"),
    OTP("otp"),
    PNO("pno"),
    PROVISION_RESPONSE("prs"),
    PROVISION_STATUS("pst"),
    TIMESTAMP("tim"),
    USE_CASE_TYPE("psr"),
    USER_AGENT("dua"),
    // Oslo
    MERCHANT_ID("mid"),
    TRANSACTION_AMOUNT("tmt"),
    TRANSACTION_ID("tid"),
    TRANSACTION_STATUS("tst"),
    // Email
    MANAGE_CARD_EVENT("manage_card_event"),
    PROVISION_CARD_EVENT("provision_card_event");

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