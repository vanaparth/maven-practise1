package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.device.AbstractPass;
import com.apple.iossystems.smp.persistence.entity.PassbookPass;
import com.apple.iossystems.smp.persistence.entity.SecureElement;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.SMPCardEvent;
import com.apple.iossystems.smp.reporting.core.util.ValidValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
class EmailContentService
{
    private EmailContentService()
    {
    }

    public static EmailRecord getEmailRecord(EventRecord record)
    {
        EmailRecord emailRecord = null;

        List<CardEvent> cardEvents = SMPEmailEvent.getCardEvents(record);

        if ((cardEvents != null) && (!cardEvents.isEmpty()))
        {
            emailRecord = createEmailRecord(record, cardEvents, cardEvents.get(0).getDpanId());
        }

        return emailRecord;
    }

    private static List<Card> getCards(List<CardEvent> cardEvents)
    {
        List<Card> cards = new ArrayList<Card>();

        for (CardEvent cardEvent : cardEvents)
        {
            cards.add(getCard(cardEvent));
        }

        return cards;
    }

    private static Card getCard(CardEvent cardEvent)
    {
        PassbookPass passbookPass = SMPEventDataService.getPassByDpanId(cardEvent.getDpanId());

        String cardDisplayNumber = SMPEventDataService.getValueFromPassbookPass(passbookPass, AbstractPass.PAYMENT_PASS_FPAN_SUFFIX_KEY);
        String cardDescription = SMPEventDataService.getValueFromPassbookPass(passbookPass, AbstractPass.PAYMENT_PASS_LONG_DESC_KEY);

        if (cardDescription == null)
        {
            cardDescription = SMPEventDataService.getValueFromPassbookPass(passbookPass, AbstractPass.PAYMENT_PASS_SHORT_DESC_KEY);
        }

        return Card.getInstance(cardDescription, cardDisplayNumber, cardEvent.getStatus());
    }

    private static EmailRecord createEmailRecord(EventRecord record, List<CardEvent> cardEvents, String dpanId)
    {
        SMPCardEvent smpCardEvent = SMPCardEvent.getSMPCardEvent(record.getAttributeValue(EventAttribute.CARD_EVENT.key()));
        String conversationId = record.getAttributeValue(EventAttribute.CONVERSATION_ID.key());
        String timestamp = record.getAttributeValue(EventAttribute.TIMESTAMP.key());

        AthenaCardEvent athenaCardEvent = AthenaCardEvent.fromJson(record.getAttributeValue(EventAttribute.ATHENA_CARD_EVENT.key()));
        ManageCardEvent manageCardEvent = ManageCardEvent.fromJson(record.getAttributeValue(EventAttribute.MANAGE_CARD_EVENT.key()));
        PassbookPass passbookPass = SMPEventDataService.getPassByDpanId(dpanId);
        SecureElement secureElement = SMPEventDataService.getSecureElement(dpanId);

        String cardHolderName = selectValue(new String[]{athenaCardEvent.getCardHolderName(), manageCardEvent.getCardHolderName(), passbookPass.getCardHolderName()});
        String cardHolderEmail = ValidValue.getStringValueWithDefault(athenaCardEvent.getCardHolderEmail(), manageCardEvent.getCardHolderEmail());

        String deviceName = selectValue(new String[]{athenaCardEvent.getDeviceName(), manageCardEvent.getDeviceName(), SMPEventDataService.getDeviceName(passbookPass, secureElement)});
        String deviceType = ValidValue.getStringValueWithDefault(athenaCardEvent.getDeviceType(), SMPEventDataService.getDeviceType(secureElement));
        String locale = ValidValue.getStringValueWithDefault(athenaCardEvent.getDeviceLanguage(), manageCardEvent.getLocale());
        String dsid = ValidValue.getStringValueWithDefault(athenaCardEvent.getDsid(), passbookPass.getUserPrincipal());

        boolean isFirstProvision = (smpCardEvent == SMPCardEvent.PROVISION_CARD) && (secureElement.getProvisioningCount() == 1);

        return EmailRecord.getBuilder().smpCardEvent(smpCardEvent).
                conversationId(conversationId).
                timestamp(timestamp).
                cardHolderName(cardHolderName).
                cardHolderEmail(cardHolderEmail).
                deviceName(deviceName).
                deviceType(deviceType).
                dsid(dsid).
                locale(locale).
                firstProvisionEvent(isFirstProvision).
                cards(getCards(cardEvents)).
                build();
    }

    private static String selectValue(String[] values)
    {
        for (String value : values)
        {
            if (value != null)
            {
                return value;
            }
        }

        return null;
    }
}