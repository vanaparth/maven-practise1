package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
public interface NotificationService
{
    void publishEvents(EventRecords records);

    boolean isOnline();
}