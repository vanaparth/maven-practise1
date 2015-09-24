package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.email.service.impl.ssp.domain.*;
import com.apple.iossystems.smp.email.service.impl.ssp.handler.*;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.SMPDeviceEvent;
import com.apple.iossystems.smp.reporting.core.eventhandler.EventListener;
import com.apple.iossystems.smp.reporting.core.eventhandler.EventListenerFactory;
import com.apple.iossystems.smp.reporting.core.util.ValidValue;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Toch
 */
public class EmailService
{
    private static final Logger LOGGER = Logger.getLogger(EmailService.class);

    private static final boolean PROVISION_EMAIL_ENABLED = ApplicationConfiguration.isProvisionEmailEnabled();
    private static final boolean SUSPEND_EMAIL_ENABLED = ApplicationConfiguration.isSuspendEmailEnabled();
    private static final boolean UNLINK_EMAIL_ENABLED = ApplicationConfiguration.isUnlinkEmailEnabled();
    private static final boolean DEFAULT_EMAIL_LOCALE_ENABLED = ApplicationConfiguration.isDefaultEmailLocaleEnabled();

    private XmlProcessor xmlProcessor = XmlProcessor.getInstance();

    private EventListener eventListener = EventListenerFactory.getInstance().getEmailEventListener();
    private EventListener kistaEventListener = EventListenerFactory.getInstance().getSMPKistaEventListener();

    private EmailService()
    {
    }

    public static EmailService getInstance()
    {
        return new EmailService();
    }

    public void send(EventRecords records)
    {
        for (EventRecord record : records.getList())
        {
            sendEventRecord(record);
        }
    }

    private void sendEventRecord(EventRecord record)
    {
        try
        {
            publishManageDeviceEvent(record);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void notifyEventListeners(ProvisionCardEvent provisionCardEvent)
    {
        eventListener.handleEvent(provisionCardEvent);
        kistaEventListener.handleEvent(provisionCardEvent);
    }

    private void notifyEventListeners(ManageDeviceEvent manageDeviceEvent)
    {
        eventListener.handleEvent(manageDeviceEvent);
        kistaEventListener.handleEvent(manageDeviceEvent);

    }

    private String format(String value)
    {
        return ValidValue.getStringValueWithDefault(value, "");
    }

    private String getLocale(String locale)
    {
        return (DEFAULT_EMAIL_LOCALE_ENABLED ? "en_US" : locale);
    }

    private Calendar getCalendar(ManageDeviceEvent manageDeviceEvent)
    {
        String tz = manageDeviceEvent.getTimezone();

        TimeZone timezone = (tz != null) ? TimeZone.getTimeZone(tz) : null;

        Calendar calendar = (timezone != null) ? Calendar.getInstance(timezone) : Calendar.getInstance();

        calendar.setTimeInMillis(Long.parseLong(manageDeviceEvent.getTimestamp()));

        return calendar;
    }

    private boolean isValidManageDeviceEvent(ManageDeviceEvent manageDeviceEvent, SMPDeviceEvent smpDeviceEvent)
    {
        boolean isManageDeviceEvent = ((smpDeviceEvent == SMPDeviceEvent.RESUME_CARD) || (smpDeviceEvent == SMPDeviceEvent.SUSPEND_CARD) || (smpDeviceEvent == SMPDeviceEvent.UNLINK_CARD));

        boolean hasRequiredSource = false;
        boolean hasRequiredValues = false;

        if (manageDeviceEvent != null)
        {
            hasRequiredSource = (manageDeviceEvent.getManageDeviceEventSource() == ManageDeviceEventSource.FMIP);

            hasRequiredValues = StringUtils.isNotBlank(manageDeviceEvent.getCardHolderEmail());
        }

        return (isManageDeviceEvent && hasRequiredSource && hasRequiredValues);
    }

    private boolean isValidProvisionCardEvent(ProvisionCardEvent provisionCardEvent)
    {
        return ((provisionCardEvent != null) && StringUtils.isNotBlank(provisionCardEvent.getCardHolderEmail()));
    }

    public void publishProvisionEvent(ProvisionCardEvent provisionCardEvent)
    {
        if (PROVISION_EMAIL_ENABLED && (provisionCardEvent != null) && isValidProvisionCardEvent(provisionCardEvent))
        {
            try
            {
                new SMPFirstTimeProvisionMailHandler(new FirstTimeProvisionRequest(
                        format(provisionCardEvent.getCardHolderName()),
                        format(provisionCardEvent.getConversationId()),
                        format(provisionCardEvent.getCardHolderEmail()),
                        format(provisionCardEvent.getLocale()),
                        format(xmlProcessor.getValidXmlString(provisionCardEvent.getDeviceName())),
                        format(provisionCardEvent.getDeviceType()),
                        format(provisionCardEvent.getDsid()))).sendEmail();

                notifyEventListeners(provisionCardEvent);
            }
            catch (Exception e)
            {
                LOGGER.error(e);
            }
        }
    }

    private void publishManageDeviceEvent(EventRecord record) throws Exception
    {
        String json = record.getAttributeValue(EventAttribute.MANAGE_DEVICE_EVENT.key());

        ManageDeviceEvent manageDeviceEvent = (json != null) ? GsonBuilderFactory.getInstance().fromJson(json, ManageDeviceEvent.class) : null;

        SMPDeviceEvent smpDeviceEvent = SMPDeviceEvent.getEvent(record);

        if ((manageDeviceEvent != null) && isValidManageDeviceEvent(manageDeviceEvent, smpDeviceEvent))
        {
            CardEventRecord cardEventRecord = CardEventRecord.getCardEventRecord(manageDeviceEvent);
            List<SMPEmailCardData> successCards = cardEventRecord.getSuccessCards();
            List<SMPEmailCardData> failedCards = cardEventRecord.getFailedCards();

            String conversationId = format(record.getAttributeValue(EventAttribute.CONVERSATION_ID.key()));
            String deviceName = format(xmlProcessor.getValidXmlString(manageDeviceEvent.getDeviceName()));
            String cardHolderName = format(manageDeviceEvent.getCardHolderName());
            String cardHolderEmail = format(manageDeviceEvent.getCardHolderEmail());
            String dsid = format(manageDeviceEvent.getDsid());
            String locale = format(getLocale(manageDeviceEvent.getLocale()));
            String deviceType = format(manageDeviceEvent.getDeviceType());
            String deviceImageUrl = format(manageDeviceEvent.getDeviceImageUrl());
            Calendar calendar = getCalendar(manageDeviceEvent);

            FmipSource fmipSource = manageDeviceEvent.getFmipSource();
            String fmipSourceDescription = format((fmipSource != null) ? fmipSource.getDescription() : "");

            if (smpDeviceEvent == SMPDeviceEvent.SUSPEND_CARD)
            {
                if (SUSPEND_EMAIL_ENABLED)
                {
                    if (cardEventRecord.isSuccessful())
                    {
                        new SMPSuccessSuspendMailHandler(new SuspendEmailRequest(deviceName, successCards, calendar, cardHolderName, conversationId, cardHolderEmail, dsid, locale, deviceType, deviceImageUrl)).sendEmail();
                    }
                    else if (cardEventRecord.hasPartialSuccess())
                    {
                        new SMPPartialSuspendMailHandler(new PartialSuspendEmailRequest(deviceName, successCards, calendar, cardHolderName, conversationId, cardHolderEmail, locale, dsid, deviceType, failedCards, deviceImageUrl)).sendEmail();
                    }
                    else if (cardEventRecord.isFailed())
                    {
                        new SMPFailSuspendMailHandler(new SuspendEmailRequest(deviceName, failedCards, calendar, cardHolderName, conversationId, cardHolderEmail, dsid, locale, deviceType, deviceImageUrl)).sendEmail();
                    }
                }
            }
            else if (smpDeviceEvent == SMPDeviceEvent.UNLINK_CARD)
            {
                if (UNLINK_EMAIL_ENABLED)
                {
                    if (cardEventRecord.isSuccessful())
                    {
                        new SMPSuccessRemoveMailHandler(new RemoveEmailRequest(deviceName, successCards, calendar, cardHolderName, conversationId, cardHolderEmail, locale, deviceType, dsid, deviceImageUrl, fmipSourceDescription)).sendEmail();
                    }
                    else if (cardEventRecord.hasPartialSuccess())
                    {
                        new SMPPartialRemoveMailHandler(new PartialRemoveEmailRequest(deviceName, successCards, calendar, cardHolderName, conversationId, cardHolderEmail, locale, dsid, deviceType, failedCards, deviceImageUrl, fmipSourceDescription)).sendEmail();
                    }
                    else if (cardEventRecord.isFailed())
                    {
                        new SMPFailRemoveMailHandler(new RemoveEmailRequest(deviceName, failedCards, calendar, cardHolderName, conversationId, cardHolderEmail, locale, deviceType, dsid, deviceImageUrl, fmipSourceDescription)).sendEmail();
                    }
                }
            }

            notifyEventListeners(manageDeviceEvent);
        }
    }
}