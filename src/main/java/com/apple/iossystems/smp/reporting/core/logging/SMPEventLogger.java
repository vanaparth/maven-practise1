package com.apple.iossystems.smp.reporting.core.logging;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class SMPEventLogger
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventLogger.class);

    private final boolean loggingEnabled;

    public SMPEventLogger(boolean loggingEnabled)
    {
        this.loggingEnabled = loggingEnabled;
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