package com.apple.iossystems.smp.reporting.core.concurrent;

import com.apple.cds.keystone.config.PropertyManager;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.*;

import static com.apple.iossystems.smp.utils.TimeConstants.FIVE_MINUTES_MILLIS;
import static com.apple.iossystems.smp.utils.TimeConstants.ONE_MINUTE_MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author Toch
 */
class ThreadPoolExecutorService
{
    private static final Logger LOGGER = Logger.getLogger(ThreadPoolExecutorService.class);

    private final ExecutorService executorService = createExecutorService();

    private ThreadPoolExecutorService()
    {
    }

    static ThreadPoolExecutorService getInstance()
    {
        return new ThreadPoolExecutorService();
    }

    private ExecutorService createExecutorService()
    {
        PropertyManager propertyManager = PropertyManager.getInstance();

        int poolSize = propertyManager.getIntValueForKeyWithDefault("keystone.async.threadpool.size", 10);
        int queueSize = propertyManager.getIntValueForKeyWithDefault("keystone.async.queue.size", 1000);

        return new ThreadPoolExecutor(poolSize / 2, poolSize, FIVE_MINUTES_MILLIS, MILLISECONDS, new ArrayBlockingQueue<Runnable>(queueSize, true), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    void destroy()
    {
        LOGGER.info("Shutting down executor service");

        executorService.shutdown();

        if (!awaitTermination())
        {
            List<Runnable> pendingTasks = executorService.shutdownNow();

            logPendingTasks(pendingTasks);

            if (!awaitTermination())
            {
                LOGGER.error("Failed to terminate executor service");
            }
        }
    }

    private boolean awaitTermination()
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

    <T> Future<T> submit(Callable<T> task)
    {
        return executorService.submit(task);
    }
}