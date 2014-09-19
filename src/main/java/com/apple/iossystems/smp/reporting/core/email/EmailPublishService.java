package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.email.service.impl.ssp.domain.*;
import com.apple.iossystems.smp.email.service.impl.ssp.handler.*;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.SMPCardEvent;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
public class EmailPublishService
{
    private static final Logger LOGGER = Logger.getLogger(EmailPublishService.class);

    private EmailPublishService()
    {
    }

    public static EmailPublishService getInstance()
    {
        return new EmailPublishService();
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
            sendRequest(record);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private EmailRecord getEmailRecord(EventRecord record)
    {
        return EmailRecord.getBuilder().cardHolderName(record.getAttributeValue(EventAttribute.CARD_HOLDER_NAME.key())).
                cardHolderEmail(EventAttribute.CARD_HOLDER_EMAIL.key()).
                conversationId(EventAttribute.CONVERSATION_ID.key()).
                deviceName(EventAttribute.DEVICE_NAME.key()).
                deviceType(EventAttribute.DEVICE_TYPE.key()).
                dsid(EventAttribute.DSID.key()).
                locale(EventAttribute.LOCALE.key()).
                timestamp(EventAttribute.TIMESTAMP.key()).build();
    }

    private static class CardsEvent
    {
        private List<SMPEmailCardData> successCards = new ArrayList<SMPEmailCardData>();
        private List<SMPEmailCardData> failedCards = new ArrayList<SMPEmailCardData>();

        private CardsEvent()
        {
        }

        private List<SMPEmailCardData> getSuccessCards()
        {
            return successCards;
        }

        private List<SMPEmailCardData> getFailedCards()
        {
            return failedCards;
        }

        private void addSuccessCard(SMPEmailCardData card)
        {
            successCards.add(card);
        }

        private void addFailedCard(SMPEmailCardData card)
        {
            failedCards.add(card);
        }

        private boolean isSuccessful()
        {
            return (!successCards.isEmpty() && failedCards.isEmpty());
        }

        private boolean hasPartialSuccess()
        {
            return (!failedCards.isEmpty());
        }
    }

    private CardsEvent getCardsEvent(List<Card> cards)
    {
        CardsEvent cardsEvent = new CardsEvent();

        for (Card card : cards)
        {
            if (card.getStatus())
            {
                cardsEvent.addSuccessCard(new SMPEmailCardData(card.getDisplayNumber(), card.getDescription()));
            }
            else
            {
                cardsEvent.addFailedCard(new SMPEmailCardData(card.getDisplayNumber(), card.getDescription()));
            }
        }

        return cardsEvent;
    }

    private void sendRequest(EventRecord record) throws Exception
    {
        String cardEventCode = record.getAttributeValue(EventAttribute.CARD_EVENT.key());

        EmailRecord emailRecord = getEmailRecord(record);

        CardsEvent cardsEvent = getCardsEvent(EmailEventGroup.getCards(record));

        List<SMPEmailCardData> successCards = cardsEvent.getSuccessCards();
        List<SMPEmailCardData> failedCards = cardsEvent.getFailedCards();

        if (cardEventCode.equals(SMPCardEvent.PROVISION_CARD.getCode()))
        {
            boolean isFirstProvision = Boolean.valueOf(record.getAttributeValue(EventAttribute.FIRST_PROVISION.key()));

            if (isFirstProvision)
            {
                FirstTimeProvisionRequest request = new FirstTimeProvisionRequest(emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), emailRecord.getLocale(), emailRecord.getDeviceName(), emailRecord.getDeviceType(), emailRecord.getDsid());

                new SMPFirstTimeProvisionMailHandler(request).sendEmail();
            }
        }
        else if (cardEventCode.equals(SMPCardEvent.SUSPEND_CARD.getCode()))
        {
            if (cardsEvent.isSuccessful())
            {
                SuspendEmailRequest request = new SuspendEmailRequest(emailRecord.getDeviceName(), successCards, emailRecord.getDate(), emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), emailRecord.getDsid(), emailRecord.getLocale(), emailRecord.getDeviceType());

                new SMPSuccessSuspendMailHandler(request).sendEmail();
            }
            else if (cardsEvent.hasPartialSuccess())
            {
                PartialSuspendEmailRequest request = new PartialSuspendEmailRequest(emailRecord.getDeviceName(), successCards, emailRecord.getDate(), emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), emailRecord.getLocale(), emailRecord.getDsid(), emailRecord.getDeviceType(), failedCards);

                new SMPPartialSuspendMailHandler(request).sendEmail();
            }
        }
        else if (cardEventCode.equals(SMPCardEvent.UNLINK_CARD.getCode()))
        {
            if (cardsEvent.isSuccessful())
            {
                RemoveEmailRequest request = new RemoveEmailRequest(emailRecord.getDeviceName(), successCards, emailRecord.getDate(), emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), emailRecord.getLocale(), emailRecord.getDeviceType(), emailRecord.getDsid());

                new SMPSuccessRemoveMailHandler(request).sendEmail();
            }
            else if (cardsEvent.hasPartialSuccess())
            {
                PartialRemoveEmailRequest request = new PartialRemoveEmailRequest(emailRecord.getDeviceName(), successCards, emailRecord.getDate(), emailRecord.getCardHolderName(), emailRecord.getConversationId(), emailRecord.getCardHolderEmail(), emailRecord.getLocale(), emailRecord.getDsid(), emailRecord.getDeviceType(), failedCards);

                new SMPPartialRemoveMailHandler(request).sendEmail();
            }
        }
    }
}