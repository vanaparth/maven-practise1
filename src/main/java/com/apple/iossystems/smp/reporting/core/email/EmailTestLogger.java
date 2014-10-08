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

    private static final boolean LOG_TESTS = ApplicationConfigurationManager.getEmailLog();

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
        StringBuilder message = new StringBuilder();

        message.append("Success Cards: " + record.getSuccessCards().size() + "\tFailed Cards: " + record.getFailedCards().size());

        for (SMPEmailCardData smpEmailCardData : record.getSuccessCards())
        {
            message.append("\tSuccess Card: " + smpEmailCardData.getCardLastFour() + "\t" + smpEmailCardData.getCardShortDescription());
        }

        for (SMPEmailCardData smpEmailCardData : record.getFailedCards())
        {
            message.append("\tFailed Card: " + smpEmailCardData.getCardLastFour() + "\t" + smpEmailCardData.getCardShortDescription());
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
                    "[ Cardholder name: " + manageCardEvent.getCardHolderName() +
                            "\tCardholder email: " + manageCardEvent.getCardHolderEmail() +
                            "\tDsid: " + manageCardEvent.getDsid() +
                            "\tDevice name: " + manageCardEvent.getDeviceName() +
                            "\tDevice image url:" + manageCardEvent.getDeviceImageUrl() +
                            "\tLocale: " + manageCardEvent.getLocale() +
                            "\tManage card api: " + manageCardEvent.getManageCardAPI() +
                            "\tCard event source: " + manageCardEvent.getCardEventSource() +
                            "\tFmip source: " + manageCardEvent.getFmipSource() + " ]";
        }

        return "Name: " + record.getCardHolderName() +
                "\tEvent: " + smpCardEventName +
                "\tEmail: " + record.getCardHolderEmail() +
                "\tFirst provision: " + record.isFirstProvisionEvent() +
                "\tConversation id: " + record.getConversationId() +
                "\tDsid: " + record.getDsid() +
                "\tLocale: " + record.getLocale() +
                "\tDevice name: " + record.getDeviceName() +
                "\tDevice type: " + record.getDeviceType() +
                "\tDevice image url: " + record.getDeviceImageUrl() +
                "\tManage card event: " + manageCardEventValue;

    }
}