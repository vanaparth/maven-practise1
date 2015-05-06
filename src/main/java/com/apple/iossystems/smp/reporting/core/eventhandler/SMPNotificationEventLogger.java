package com.apple.iossystems.smp.reporting.core.eventhandler;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class SMPNotificationEventLogger
{
    private static final Logger LOGGER = Logger.getLogger(SMPNotificationEventLogger.class);

    private boolean loggingEnabled = PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.notificationEvent.log", false);

    public SMPNotificationEventLogger()
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