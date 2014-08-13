package com.apple.iossystems.smp.reporting.core.event;

/**
 * @author Toch
 */
public enum EventAttribute
{
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
    // other
    //CHECK_CARD_STATUS_CODE("check_card_status_code"),
    //CHECK_CARD_NETWORK_SUB_STATUS_CODE("check_card_network_sub_status_code"),
    //DEVICE_TYPE("device_type"),
    //IOS_VERSION("ios_version"),
    // Oslo
    APPLICATION_ID("application_id"),
    MERCHANT_ID("merchant_id"),
    TRANSACTION_AMOUNT("transaction_amount"),
    TRANSACTION_STATUS("transaction_status");

    private String key;

    private EventAttribute(String key)
    {
        this.key = key;
    }

    public String key()
    {
        return key;
    }
}