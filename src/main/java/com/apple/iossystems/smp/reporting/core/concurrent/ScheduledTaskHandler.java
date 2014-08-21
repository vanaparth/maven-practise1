package com.apple.iossystems.smp.reporting.core.concurrent;

/**
 * @author Toch
 */
public interface ScheduledTaskHandler
{
    public void begin();

    public void handleEvent(ScheduledEvent event);
}
