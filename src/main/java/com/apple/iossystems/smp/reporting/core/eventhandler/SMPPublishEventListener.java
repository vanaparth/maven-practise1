package com.apple.iossystems.smp.reporting.core.eventhandler;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.logging.SMPEventLogger;

/**
 * @author Toch
 */
class SMPPublishEventListener extends SMPEventListener
{
    private final SMPEventLogger smpEventLogger = new SMPEventLogger(PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.publish.event.log", false));

    SMPPublishEventListener()
    {
    }

    @Override
    public void handleEvent(EventRecords records)
    {
        smpEventLogger.log(records);
    }
}