package com.apple.iossystems.smp.reporting.util;

/**
 * @author Toch
 */
public class Utils
{
    public static Object getValueWithDefault(Object value, Object defaultValue)
    {
        return (value != null) ? value : defaultValue;
    }
}
