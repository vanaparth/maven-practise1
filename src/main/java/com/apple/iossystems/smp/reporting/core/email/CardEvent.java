package com.apple.iossystems.smp.reporting.core.email;

/**
 * @author Toch
 */
public class CardEvent
{
    private final String dpanId;
    private final String cardDisplayNumber;
    private final String cardDescription;
    private final boolean isSuccessful;

    public CardEvent(String dpanId, String cardDisplayNumber, String cardDescription, boolean isSuccessful)
    {
        this.dpanId = dpanId;
        this.cardDisplayNumber = cardDisplayNumber;
        this.cardDescription = cardDescription;
        this.isSuccessful = isSuccessful;
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

    public boolean isSuccessful()
    {
        return isSuccessful;
    }
}