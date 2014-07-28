package com.apple.iossystems.smp.reporting.core.event;

/**
 * @author Toch
 */
public enum SMPEventAttribute
{
    CARD_BIN("bin"),
    CARD_ISSUER("issuer_name"),
    CARD_SOURCE("provision_type"),
    CARD_STATUS("provision_status"),
    CARD_TYPE("card_type"),
    DEVICE_LOCATION_IP("ip_address"),
    DEVICE_LOCATION_LATLON("lat_long"),
    DEVICE_SERIAL_NUMBER("serial_no"),
    DPAN_GUID("dpan_guid_no"),
    EVENT("event_type"),
    FAILURE_CODE("failure_code"),
    FPAN_GUID("fpan_guid_no"),
    PNO("pno"),
    TIMESTAMP("event_time"),
    USER_AGENT("device_user_agent"),
    USER_ID("ds_prsid");

    private String jsonKey;

    private SMPEventAttribute(String jsonKey)
    {
        this.jsonKey = jsonKey;
    }

    public String getJsonKey()
    {
        return jsonKey;
    }
}
