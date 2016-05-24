package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
class NoOpNotificationService implements NotificationService
{
    private NoOpNotificationService()
    {
    }

    static NoOpNotificationService getInstance()
    {
        return new NoOpNotificationService();
    }

    @Override
    public void publishEvents(EventRecords records)
    {
    }

    @Override
    public boolean isOnline()
    {
        return true;
    }
}