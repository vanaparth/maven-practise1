package com.apple.iossystems.smp.reporting.core.concurrent;

import org.apache.log4j.Logger;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Toch
 */
public class ScheduledTask
{
    private static final Logger LOGGER = Logger.getLogger(ScheduledTask.class);

    private final ScheduledTaskHandler scheduledTaskHandler;

    private ScheduledExecutorService scheduledExecutorService;

    private ScheduledTask(ScheduledTaskHandler scheduledTaskHandler, long period)
    {
        this.scheduledTaskHandler = scheduledTaskHandler;

        startTask(period);
    }

    public static ScheduledTask getInstance(ScheduledTaskHandler scheduledTaskHandler, long period)
    {
        return new ScheduledTask(scheduledTaskHandler, period);
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
}