package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
public class NoOpNotificationService implements NotificationService
{
    private NoOpNotificationService()
    {
    }

    public static NoOpNotificationService getInstance()
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