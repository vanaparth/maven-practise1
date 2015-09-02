package com.apple.iossystems.smp.reporting.core.messaging;

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
    public boolean isOnline()
    {
        return false;
    }
}