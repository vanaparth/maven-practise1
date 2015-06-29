package com.apple.iossystems.smp.reporting.core.logging;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class SMPEventLogger
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventLogger.class);

    private boolean loggingEnabled = PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.event.log", false);

    public SMPEventLogger()
    {
    }

    public void log(EventRecords records)
    {
        if (loggingEnabled)
        {
            for (EventRecord record : records.getList())
            {
                LOGGER.info(record);
            }
        }
    }
}