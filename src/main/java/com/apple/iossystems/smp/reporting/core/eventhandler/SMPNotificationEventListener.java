package com.apple.iossystems.smp.reporting.core.eventhandler;

import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
class SMPNotificationEventListener extends SMPEventListener
{
    private SMPNotificationEventLogger smpNotificationEventLogger = new SMPNotificationEventLogger();

    public SMPNotificationEventListener()
    {
    }

    @Override
    public void handleEvent(EventRecords records)
    {
        smpNotificationEventLogger.log(records);
    }
}