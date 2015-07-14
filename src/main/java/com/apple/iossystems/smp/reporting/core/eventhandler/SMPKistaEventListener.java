package com.apple.iossystems.smp.reporting.core.eventhandler;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.logging.KistaEventLogger;

/**
 * @author Toch
 */
public class SMPKistaEventListener extends SMPEventListener
{
    private KistaEventLogger kistaEventLogger = new KistaEventLogger(PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.kista.event.log", false));

    public SMPKistaEventListener()
    {
    }

    @Override
    public void handleEvent(EventRecords records)
    {
        kistaEventLogger.log(records);
    }
}