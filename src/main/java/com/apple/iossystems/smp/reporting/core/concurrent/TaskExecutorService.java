package com.apple.iossystems.smp.reporting.core.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author Toch
 */
public interface TaskExecutorService
{
    <T> Future<T> submit(Callable<T> task);
}