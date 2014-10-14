package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.email.service.impl.ssp.domain.*;
import com.apple.iossystems.smp.email.service.impl.ssp.handler.*;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.SMPCardEvent;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * @author Toch
 */
public class EmailPublishService
{
    private static final Logger LOGGER = Logger.getLogger(EmailPublishService.class);

    private static final boolean EMAIL_PROVISION = ApplicationConfigurationManager.getEmailProvision();
    private static final boolean EMAIL_SUSPEND = ApplicationConfigurationManager.getEmailSuspend();
    private static final boolean EMAIL_UNLINK = ApplicationConfigurationManager.getEmailUnlink();
    private static final boolean EMAIL_LOCALE = ApplicationConfigurationManager.getEmailLocale();

    private EmailPublishService()
    {
    }

    public static void send(EventRecords records)
    {
        for (EventRecord record : records.getList())
        {
            sendEventRecord(record);
        }
    }

    private static void sendEventRecord(EventRecord record)
    {
        try
        {
            EmailRecord emailRecord = getEmailRecord(record);

            boolean requestSent = false;

            if (emailRecord != null)
            {
                if (EmailRecordFilter.isValidEmailRecord(emailRecord))
                {
                    sendRequest(emailRecord);

                    requestSent = true;
                }

                EmailPublishServiceLogger.log(emailRecord, requestSent);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private static void sendRequest(EmailRecord emailRecord)
    {
        try
        {
            doSendRequest(emailRecord);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private static EmailRecord getEmailRecord(EventRecord record)
    {
        EmailRecord emailRecord = null;

        if (EmailRecordFilter.isProvisionEventRecord(record))
        {
            emailRecord = ProvisionCardEvent.getEmailRecord(record);
        }
        else if (EmailRecordFilter.isManageCardEventRecord(record))
        {
            emailRecord = ManageCardEvent.getEmailRecord(record);
        }

        return emailRecord;
    }

    private static void doSendRequest(EmailRecord emailRecord) throws Exception
    {
        SMPCardEvent smpCardEvent = emailRecord.getSMPCardEvent();

        CardEventRecord cardEventRecord = CardEventRecord.getCardEventRecord(emailRecord);

        List<SMPEmailCardData> successCards = cardEventRecord.getSuccessCards();
        List<SMPEmailCardData> failedCards = cardEventRecord.getFailedCards();

        if (smpCardEvent == SMPCardEvent.PROVISION_CARD)
        {
            if (EMAIL_PROVISION && emailRecord.isFirstProvisionEvent())
            {
                FirstTimeProvisionRequest request = new FirstTimeProvisionRequest(
                        EmailRecordFormat.getValidValue(emailRecord.getCardHolderName()),
                        EmailRecordFormat.getValidValue(emailRecord.getConversationId()),
                        EmailRecordFormat.getValidValue(emailRecord.getCardHolderEmail()),
                        EmailRecordFormat.getValidValue(EmailRecordFormat.getLocale(emailRecord, EMAIL_LOCALE)),
                        EmailRecordFormat.getValidValue(emailRecord.getDeviceName()),
                        EmailRecordFormat.getValidValue(emailRecord.getDeviceType()),
                        EmailRecordFormat.getValidValue(emailRecord.getDsid()));

                new SMPFirstTimeProvisionMailHandler(request).sendEmail();
            }
        }
        else if (smpCardEvent == SMPCardEvent.SUSPEND_CARD)
        {
            if (EMAIL_SUSPEND)
            {
                if (cardEventRecord.isSuccessful())
                {
                    SuspendEmailRequest request = new SuspendEmailRequest(
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceName()),
                            successCards,
                            emailRecord.getCalendar(),
                            EmailRecordFormat.getValidValue(emailRecord.getCardHolderName()),
                            EmailRecordFormat.getValidValue(emailRecord.getConversationId()),
                            EmailRecordFormat.getValidValue(emailRecord.getCardHolderEmail()),
                            EmailRecordFormat.getValidValue(emailRecord.getDsid()),
                            EmailRecordFormat.getValidValue(EmailRecordFormat.getLocale(emailRecord, EMAIL_LOCALE)),
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceType()),
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceImageUrl()));

                    new SMPSuccessSuspendMailHandler(request).sendEmail();
                }
                else if (cardEventRecord.hasPartialSuccess())
                {
                    PartialSuspendEmailRequest request = new PartialSuspendEmailRequest(
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceName()),
                            successCards,
                            emailRecord.getCalendar(),
                            EmailRecordFormat.getValidValue(emailRecord.getCardHolderName()),
                            EmailRecordFormat.getValidValue(emailRecord.getConversationId()),
                            EmailRecordFormat.getValidValue(emailRecord.getCardHolderEmail()),
                            EmailRecordFormat.getValidValue(EmailRecordFormat.getLocale(emailRecord, EMAIL_LOCALE)),
                            EmailRecordFormat.getValidValue(emailRecord.getDsid()),
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceType()),
                            failedCards,
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceImageUrl()));

                    new SMPPartialSuspendMailHandler(request).sendEmail();
                }
                else if (cardEventRecord.isFailed())
                {
                    SuspendEmailRequest request = new SuspendEmailRequest(
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceName()),
                            failedCards,
                            emailRecord.getCalendar(),
                            EmailRecordFormat.getValidValue(emailRecord.getCardHolderName()),
                            EmailRecordFormat.getValidValue(emailRecord.getConversationId()),
                            EmailRecordFormat.getValidValue(emailRecord.getCardHolderEmail()),
                            EmailRecordFormat.getValidValue(emailRecord.getDsid()),
                            EmailRecordFormat.getValidValue(EmailRecordFormat.getLocale(emailRecord, EMAIL_LOCALE)),
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceType()),
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceImageUrl()));

                    new SMPFailSuspendMailHandler(request).sendEmail();
                }
            }
        }
        else if (smpCardEvent == SMPCardEvent.UNLINK_CARD)
        {
            if (EMAIL_UNLINK)
            {
                if (cardEventRecord.isSuccessful())
                {
                    RemoveEmailRequest request = new RemoveEmailRequest(
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceName()),
                            successCards,
                            emailRecord.getCalendar(),
                            EmailRecordFormat.getValidValue(emailRecord.getCardHolderName()),
                            EmailRecordFormat.getValidValue(emailRecord.getConversationId()),
                            EmailRecordFormat.getValidValue(emailRecord.getCardHolderEmail()),
                            EmailRecordFormat.getValidValue(EmailRecordFormat.getLocale(emailRecord, EMAIL_LOCALE)),
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceType()),
                            EmailRecordFormat.getValidValue(emailRecord.getDsid()),
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceImageUrl()),
                            EmailRecordFormat.getValidValue(EmailRecordFormat.getFmipSource(emailRecord)));

                    new SMPSuccessRemoveMailHandler(request).sendEmail();
                }
                else if (cardEventRecord.hasPartialSuccess())
                {
                    PartialRemoveEmailRequest request = new PartialRemoveEmailRequest(
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceName()),
                            successCards,
                            emailRecord.getCalendar(),
                            EmailRecordFormat.getValidValue(emailRecord.getCardHolderName()),
                            EmailRecordFormat.getValidValue(emailRecord.getConversationId()),
                            EmailRecordFormat.getValidValue(emailRecord.getCardHolderEmail()),
                            EmailRecordFormat.getValidValue(EmailRecordFormat.getLocale(emailRecord, EMAIL_LOCALE)),
                            EmailRecordFormat.getValidValue(emailRecord.getDsid()),
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceType()),
                            failedCards,
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceImageUrl()),
                            EmailRecordFormat.getValidValue(EmailRecordFormat.getFmipSource(emailRecord)));

                    new SMPPartialRemoveMailHandler(request).sendEmail();
                }
                else if (cardEventRecord.isFailed())
                {
                    RemoveEmailRequest request = new RemoveEmailRequest(
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceName()),
                            failedCards,
                            emailRecord.getCalendar(),
                            EmailRecordFormat.getValidValue(emailRecord.getCardHolderName()),
                            EmailRecordFormat.getValidValue(emailRecord.getConversationId()),
                            EmailRecordFormat.getValidValue(emailRecord.getCardHolderEmail()),
                            EmailRecordFormat.getValidValue(EmailRecordFormat.getLocale(emailRecord, EMAIL_LOCALE)),
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceType()),
                            EmailRecordFormat.getValidValue(emailRecord.getDsid()),
                            EmailRecordFormat.getValidValue(emailRecord.getDeviceImageUrl()),
                            EmailRecordFormat.getValidValue(EmailRecordFormat.getFmipSource(emailRecord)));

                    new SMPFailRemoveMailHandler(request).sendEmail();
                }
            }
        }
    }
}