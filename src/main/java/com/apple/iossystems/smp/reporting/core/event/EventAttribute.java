package com.apple.iossystems.smp.reporting.core.event;

/**
 * @author Toch
 */
public enum EventAttribute
{
    // Reserved
    EVENT_TYPE("event_type"),
    // Stockholm
    APP_ID("bbd"),
    ATHENA_COLOR("ast"),
    CARD_BIN("bin"),
    CARD_ID("cid"),
    CARD_ISSUER("bnm"),
    CARD_SOURCE("ptp"),
    CARD_STATUS("cst"),
    CARD_STATUS_UPDATE_SOURCE("ssr"),
    CARD_TYPE("ctp"),
    COMPANION_DEVICE_SERIAL_NUMBER("csn"),
    COMPANION_USER_AGENT("cua"),
    CONVERSATION_ID("con"),
    COUNTRY("ccd"),
    CURRENCY("cur"),
    DEVICE_LANGUAGE("lcd"),
    DEVICE_LOCATION("llg"),
    DEVICE_SERIAL_NUMBER("sno"),
    DEVICE_TYPE("dty"),
    DPAN_ID("dpn"),
    DSID("did"),
    EVENT("evt"),
    FPAN_ID("fpn"),
    MERCHANT_ID("mid"),
    OTP("otp"),
    PNO("pno"),
    PRODUCT("plr"),
    PROVISION_RESPONSE("prs"),
    PROVISION_STATUS("pst"),
    TIMESTAMP("tim"),
    USE_CASE_TYPE("psr"),
    USER_AGENT("dua"),
    WATCH_MATERIAL_PART_NUMBER("wpn"),
    MERCHANT_ID("mid"),
    // Oslo
    TRANSACTION_AMOUNT("tmt"),
    TRANSACTION_ID("tid"),
    TRANSACTION_STATUS("tst"),
    // Email
    MANAGE_DEVICE_EVENT("manage_device_event"),
    PROVISION_CARD_EVENT("provision_card_event"),
    // Kista
    SEID("seid"),
    // MerchantServices
    TOKEN_ID("tkn");

    private final String key;

    EventAttribute(String key)
    {
        this.key = key;
    }

    public String key()
    {
        return key;
    }
}