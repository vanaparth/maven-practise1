package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
import com.apple.iossystems.smp.reporting.core.event.SMPCardEvent;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class EmailTestLogger
{
    private static final Logger LOGGER = Logger.getLogger(EmailTestLogger.class);

    private static final boolean LOG_TESTS = ApplicationConfigurationManager.getIReporterURL().contains("https://icloud4-e3.icloud.com");

    private EmailTestLogger()
    {
    }

    public static void log(EmailRecord record, boolean requestSent)
    {
        if (LOG_TESTS && (record != null) && EmailPublishServiceLogger.doLog(record))
        {
            CardEventRecord cardEventRecord = CardEventRecord.getCardEventRecord(record);

            LOGGER.info("Sending email [" + requestSent + "] for " + getEmailRecordString(record) + "\t" + getCardEventRecordString(cardEventRecord));
        }
    }

    private static String getCardEventRecordString(CardEventRecord record)
    {
        return "Success Cards: " + record.getSuccessCards().size() + "\t" + "Failed Cards: " + record.getFailedCards().size();
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
                            "Card event source: " + manageCardEvent.getCardEventSource() + "\t" +
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
}