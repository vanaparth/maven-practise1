package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.logging.LogLevel;

/**
 * @author Toch
 */
public enum EventType
{
    REPORTS("reports", LogLevel.EVENT1),
    PAYMENT("payment", LogLevel.EVENT2),
    BACKLOG("backlog", LogLevel.EVENT3),
    LOYALTY("loyalty", LogLevel.EVENT),
    EMAIL("email", null);

    private final String key;
    private final LogLevel logLevel;

    EventType(String key, LogLevel logLevel)
    {
        this.key = key;
        this.logLevel = logLevel;
    }

    public String getKey()
    {
        return key;
    }

    public LogLevel getLogLevel()
    {
        return logLevel;
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
        for (EventType eventType : values())
        {
            if (eventType.keyEquals(value))
            {
                return eventType;
            }
        }

        return null;
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