package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
class OfflineEmailService implements EmailEventService
{
    private OfflineEmailService()
    {
    }

    public static OfflineEmailService getInstance()
    {
        return new OfflineEmailService();
    }

    @Override
    public void send(EventRecords records)
    {
    }

    @Override
    public void publishProvisionEvent(ProvisionCardEvent provisionCardEvent)
    {
    }
}