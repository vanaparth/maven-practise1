package com.apple.iossystems.smp.reporting.core.concurrent;

import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author Toch
 */
@Service
class SMPTaskExecutorService implements TaskExecutorService
{
    private final ThreadPoolExecutorService executorService = ThreadPoolExecutorService.getInstance();

    @PreDestroy
    private void destroy()
    {
        executorService.destroy();
    }

    @Override
    public <T> Future<T> submit(Callable<T> task)
    {
        return executorService.submit(task);
    }
}