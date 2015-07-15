package com.apple.iossystems.smp.reporting.core.eventhandler;

import com.apple.iossystems.smp.reporting.core.email.ManageDeviceEvent;
import com.apple.iossystems.smp.reporting.core.email.ProvisionCardEvent;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
abstract class SMPEventListener implements EventListener
{
    @Override
    public void handleEvent(EventRecords records)
    {
    }

    @Override
    public void handleEvent(ProvisionCardEvent provisionCardEvent)
    {
    }

    @Override
    public void handleEvent(ManageDeviceEvent manageDeviceEvent)
    {
    }
}