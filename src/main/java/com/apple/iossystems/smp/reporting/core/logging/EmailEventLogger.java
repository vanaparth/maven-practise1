package com.apple.iossystems.smp.reporting.core.logging;

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
        LogMessage logMessage = new LogMessage();

        if (record != null)
        {
            logMessage.add("SuccessCards", String.valueOf(record.getSuccessCards().size()));
            logMessage.add("FailedCards", String.valueOf(record.getFailedCards().size()));

            for (SMPEmailCardData smpEmailCardData : record.getSuccessCards())
            {
                logMessage.add("SuccessCard", smpEmailCardData.getCardLastFour() + "," + smpEmailCardData.getCardShortDescription());
            }

            for (SMPEmailCardData smpEmailCardData : record.getFailedCards())
            {
                logMessage.add("FailedCard", smpEmailCardData.getCardLastFour() + "," + smpEmailCardData.getCardShortDescription());
            }
        }

        return logMessage.toString();
    }

    public void log(ProvisionCardEvent provisionCardEvent)
    {
        if (provisionCardEvent != null)
        {
            log(new LogMessage()
                    .add("event", "PROVISION_CARD")
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
    }

    public void log(EventRecord record, ManageDeviceEvent manageDeviceEvent, CardEventRecord cardEventRecord)
    {
        if (manageDeviceEvent != null)
        {
            SMPDeviceEvent smpEvent = SMPDeviceEvent.getEvent(record);
            FmipSource fmipSource = manageDeviceEvent.getFmipSource();
            ManageDeviceEventSource manageDeviceEventSource = manageDeviceEvent.getManageDeviceEventSource();

            String smpEventName = (smpEvent != null) ? smpEvent.name() : null;
            String fmipSourceName = (fmipSource != null) ? fmipSource.getDescription() : null;
            String manageDeviceEventName = (manageDeviceEventSource != null) ? manageDeviceEventSource.name() : null;

            log(new LogMessage()
                    .add("event", smpEventName)
                    .add("conversationId", record.getAttributeValue(EventAttribute.CONVERSATION_ID.key()))
                    .add("timestamp", manageDeviceEvent.getTimestamp())
                    .add("cardHolderName", manageDeviceEvent.getCardHolderName())
                    .add("cardHolderEmail", manageDeviceEvent.getCardHolderEmail())
                    .add("dsid", manageDeviceEvent.getDsid())
                    .add("deviceName", manageDeviceEvent.getDeviceName())
                    .add("deviceType", manageDeviceEvent.getDeviceType())
                    .add("deviceImageUrl", manageDeviceEvent.getDeviceImageUrl())
                    .add("timezone", manageDeviceEvent.getTimezone())
                    .add("locale", manageDeviceEvent.getLocale())
                    .add("fmipSource", fmipSourceName)
                    .add("manageDeviceEventSource", manageDeviceEventName)
                    .add("cardEvents", getCardEventRecordString(cardEventRecord)).toString());
        }
    }
}