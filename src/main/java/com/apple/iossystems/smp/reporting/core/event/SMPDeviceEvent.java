package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.utils.PnoApi;

/**
 * @author Toch
 */
public enum SMPDeviceEvent
{
    CHECK_CARD("100"),
    PROVISION_CARD("101"),
    SUSPEND_CARD("102"),
    UNLINK_CARD("103"),
    RESUME_CARD("104"),
    PUT_PENDING("105"),
    POST_COMMAND("106"),
    GET_OTP_RESOLUTION_METHODS("107"),
    SEND_OTP("108"),
    UNKNOWN("000");

    private final String code;

    private SMPDeviceEvent(String code)
    {
        this.code = code;
    }

    public static SMPDeviceEvent pnoApiToCardEvent(PnoApi pnoApi)
    {
        switch (pnoApi)
        {
            case RESUME:
                return RESUME_CARD;

            case SUSPEND:
                return SUSPEND_CARD;

            case UNLINK:
                return UNLINK_CARD;

            default:
                return UNKNOWN;
        }
    }

    private static SMPDeviceEvent fromCode(String code)
    {
        for (SMPDeviceEvent smpEvent : values())
        {
            if (smpEvent.code.equals(code))
            {
                return smpEvent;
            }
        }

        return UNKNOWN;
    }

    public static SMPDeviceEvent getSMPEvent(EventRecord record)
    {
        return fromCode(record.getAttributeValue(EventAttribute.EVENT.key()));
    }

    public void setEvent(EventRecord record)
    {
        record.setAttributeValue(EventAttribute.EVENT.key(), code);
    }
}