package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
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

        if (EmailRecordFilter.hasRequiredValues(record))
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

    private static boolean doLog(EmailRecord record)
    {
        SMPCardEvent smpCardEvent = record.getSMPCardEvent();

        return ((smpCardEvent == SMPCardEvent.PROVISION_CARD) || (smpCardEvent == SMPCardEvent.SUSPEND_CARD) || (smpCardEvent == SMPCardEvent.UNLINK_CARD) || (smpCardEvent == SMPCardEvent.RESUME_CARD));
    }

    // Logging for email tests
    private static final boolean LOG_TESTS = ApplicationConfigurationManager.getIReporterURL().equals("https://icloud4-e3.icloud.com");

    public static void logTests(EmailRecord record, CardEventRecord cardEventRecord)
    {
        if (LOG_TESTS && (record != null) && doLog(record))
        {
            LOGGER.info("Sending email for " + getEmailRecordString(record) + "\t" + getCardEventRecordString(cardEventRecord));
        }
    }

    private static String getEmailRecordString(EmailRecord record)
    {
        SMPCardEvent smpCardEvent = record.getSMPCardEvent();
        String smpCardEventName = (smpCardEvent != null) ? smpCardEvent.name() : "Unknown Event";

        ManageCardEvent manageCardEvent = record.getManageCardEvent();
        String manageCardEventValue = "";

        if (manageCardEvent != null)
        {
            manageCardEventValue =
                    "[Cardholder name: " + manageCardEvent.getCardHolderName() + "\t" +
                            "Cardholder email: " + manageCardEvent.getCardHolderEmail() + "\t" +
                            "Dsid: " + manageCardEvent.getDsid() + "\t" +
                            "Device name: " + manageCardEvent.getDeviceName() + "\t" +
                            "Device image url:" + manageCardEvent.getDeviceImageUrl() + "\t" +
                            "Locale: " + manageCardEvent.getLocale() + "\t" +
                            "Manage card api: " + manageCardEvent.getManageCardAPI() + "\t" +
                            "Card event source: " + manageCardEvent.getCardEventSource() + "t" +
                            "Fmip source: " + manageCardEvent.getFmipSource() + "]";
        }

        return "Name: " + record.getCardHolderName() + "\t" +
                "Event: " + smpCardEventName + "\t" +
                "Email: " + record.getCardHolderEmail() + "\t" +
                "First provision: " + record.isFirstProvisionEvent() + "\t" +
                "Conversation id: " + record.getConversationId() + "\t" +
                "Dsid: " + record.getDsid() + "\t" +
                "Locale: " + record.getLocale() + "\t" +
                "Device name: " + record.getDeviceName() + "\t" +
                "Device type: " + record.getDeviceType() + "\t" +
                "Device image url: " + record.getDeviceImageUrl() + "\t" +
                "Manage card event: " + manageCardEventValue;

    }

    private static String getCardEventRecordString(CardEventRecord record)
    {
        return "Success Cards: " + record.getSuccessCards().size() + "\t" + "Failed Cards: " + record.getFailedCards().size();
    }
}