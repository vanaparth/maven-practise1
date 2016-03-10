package com.apple.iossystems.smp.reporting.core.analytics;

/**
 * @author Toch
 */
public class PublishStatistics
{
    private long publishTime = 0;

    private PublishStatistics()
    {
    }

    public static PublishStatistics getInstance()
    {
        return new PublishStatistics();
    }

    public long getPublishTime()
    {
        return publishTime;
    }

    public void updatePublishTime()
    {
        publishTime = System.currentTimeMillis();
    }

    public void setPublishTime(long time)
    {
        publishTime = time;
    }
}