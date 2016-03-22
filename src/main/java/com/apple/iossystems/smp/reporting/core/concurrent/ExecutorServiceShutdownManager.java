package com.apple.iossystems.smp.reporting.core.concurrent;

import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ExecutorService;

import static com.apple.iossystems.smp.utils.TimeConstants.ONE_MINUTE_MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author Toch
 */
class ExecutorServiceShutdownManager
{
    private static final Logger LOGGER = Logger.getLogger(ExecutorServiceShutdownManager.class);

    private ExecutorServiceShutdownManager()
    {
    }

    static ExecutorServiceShutdownManager getInstance()
    {
        return new ExecutorServiceShutdownManager();
    }

    void shutdownExecutorService(ExecutorService executorService)
    {
        LOGGER.info("Shutting down executor service");

        executorService.shutdown();

        if (!awaitTermination(executorService))
        {
            List<Runnable> pendingTasks = executorService.shutdownNow();

            logPendingTasks(pendingTasks);

            if (!awaitTermination(executorService))
            {
                LOGGER.error("Failed to terminate executor service");
            }
        }
    }

    private boolean awaitTermination(ExecutorService executorService)
    {
        boolean result = false;

        try
        {
            result = executorService.awaitTermination(ONE_MINUTE_MILLIS, MILLISECONDS);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return result;
    }

    private void logPendingTasks(List<Runnable> pendingTasks)
    {
        if ((pendingTasks != null) && (!pendingTasks.isEmpty()))
        {
            LOGGER.error("Tasks did not execute");

            for (Runnable pendingTask : pendingTasks)
            {
                LOGGER.error(pendingTask);
            }
        }
    }
}