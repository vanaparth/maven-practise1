package com.apple.iossystems.smp.reporting.core.util;

/**
 * @author Toch
 */
public class Calendar
{
    private Calendar()
    {
    }

    private static long getHourInMilliseconds(long timeInMilliseconds)
    {
        long hour = 60 * 60 * 1000;

        return ((timeInMilliseconds / hour) * hour);
    }

    public static long getCurrentHourInMilliseconds()
    {
        return getHourInMilliseconds(System.currentTimeMillis());
    }
}