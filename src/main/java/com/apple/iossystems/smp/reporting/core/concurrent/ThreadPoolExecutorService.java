package com.apple.iossystems.smp.reporting.core.concurrent;

import com.apple.cds.keystone.config.PropertyManager;

import java.util.concurrent.*;

import static com.apple.iossystems.smp.utils.TimeConstants.FIVE_MINUTES_MILLIS;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author Toch
 */
class ThreadPoolExecutorService
{
    private final ExecutorService executorService = getExecutorService();

    private ThreadPoolExecutorService()
    {
    }

    static ThreadPoolExecutorService getInstance()
    {
        return new ThreadPoolExecutorService();
    }

    private ExecutorService getExecutorService()
    {
        PropertyManager propertyManager = PropertyManager.getInstance();

        int poolSize = propertyManager.getIntValueForKeyWithDefault("keystone.async.threadpool.size", 10);
        int queueSize = propertyManager.getIntValueForKeyWithDefault("keystone.async.queue.size", 1000);

        return new ThreadPoolExecutor(poolSize / 2, poolSize, FIVE_MINUTES_MILLIS, MILLISECONDS, new ArrayBlockingQueue<Runnable>(queueSize, true), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    void destroy()
    {
        ExecutorServiceShutdownManager.getInstance().shutdownExecutorService(executorService);
    }

    <T> Future<T> submit(Callable<T> task)
    {
        return executorService.submit(task);
    }
}