package com.apple.iossystems.smp.reporting.core.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Toch
 */
public class ScheduledNotification
{
    private ScheduledTaskHandler scheduledTaskHandler;
    private ScheduledEvent scheduledEvent;

    private ScheduledNotification(ScheduledTaskHandler scheduledTaskHandler, ScheduledEvent scheduledEvent, long initialDelay, long period)
    {
        this.scheduledTaskHandler = scheduledTaskHandler;
        this.scheduledEvent = scheduledEvent;

        startTask(initialDelay, period);
    }

    public static ScheduledNotification getInstance(ScheduledTaskHandler scheduledTaskHandler, ScheduledEvent scheduledEvent, long initialDelay, long period)
    {
        return new ScheduledNotification(scheduledTaskHandler, scheduledEvent, initialDelay, period);
    }

    private void startTask(long initialDelay, long period)
    {
        Runnable command = new Runnable()
        {
            @Override
            public void run()
            {
                scheduledTaskHandler.handleEvent(scheduledEvent);
            }
        };

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(command, initialDelay, period, TimeUnit.MILLISECONDS);
    }
}