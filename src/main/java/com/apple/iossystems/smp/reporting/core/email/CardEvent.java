package com.apple.iossystems.smp.reporting.core.email;

/**
 * @author Toch
 */
public class CardEvent
{
    private final String dpanId;
    private final String cardDisplayNumber;
    private final String cardDescription;
    private final boolean eventStatus;

    private CardEvent(String dpanId, String cardDisplayNumber, String cardDescription, boolean eventStatus)
    {
        this.dpanId = dpanId;
        this.cardDisplayNumber = cardDisplayNumber;
        this.cardDescription = cardDescription;
        this.eventStatus = eventStatus;
    }

    public static CardEvent getInstance(String dpanId, String cardDisplayNumber, String cardDescription, boolean eventStatus)
    {
        return new CardEvent(dpanId, cardDisplayNumber, cardDescription, eventStatus);
    }

    public String getDpanId()
    {
        return dpanId;
    }

    public String getCardDisplayNumber()
    {
        return cardDisplayNumber;
    }

    public String getCardDescription()
    {
        return cardDescription;
    }

    public boolean getEventStatus()
    {
        return eventStatus;
    }
}