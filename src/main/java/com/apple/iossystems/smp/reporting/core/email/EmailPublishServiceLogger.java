package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.SMPCardEvent;
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

        SMPCardEvent smpCardEvent = record.getSMPCardEvent();
        String smpCardEventName = (smpCardEvent != null) ? smpCardEvent.toString() : "Unknown Event";

        appendMessage(message, "event", smpCardEventName);
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
        SMPCardEvent smpCardEvent = record.getSMPCardEvent();

        return ((smpCardEvent == SMPCardEvent.PROVISION_CARD) || (smpCardEvent == SMPCardEvent.SUSPEND_CARD) || (smpCardEvent == SMPCardEvent.UNLINK_CARD) || (smpCardEvent == SMPCardEvent.RESUME_CARD));
    }
}