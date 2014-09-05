package com.apple.iossystems.smp.reporting.core.util;

/**
 * @author Toch
 */
public class Calendar
{
    private Calendar()
    {
    }

    public static long getHourMillis(long time)
    {
        long hour = 60 * 60 * 1000;

        return ((time / hour) * hour);
    }

    public static long getCurrentHourMillis()
    {
        return getHourMillis(System.currentTimeMillis());
    }
}