package com.apple.iossystems.smp.reporting.core.util;

/**
 * @author Toch
 */
public class ValueSelector
{
    private ValueSelector()
    {
    }

    public static Object getValueWithDefault(Object value, Object defaultValue)
    {
        return (value != null) ? value : defaultValue;
    }

    public static String getStringValueWithDefault(Object value, String defaultValue)
    {
        return (value != null) ? String.valueOf(value) : defaultValue;
    }

    public static int getIntValueWithDefault(Object value, int defaultValue)
    {
        return (value != null) ? Integer.valueOf(String.valueOf(value)) : defaultValue;
    }

    public static long getLongValueWithDefault(Object value, long defaultValue)
    {
        return (value != null) ? Long.valueOf(String.valueOf(value)) : defaultValue;
    }
}
