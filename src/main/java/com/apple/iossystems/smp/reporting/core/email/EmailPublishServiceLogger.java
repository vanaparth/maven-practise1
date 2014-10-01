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

        if (hasRequiredValues(record))
        {
            message = "Sending email " + message;
        }
        else
        {
            message = "Unable to send email " + message;
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
        if (value != null)
        {
            message.append(key + "=" + value + " ");
        }
    }

    private static boolean hasRequiredValues(EmailRecord record)
    {
        return ((record == null) || (record.getCardHolderEmail() != null));
    }

    private static boolean doLog(EmailRecord record)
    {
        SMPCardEvent smpCardEvent = record.getSMPCardEvent();

        return ((smpCardEvent == SMPCardEvent.PROVISION_CARD) || (smpCardEvent == SMPCardEvent.SUSPEND_CARD) || (smpCardEvent == SMPCardEvent.UNLINK_CARD));
    }

    public static void logTests(EmailRecord record, CardEventRecord cardEventRecord)
    {
        // Logging for email tests
        if ((record != null) && doLog(record))
        {
            SMPCardEvent smpCardEvent = record.getSMPCardEvent();
            String smpCardEventName = (smpCardEvent != null) ? smpCardEvent.name() : "Unknown Event";

            LOGGER.info("Sending email for " + smpCardEventName + "\t" +
                    "Name: " + record.getCardHolderName() + "\t" +
                    "Email: " + record.getCardHolderEmail() + "\t" +
                    "Date: " + record.getDate() + "\t" +
                    "First provision: " + record.isFirstProvisionEvent() + "\t" +
                    "Conversation id: " + record.getConversationId() + "\t" +
                    "Dsid: " + record.getDsid() + "\t" +
                    "Locale: " + record.getLocale() + "\t" +
                    "Device name: " + record.getDeviceName() + "\t" +
                    "Device type: " + record.getDeviceType() + "\t" +
                    "Device image url: " + record.getDeviceImageUrl() + "\t" +
                    "Success Cards: " + cardEventRecord.getSuccessCards().size() + "\t" +
                    "Failed Cards: " + cardEventRecord.getFailedCards().size());
        }
    }
}