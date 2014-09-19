package com.apple.iossystems.smp.reporting.core.email;

/**
 * @author Toch
 */
class Card
{
    private final String description;
    private final String displayNumber;
    private final boolean status;

    private Card(String description, String displayNumber, boolean status)
    {
        this.description = description;
        this.displayNumber = displayNumber;
        this.status = status;
    }

    public static Card getInstance(String description, String displayNumber, boolean status)
    {
        return new Card(description, displayNumber, status);
    }

    public String getDescription()
    {
        return description;
    }

    public String getDisplayNumber()
    {
        return displayNumber;
    }

    public boolean getStatus()
    {
        return status;
    }
}