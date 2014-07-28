package com.apple.iossystems.smp.reporting.core.timer;

/**
 * @author Toch
 */
public class Timer
{
    private Timer()
    {
    }

    public static boolean delayExpired(long time, int delay)
    {
        return ((System.currentTimeMillis() - time) >= delay);
    }
}