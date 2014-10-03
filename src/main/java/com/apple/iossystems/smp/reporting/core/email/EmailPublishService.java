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
            EmailRecord emailRecord = EmailContentService.getEmailRecord(record);

            if (emailRecord != null)
            {
                if (EmailRecordFilter.isValidEmailRecord(emailRecord))
                {
                    doSendRequest(emailRecord);
                }

                log(emailRecord);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private static void doSendRequest(EmailRecord emailRecord)
    {
        try
        {
            sendRequest(emailRecord);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private static void log(EmailRecord emailRecord)
    {
        EmailPublishServiceLogger.log(emailRecord);
        EmailPublishServiceLogger.logTests(emailRecord, getCardEventRecord(emailRecord));
    }

    private static CardEventRecord getCardEventRecord(EmailRecord emailRecord)
    {
        if (emailRecord.getCards() != null)
        {
            return CardEventRecord.getCardEventRecord(emailRecord.getCards());
        }
        else
        {
            return CardEventRecord.getInstance();
        }
    }

    private static void sendRequest(EmailRecord emailRecord) throws Exception
    {
        SMPCardEvent smpCardEvent = emailRecord.getSMPCardEvent();

        CardEventRecord cardEventRecord = getCardEventRecord(emailRecord);

        List<SMPEmailCardData> successCards = cardEventRecord.getSuccessCards();
        List<SMPEmailCardData> failedCards = cardEventRecord.getFailedCards();

        if (smpCardEvent == SMPCardEvent.PROVISION_CARD)
        {
            if (EMAIL_PROVISION)
            {
                if (emailRecord.isFirstProvisionEvent())
                {
                    FirstTimeProvisionRequest request = new FirstTimeProvisionRequest(emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), EmailRecordFormat.getLocale(emailRecord, EMAIL_LOCALE), emailRecord.getDeviceName(), EmailRecordFormat.getDeviceType(emailRecord), emailRecord.getDsid());

                    new SMPFirstTimeProvisionMailHandler(request).sendEmail();
                }
            }
        }
        else if (smpCardEvent == SMPCardEvent.SUSPEND_CARD)
        {
            if (EMAIL_SUSPEND)
            {
                if (cardEventRecord.isSuccessful())
                {
                    SuspendEmailRequest request = new SuspendEmailRequest(emailRecord.getDeviceName(), successCards, emailRecord.getDate(), emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), emailRecord.getDsid(), EmailRecordFormat.getLocale(emailRecord, EMAIL_LOCALE), EmailRecordFormat.getDeviceType(emailRecord), emailRecord.getDeviceImageUrl());

                    new SMPSuccessSuspendMailHandler(request).sendEmail();
                }
                else if (cardEventRecord.hasPartialSuccess())
                {
                    PartialSuspendEmailRequest request = new PartialSuspendEmailRequest(emailRecord.getDeviceName(), successCards, emailRecord.getDate(), emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), EmailRecordFormat.getLocale(emailRecord, EMAIL_LOCALE), emailRecord.getDsid(), EmailRecordFormat.getDeviceType(emailRecord), failedCards, emailRecord.getDeviceImageUrl());

                    new SMPPartialSuspendMailHandler(request).sendEmail();
                }
            }
        }
        else if (smpCardEvent == SMPCardEvent.UNLINK_CARD)
        {
            if (EMAIL_UNLINK)
            {
                if (cardEventRecord.isSuccessful())
                {
                    RemoveEmailRequest request = new RemoveEmailRequest(emailRecord.getDeviceName(), successCards, emailRecord.getDate(), emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), EmailRecordFormat.getLocale(emailRecord, EMAIL_LOCALE), EmailRecordFormat.getDeviceType(emailRecord), emailRecord.getDsid(), emailRecord.getDeviceImageUrl(), EmailRecordFormat.getFmipSource(emailRecord));

                    new SMPSuccessRemoveMailHandler(request).sendEmail();
                }
                else if (cardEventRecord.hasPartialSuccess())
                {
                    PartialRemoveEmailRequest request = new PartialRemoveEmailRequest(emailRecord.getDeviceName(), successCards, emailRecord.getDate(), emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), EmailRecordFormat.getLocale(emailRecord, EMAIL_LOCALE), emailRecord.getDsid(), EmailRecordFormat.getDeviceType(emailRecord), failedCards, emailRecord.getDeviceImageUrl(), EmailRecordFormat.getFmipSource(emailRecord));

                    new SMPPartialRemoveMailHandler(request).sendEmail();
                }
            }
        }
    }
}