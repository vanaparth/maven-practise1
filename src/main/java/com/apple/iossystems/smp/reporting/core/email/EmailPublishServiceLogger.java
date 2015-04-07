package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.email.service.impl.ssp.domain.SMPEmailCardData;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.SMPDeviceEvent;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class EmailPublishServiceLogger
{
    private static final Logger LOGGER = Logger.getLogger(EmailPublishServiceLogger.class);

    private static final boolean EMAIL_LOGGING_ENABLED = ApplicationConfigurationManager.isEmailLoggingEnabled();

    private EmailPublishServiceLogger()
    {
    }

    private static void log(String message)
    {
        if (EMAIL_LOGGING_ENABLED)
        {
            LOGGER.info(message);
        }
    }

    public static void log(ProvisionCardEvent provisionCardEvent)
    {
        log("PROVISION_CARD " +
                ", conversationId=" + provisionCardEvent.getConversationId() +
                ", timestamp=" + provisionCardEvent.getTimestamp() +
                ", cardHolderName= " + provisionCardEvent.getCardHolderName() +
                ", cardHolderEmail= " + provisionCardEvent.getCardHolderEmail() +
                ", cardDisplayNumber= " + provisionCardEvent.getCardDisplayNumber() +
                ", dsid= " + provisionCardEvent.getDsid() +
                ", deviceName= " + provisionCardEvent.getDeviceName() +
                ", deviceType= " + provisionCardEvent.getDeviceType() +
                ", locale= " + provisionCardEvent.getLocale());

    }

    public static void log(EventRecord record, ManageDeviceEvent manageDeviceEvent, CardEventRecord cardEventRecord)
    {
        SMPDeviceEvent smpEvent = SMPDeviceEvent.getSMPEvent(record);

        log(smpEvent +
                ", conversationId=" + record.getAttributeValue(EventAttribute.CONVERSATION_ID.key()) +
                ", timestamp=" + manageDeviceEvent.getTimestamp() +
                ", cardHolderName= " + manageDeviceEvent.getCardHolderName() +
                ", cardHolderEmail= " + manageDeviceEvent.getCardHolderEmail() +
                ", dsid= " + manageDeviceEvent.getDsid() +
                ", deviceName= " + manageDeviceEvent.getDeviceName() +
                ", deviceType= " + manageDeviceEvent.getDeviceType() +
                ", deviceImageUrl= " + manageDeviceEvent.getDeviceImageUrl() +
                ", locale= " + manageDeviceEvent.getLocale() +
                ", fmipSource= " + manageDeviceEvent.getFmipSource() +
                ", manageDeviceEventSource=" + manageDeviceEvent.getManageDeviceEventSource() +
                ", " + getCardEventRecordString(cardEventRecord));
    }

    private static String getCardEventRecordString(CardEventRecord record)
    {
        StringBuilder message = new StringBuilder();

        message.append("SuccessCards=").append(record.getSuccessCards().size()).append(", FailedCards=").append(record.getFailedCards().size());

        for (SMPEmailCardData smpEmailCardData : record.getSuccessCards())
        {
            message.append(", SuccessCard=").append(smpEmailCardData.getCardLastFour()).append(", ").append(smpEmailCardData.getCardShortDescription());
        }

        for (SMPEmailCardData smpEmailCardData : record.getFailedCards())
        {
            message.append(", FailedCard=").append(smpEmailCardData.getCardLastFour()).append(", ").append(smpEmailCardData.getCardShortDescription());
        }

        return message.toString();
    }
}