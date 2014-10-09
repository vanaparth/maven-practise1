package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.utils.PnoApi;

/**
 * @author Toch
 */
public enum SMPCardEvent
{
    CHECK_CARD("100"),
    PROVISION_CARD("101"),
    SUSPEND_CARD("102"),
    UNLINK_CARD("103"),
    RESUME_CARD("104"),
    PUT_PENDING("105"),
    POST_COMMAND("106"),
    UNKNOWN("000");

    private final String code;

    private SMPCardEvent(String code)
    {
        this.code = code;
    }

    private String getCode()
    {
        return code;
    }

    public static SMPCardEvent pnoApiToCardEvent(PnoApi pnoApi)
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

    private static SMPCardEvent getSMPCardEvent(String code)
    {
        for (SMPCardEvent smpCardEvent : SMPCardEvent.values())
        {
            if (smpCardEvent.code.equals(code))
            {
                return smpCardEvent;
            }
        }

        return UNKNOWN;
    }

    public static SMPCardEvent getSMPCardEvent(EventRecord record)
    {
        return getSMPCardEvent(record.getAttributeValue(EventAttribute.CARD_EVENT.key()));
    }

    public void setSMPCardEvent(EventRecord record)
    {
        record.setAttributeValue(EventAttribute.CARD_EVENT.key(), getCode());
    }
}