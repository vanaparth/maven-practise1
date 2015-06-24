package com.apple.iossystems.smp.reporting.core.eventhandler;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.smp.email.service.impl.ssp.domain.SMPEmailCardData;
import com.apple.iossystems.smp.reporting.core.email.*;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.SMPDeviceEvent;
import com.apple.iossystems.smp.reporting.core.util.LogMessage;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class EmailEventLogger
{
    private static final Logger LOGGER = Logger.getLogger(EmailEventLogger.class);

    private boolean loggingEnabled = PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.email.log", false);

    public EmailEventLogger()
    {
    }

    private void log(String message)
    {
        if (loggingEnabled)
        {
            LOGGER.info(message);
        }
    }

    private String getCardEventRecordString(CardEventRecord record)
    {
        StringBuilder message = new StringBuilder();

        if (record != null)
        {
            message.append("SuccessCards=").append(record.getSuccessCards().size()).append(", FailedCards=").append(record.getFailedCards().size());

            for (SMPEmailCardData smpEmailCardData : record.getSuccessCards())
            {
                message.append(", SuccessCard=[").append(smpEmailCardData.getCardLastFour()).append(", ").append(smpEmailCardData.getCardShortDescription()).append("]");
            }

            for (SMPEmailCardData smpEmailCardData : record.getFailedCards())
            {
                message.append(", FailedCard=[").append(smpEmailCardData.getCardLastFour()).append(", ").append(smpEmailCardData.getCardShortDescription()).append("]");
            }
        }

        return message.toString();
    }

    public void log(ProvisionCardEvent provisionCardEvent)
    {
        log(new LogMessage()
                .add("PROVISION_CARD")
                .add("conversationId", provisionCardEvent.getConversationId())
                .add("timestamp", provisionCardEvent.getTimestamp())
                .add("cardHolderName", provisionCardEvent.getCardHolderName())
                .add("cardHolderEmail", provisionCardEvent.getCardHolderEmail())
                .add("cardDisplayNumber", provisionCardEvent.getCardDisplayNumber())
                .add("dsid", provisionCardEvent.getDsid())
                .add("deviceName", provisionCardEvent.getDeviceName())
                .add("deviceType", provisionCardEvent.getDeviceType())
                .add("locale", provisionCardEvent.getLocale()).toString());
    }

    public void log(EventRecord record, ManageDeviceEvent manageDeviceEvent, CardEventRecord cardEventRecord)
    {
        SMPDeviceEvent smpEvent = SMPDeviceEvent.getEvent(record);
        FmipSource fmipSource = manageDeviceEvent.getFmipSource();
        ManageDeviceEventSource manageDeviceEventSource = manageDeviceEvent.getManageDeviceEventSource();

        String smpEventName = (smpEvent != null) ? smpEvent.name() : null;
        String fmipSourceName = (fmipSource != null) ? fmipSource.getDescription() : null;
        String manageDeviceEventName = (manageDeviceEventSource != null) ? manageDeviceEventSource.name() : null;

        log(new LogMessage()
                .add(smpEventName)
                .add("conversationId", record.getAttributeValue(EventAttribute.CONVERSATION_ID.key()))
                .add("cardHolderName", manageDeviceEvent.getCardHolderName())
                .add("cardHolderEmail", manageDeviceEvent.getCardHolderEmail())
                .add("dsid", manageDeviceEvent.getDsid())
                .add("deviceName", manageDeviceEvent.getDeviceName())
                .add("deviceType", manageDeviceEvent.getDeviceType())
                .add("deviceImageUrl", manageDeviceEvent.getDeviceImageUrl())
                .add("timestamp", manageDeviceEvent.getTimestamp())
                .add("timezone", manageDeviceEvent.getTimezone())
                .add("locale", manageDeviceEvent.getLocale())
                .add("fmipSource", fmipSourceName)
                .add("manageDeviceEventSource", manageDeviceEventName)
                .add("cardEvents", getCardEventRecordString(cardEventRecord)).toString());
    }
}