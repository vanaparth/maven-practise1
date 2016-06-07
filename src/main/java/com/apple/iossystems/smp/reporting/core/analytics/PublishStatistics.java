package com.apple.iossystems.smp.reporting.core.analytics;

/**
 * @author Toch
 */
public class PublishStatistics
{
    private long startPublishTime = 0;
    private long endPublishTime = 0;
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
        long time = System.currentTimeMillis();

        if (publishStatus == status)
        {
            if (startPublishTime == 0)
            {
                startPublishTime = time;
            }

            endPublishTime = time;
            publishCount++;
        }
        else
        {
            startPublishTime = time;
            endPublishTime = time;

            publishStatus = status;
            publishCount = 1;
        }
    }

    public long getPublishStatusDuration()
    {
        return (endPublishTime - startPublishTime);
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