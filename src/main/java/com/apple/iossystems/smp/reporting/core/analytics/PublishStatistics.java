package com.apple.iossystems.smp.reporting.core.analytics;

/**
 * @author Toch
 */
public class PublishStatistics
{
    private long publishTime = 0;
    private int publishCount = 0;
    private boolean publishStatus = true;

    private PublishStatistics()
    {
    }

    public static PublishStatistics getInstance()
    {
        return new PublishStatistics();
    }

    private void updateForEvent(boolean status)
    {
        publishTime = System.currentTimeMillis();

        if (publishStatus == status)
        {
            publishCount++;
        }
        else
        {
            publishStatus = status;
            publishCount = 1;
        }
    }

    public long getPublishTime()
    {
        return publishTime;
    }

    public int getPublishCount()
    {
        return publishCount;
    }

    public boolean getPublishStatus()
    {
        return publishStatus;
    }

    public void updateForSuccessEvent()
    {
        updateForEvent(true);
    }

    public void updateForFailedEvent()
    {
        updateForEvent(false);
    }
}