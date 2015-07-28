package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.concurrent.ThreadPoolExecutorService;

import java.util.concurrent.Callable;

/**
 * @author Toch
 */
public class EventNotificationServiceThreadPool
{
    private static final EventNotificationServiceThreadPool INSTANCE = new EventNotificationServiceThreadPool();

    private final ThreadPoolExecutorService threadPoolExecutorService = ThreadPoolExecutorService.getInstance();

    private EventNotificationServiceThreadPool()
    {
    }

    public static EventNotificationServiceThreadPool getInstance()
    {
        return INSTANCE;
    }

    public void submit(Callable task)
    {
        threadPoolExecutorService.submit(task);
    }
}