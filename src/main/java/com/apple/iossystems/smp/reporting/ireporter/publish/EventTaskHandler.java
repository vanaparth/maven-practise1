package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTaskHandler;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;

/**
 * @author Toch
 */
public interface EventTaskHandler extends ScheduledTaskHandler
{
    void processEventRecord(EventRecord record);
}