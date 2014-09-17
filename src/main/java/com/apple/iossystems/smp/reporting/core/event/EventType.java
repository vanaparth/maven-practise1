package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.logging.LogLevel;

/**
 * @author Toch
 */
public enum EventType
{
    REPORTS("reports", LogLevel.EVENT),
    PAYMENT("payment", LogLevel.EVENT),
    EMAIL("email", LogLevel.EVENT),
    //
    UNKNOWN("unknown", LogLevel.EVENT);

    private final String key;
    private final LogLevel logLevel;

    private EventType(String key, LogLevel logLevel)
    {
        this.key = key;
        this.logLevel = logLevel;
    }

    public String getKey()
    {
        return key;
    }

    public String getQueueName()
    {
        return logLevel.name();
    }

    public boolean keyEquals(String value)
    {
        return key.equals(value);
    }

    public static EventType getEventType(String value)
    {
        for (EventType eventType : EventType.values())
        {
            if (eventType.keyEquals(value))
            {
                return eventType;
            }
        }

        return UNKNOWN;
    }

    public static EventType getEventType(EventRecord record)
    {
        return getEventType(record.getAttributeValue(EventAttribute.EVENT_TYPE.key()));
    }

    public static LogLevel getLogLevel(EventRecord record)
    {
        return getEventType(record).logLevel;
    }
}