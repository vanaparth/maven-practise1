package com.apple.iossystems.smp.reporting.core.eventhandler;

import com.apple.iossystems.smp.reporting.core.email.CardEventRecord;
import com.apple.iossystems.smp.reporting.core.email.ManageDeviceEvent;
import com.apple.iossystems.smp.reporting.core.email.ProvisionCardEvent;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
public interface EventListener
{
    void handleEvent(EventRecords records);

    void handleEvent(ProvisionCardEvent provisionCardEvent);

    void handleEvent(EventRecord record, ManageDeviceEvent manageDeviceEvent, CardEventRecord cardEventRecord);
}