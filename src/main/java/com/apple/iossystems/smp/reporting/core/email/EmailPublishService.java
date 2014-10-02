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

            if ((emailRecord != null) && isValidEmailRecord(emailRecord))
            {
                sendRequest(emailRecord);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
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

    private static boolean isValidEmailRecord(EmailRecord emailRecord)
    {
        SMPCardEvent smpCardEvent = emailRecord.getSMPCardEvent();

        boolean doFilter = ((smpCardEvent == SMPCardEvent.SUSPEND_CARD) || (smpCardEvent == SMPCardEvent.UNLINK_CARD) || (smpCardEvent == SMPCardEvent.RESUME_CARD));

        EmailPublishServiceLogger.logCheck(emailRecord, (!doFilter || hasValidCardEventSource(emailRecord)));

        return (!doFilter || hasValidCardEventSource(emailRecord));
    }

    private static boolean hasValidCardEventSource(EmailRecord emailRecord)
    {
        ManageCardEvent manageCardEvent = emailRecord.getManageCardEvent();

        if (manageCardEvent != null)
        {
            return (manageCardEvent.getCardEventSource() == CardEventSource.FMIP);
        }

        return true;
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
                    FirstTimeProvisionRequest request = new FirstTimeProvisionRequest(emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), getLocale(emailRecord), emailRecord.getDeviceName(), emailRecord.getDeviceType(), emailRecord.getDsid());

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
                    SuspendEmailRequest request = new SuspendEmailRequest(emailRecord.getDeviceName(), successCards, emailRecord.getDate(), emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), emailRecord.getDsid(), getLocale(emailRecord), emailRecord.getDeviceType(), emailRecord.getDeviceImageUrl());

                    new SMPSuccessSuspendMailHandler(request).sendEmail();
                }
                else if (cardEventRecord.hasPartialSuccess())
                {
                    PartialSuspendEmailRequest request = new PartialSuspendEmailRequest(emailRecord.getDeviceName(), successCards, emailRecord.getDate(), emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), getLocale(emailRecord), emailRecord.getDsid(), emailRecord.getDeviceType(), failedCards, emailRecord.getDeviceImageUrl());

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
                    RemoveEmailRequest request = new RemoveEmailRequest(emailRecord.getDeviceName(), successCards, emailRecord.getDate(), emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), getLocale(emailRecord), emailRecord.getDeviceType(), emailRecord.getDsid(), emailRecord.getDeviceImageUrl(), null);

                    new SMPSuccessRemoveMailHandler(request).sendEmail();
                }
                else if (cardEventRecord.hasPartialSuccess())
                {
                    PartialRemoveEmailRequest request = new PartialRemoveEmailRequest(emailRecord.getDeviceName(), successCards, emailRecord.getDate(), emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), getLocale(emailRecord), emailRecord.getDsid(), emailRecord.getDeviceType(), failedCards, emailRecord.getDeviceImageUrl(), null);

                    new SMPPartialRemoveMailHandler(request).sendEmail();
                }
            }
        }

        EmailPublishServiceLogger.logTests(emailRecord, cardEventRecord);
        EmailPublishServiceLogger.log(emailRecord);
    }

    private static String getLocale(EmailRecord record)
    {
        return (EMAIL_LOCALE ? "en_US" : record.getLocale());
    }
}