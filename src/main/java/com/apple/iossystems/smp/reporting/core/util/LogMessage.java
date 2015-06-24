package com.apple.iossystems.smp.reporting.core.util;

import org.apache.commons.lang.StringUtils;

/**
 * @author Toch
 */
public class LogMessage
{
    private StringJoiner stringJoiner = new StringJoiner(" ");

    public LogMessage()
    {
    }

    public LogMessage add(String key, String value)
    {
        if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value))
        {
            stringJoiner.add(key + "=" + value);
        }

        return this;
    }

    public LogMessage add(String message)
    {
        if (StringUtils.isNotBlank(message))
        {
            stringJoiner.add(message);
        }

        return this;
    }

    @Override
    public String toString()
    {
        return stringJoiner.toString();
    }
}