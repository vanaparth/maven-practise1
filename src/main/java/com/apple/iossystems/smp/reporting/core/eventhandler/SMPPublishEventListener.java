package com.apple.iossystems.smp.reporting.core.eventhandler;

import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.logging.SMPEventLogger;

/**
 * @author Toch
 */
class SMPPublishEventListener extends SMPEventListener
{
    private SMPEventLogger smpEventLogger = new SMPEventLogger();

    public SMPPublishEventListener()
    {
    }

    @Override
    public void handleEvent(EventRecords records)
    {
        smpEventLogger.log(records);
    }
}