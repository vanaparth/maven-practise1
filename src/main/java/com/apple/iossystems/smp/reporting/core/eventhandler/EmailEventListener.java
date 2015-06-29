package com.apple.iossystems.smp.reporting.core.eventhandler;

import com.apple.iossystems.smp.reporting.core.email.CardEventRecord;
import com.apple.iossystems.smp.reporting.core.email.ManageDeviceEvent;
import com.apple.iossystems.smp.reporting.core.email.ProvisionCardEvent;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.logging.EmailEventLogger;

/**
 * @author Toch
 */
class EmailEventListener extends SMPEventListener
{
    private EmailEventLogger emailEventLogger = new EmailEventLogger();

    public EmailEventListener()
    {
    }

    @Override
    public void handleEvent(ProvisionCardEvent provisionCardEvent)
    {
        emailEventLogger.log(provisionCardEvent);
    }

    @Override
    public void handleEvent(EventRecord record, ManageDeviceEvent manageDeviceEvent, CardEventRecord cardEventRecord)
    {
        emailEventLogger.log(record, manageDeviceEvent, cardEventRecord);
    }
}