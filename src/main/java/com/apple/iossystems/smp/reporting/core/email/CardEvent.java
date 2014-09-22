package com.apple.iossystems.smp.reporting.core.email;

/**
 * @author Toch
 */
class CardEvent
{
    private final String dpanId;
    private final boolean status;

    private CardEvent(String dpanId, boolean status)
    {
        this.dpanId = dpanId;
        this.status = status;
    }

    public static CardEvent getInstance(String dpanId, boolean status)
    {
        return new CardEvent(dpanId, status);
    }

    public String getDpanId()
    {
        return dpanId;
    }

    public boolean getStatus()
    {
        return status;
    }
}