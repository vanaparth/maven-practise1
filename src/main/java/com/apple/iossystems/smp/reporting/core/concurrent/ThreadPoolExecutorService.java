package com.apple.iossystems.smp.reporting.core.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Toch
 */
public class ThreadPoolExecutorService
{
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private ThreadPoolExecutorService()
    {
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