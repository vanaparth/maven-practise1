package com.apple.iossystems.smp.reporting.core.util;

/**
 * @author Toch
 */
public class ValidValue
{
    private ValidValue()
    {
    }

    public static int getIntValueWithDefault(String value, int defaultValue)
    {
        return (value != null) ? Integer.valueOf(value) : defaultValue;
    }
}