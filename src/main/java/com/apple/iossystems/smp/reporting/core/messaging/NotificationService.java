package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.logging.LogLevel;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
public interface NotificationService
{
    void publishEvents(EventRecords records);

    void publishEvent(EventRecord record, LogLevel logLevel);

    boolean isOnline();
}