package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.SMPDeviceEvent;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class EmailPublishServiceLogger
{
    private static final Logger LOGGER = Logger.getLogger(EmailPublishServiceLogger.class);

    private EmailPublishServiceLogger()
    {
    }

    public static void log(EmailRecord emailRecord, boolean requestSent)
    {
        log(emailRecord);
        EmailTestLogger.log(emailRecord, requestSent);
    }

    public static void log(EmailRecord record)
    {
        if ((record != null) && doLog(record))
        {
            String message = getLogMessage(record);

            if ((message != null) && (!message.isEmpty()))
            {
                LOGGER.info(message);
            }
        }
    }

    private static String getLogMessage(EmailRecord record)
    {
        String message = getMessage(record);

        if (EmailRecordFilter.hasRequiredValues(record))
        {
            message = "Sending email " + message;
        }
        else
        {
            message = "Not sending email " + message;
        }

        return message;
    }

    private static String getMessage(EmailRecord record)
    {
        StringBuilder message = new StringBuilder();

        SMPDeviceEvent smpEvent = record.getSMPEvent();
        String smpEventName = (smpEvent != null) ? smpEvent.toString() : "Unknown Event";

        appendMessage(message, "event", smpEventName);
        appendMessage(message, "conversationId", record.getConversationId());
        appendMessage(message, "dsid", record.getDsid());

        return message.toString().trim();
    }

    private static void appendMessage(StringBuilder message, String key, String value)
    {
        message.append(key + "=" + value + " ");
    }

    public static boolean doLog(EmailRecord record)
    {
        SMPDeviceEvent smpEvent = record.getSMPEvent();

        return ((smpEvent == SMPDeviceEvent.PROVISION_CARD) || (smpEvent == SMPDeviceEvent.SUSPEND_CARD) || (smpEvent == SMPDeviceEvent.UNLINK_CARD) || (smpEvent == SMPDeviceEvent.RESUME_CARD));
    }
}