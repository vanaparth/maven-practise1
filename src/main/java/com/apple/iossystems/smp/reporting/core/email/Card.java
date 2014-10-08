package com.apple.iossystems.smp.reporting.core.email;

/**
 * @author Toch
 */
class Card
{
    private final String displayNumber;
    private final String description;
    private final CardEvent cardEvent;

    private Card(String displayNumber, String description, CardEvent cardEvent)
    {
        this.displayNumber = displayNumber;
        this.description = description;
        this.cardEvent = cardEvent;
    }

    public static Card getInstance(String displayNumber, String description, CardEvent cardEvent)
    {
        return new Card(displayNumber, description, cardEvent);
    }

    public String getDisplayNumber()
    {
        return displayNumber;
    }

    public String getDescription()
    {
        return description;
    }

    public CardEvent getCardEvent()
    {
        return cardEvent;
    }
}