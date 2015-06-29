package com.apple.iossystems.smp.reporting.core.util;

/**
 * @author Toch
 */
public class ValidValue
{
    private ValidValue()
    {
    }

    public static String getStringValueWithDefault(String value, String defaultValue)
    {
        return (value != null) ? value : defaultValue;
    }

    public static int getIntValueWithDefault(String value, int defaultValue)
    {
        return (value != null) ? Integer.parseInt(value) : defaultValue;
    }
}