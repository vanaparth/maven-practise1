package com.apple.iossystems.smp.reporting.core.concurrent;

import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Toch
 */
public class ScheduledNotification
{
    private static final Logger LOGGER = Logger.getLogger(ScheduledNotification.class);

    private ScheduledTaskHandler scheduledTaskHandler;

    private ScheduledExecutorService scheduledExecutorService;

    private ScheduledNotification(ScheduledTaskHandler scheduledTaskHandler, long period)
    {
        this.scheduledTaskHandler = scheduledTaskHandler;

        startTask(period);
    }

    public static ScheduledNotification getInstance(ScheduledTaskHandler scheduledTaskHandler, long period)
    {
        return new ScheduledNotification(scheduledTaskHandler, period);
    }

    private void startTask(long period)
    {
        Runnable command = new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    scheduledTaskHandler.handleEvent();
                }
                catch (Exception e)
                {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        };

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

        scheduledExecutorService.scheduleWithFixedDelay(command, 0, period, TimeUnit.MILLISECONDS);
    }

    public void shutdown()
    {
        scheduledExecutorService.shutdown();
    }

    public boolean isShutdown()
    {
        return scheduledExecutorService.isShutdown();
    }
}