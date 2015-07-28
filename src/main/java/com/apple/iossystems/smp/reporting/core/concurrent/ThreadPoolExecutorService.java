package com.apple.iossystems.smp.reporting.core.concurrent;

import com.apple.cds.keystone.config.PropertyManager;

import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author Toch
 */
public class ThreadPoolExecutorService
{
    private final ExecutorService executorService;

    private ThreadPoolExecutorService()
    {
        PropertyManager propertyManager = PropertyManager.getInstance();

        int poolSize = propertyManager.getIntValueForKeyWithDefault("keystone.async.threadpool.size", 10);
        int queueSize = propertyManager.getIntValueForKeyWithDefault("keystone.async.queue.size", 1000);

        executorService = new ThreadPoolExecutor(poolSize / 2, poolSize, TimeUnit.MINUTES.toMillis(5), MILLISECONDS, new ArrayBlockingQueue<Runnable>(queueSize, true), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static ThreadPoolExecutorService getInstance()
    {
        return new ThreadPoolExecutorService();
    }

    public void submit(Callable task)
    {
        executorService.submit(task);
    }
}