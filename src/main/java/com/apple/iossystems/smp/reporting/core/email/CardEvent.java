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
    private final boolean isTruthOnCard;

    public CardEvent(String dpanId, String cardDisplayNumber, String cardDescription, boolean isSuccessful, boolean isTruthOnCard)
    {
        this.dpanId = dpanId;
        this.cardDisplayNumber = cardDisplayNumber;
        this.cardDescription = cardDescription;
        this.isSuccessful = isSuccessful;
        this.isTruthOnCard = isTruthOnCard;
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

    public boolean isTruthOnCard()
    {
        return isTruthOnCard;
    }
}