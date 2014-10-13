package com.apple.iossystems.smp.reporting.core.timer;

/**
 * @author Toch
 */
public class StopWatch
{
    private long startTime;
    private long endTime;

    private StopWatch()
    {
    }

    public static StopWatch getInstance()
    {
        return new StopWatch();
    }

    public void start()
    {
        startTime = System.currentTimeMillis();
    }

    public void stop()
    {
        endTime = System.currentTimeMillis();
    }

    public long getTimeMillis()
    {
        return (endTime - startTime);
    }
}