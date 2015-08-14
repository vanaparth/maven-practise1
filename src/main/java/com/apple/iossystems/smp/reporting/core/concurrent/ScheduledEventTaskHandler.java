package com.apple.iossystems.smp.reporting.core.concurrent;

/**
 * @author Toch
 */
public abstract class ScheduledEventTaskHandler implements ScheduledTaskHandler
{
    private ScheduledNotification scheduledNotification;

    public ScheduledEventTaskHandler()
    {
        startScheduledTask();
    }

    private void startScheduledTask()
    {
        scheduledNotification = ScheduledNotification.getInstance(this, 60 * 1000);
    }

    public void shutdown()
    {
        scheduledNotification.shutdown();
    }

    public boolean isShutdown()
    {
        return scheduledNotification.isShutdown();
    }
}