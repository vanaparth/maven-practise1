package com.apple.iossystems.smp.reporting.core.event;

/**
 * Created by scottblakesley on 12/15/15.
 */
public enum LoyaltyEventTypeEnum
{
    SIGNUP_START("100"),
    SIGNUP_COMPLETE("101");

    private final String eventType;

    LoyaltyEventTypeEnum(String eventType)
    {
        this.eventType = eventType;
    }

    String getEventType()
    {
        return eventType;
    }
}