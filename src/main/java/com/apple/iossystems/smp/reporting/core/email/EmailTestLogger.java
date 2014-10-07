package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.email.service.impl.ssp.domain.SMPEmailCardData;
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

            LOGGER.info("Sending email [" + requestSent + "] for " + getEmailRecordString(record) + " " + getCardEventRecordString(cardEventRecord));
        }
    }

    private static String getCardEventRecordString(CardEventRecord record)
    {
        StringBuilder message = new StringBuilder();

        message.append("Success Cards: " + record.getSuccessCards().size() + " Failed Cards: " + record.getFailedCards().size());

        for (SMPEmailCardData smpEmailCardData : record.getSuccessCards())
        {
            message.append("Success Card: " + smpEmailCardData.getCardLastFour() + " " + smpEmailCardData.getCardShortDescription());
        }

        for (SMPEmailCardData smpEmailCardData : record.getFailedCards())
        {
            message.append("Failed Card: " + smpEmailCardData.getCardLastFour() + " " + smpEmailCardData.getCardShortDescription());
        }

        return message.toString();
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
                    "[Cardholder name: " + manageCardEvent.getCardHolderName() +
                            " Cardholder email: " + manageCardEvent.getCardHolderEmail() +
                            " Dsid: " + manageCardEvent.getDsid() +
                            " Device name: " + manageCardEvent.getDeviceName() +
                            " Device image url:" + manageCardEvent.getDeviceImageUrl() +
                            " Locale: " + manageCardEvent.getLocale() +
                            " Manage card api: " + manageCardEvent.getManageCardAPI() +
                            " Card event source: " + manageCardEvent.getCardEventSource() +
                            " Fmip source: " + manageCardEvent.getFmipSource() + "]";
        }

        return "Name: " + record.getCardHolderName() +
                " Event: " + smpCardEventName +
                " Email: " + record.getCardHolderEmail() +
                " First provision: " + record.isFirstProvisionEvent() +
                " Conversation id: " + record.getConversationId() +
                " Dsid: " + record.getDsid() +
                " Locale: " + record.getLocale() +
                " Device name: " + record.getDeviceName() +
                " Device type: " + record.getDeviceType() +
                " Device image url: " + record.getDeviceImageUrl() +
                " Manage card event: " + manageCardEventValue;

    }
}