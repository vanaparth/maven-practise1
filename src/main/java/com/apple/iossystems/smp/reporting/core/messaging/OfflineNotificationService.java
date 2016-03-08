package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.logging.LogLevel;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
class OfflineNotificationService implements NotificationService
{
    private OfflineNotificationService()
    {
    }

    public static OfflineNotificationService getInstance()
    {
        return new OfflineNotificationService();
    }

    @Override
    public void publishEvents(EventRecords records)
    {
    }

    @Override
    public void publishEvent(EventRecord record, LogLevel logLevel)
    {
    }

    @Override
    public boolean isOnline()
    {
        return false;
    }
}