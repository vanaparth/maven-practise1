package com.apple.iossystems.smp.reporting.core.eventhandler;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.logging.SMPEventLogger;

/**
 * @author Toch
 */
class SMPConsumeEventListener extends SMPEventListener
{
    private final SMPEventLogger smpEventLogger = new SMPEventLogger(PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.consume.event.log", false));

    SMPConsumeEventListener()
    {
    }

    @Override
    public void handleEvent(EventRecords records)
    {
        smpEventLogger.log(records);
    }
}