package com.apple.iossystems.smp.reporting.core.email;

/**
 * @author Toch
 */
class Card
{
    private final String description;
    private final String displayNumber;
    private final CardEvent cardEvent;

    private Card(String description, String displayNumber, CardEvent cardEvent)
    {
        this.description = description;
        this.displayNumber = displayNumber;
        this.cardEvent = cardEvent;
    }

    public static Card getInstance(String description, String displayNumber, CardEvent cardEvent)
    {
        return new Card(description, displayNumber, cardEvent);
    }

    public String getDescription()
    {
        return description;
    }

    public String getDisplayNumber()
    {
        return displayNumber;
    }

    public CardEvent getCardEvent()
    {
        return cardEvent;
    }
}