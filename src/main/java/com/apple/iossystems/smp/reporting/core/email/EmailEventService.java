package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
public interface EmailEventService
{
    void send(EventRecords records);

    void processProvisionEvent(ProvisionCardEvent provisionCardEvent);
}