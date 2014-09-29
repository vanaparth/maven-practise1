package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.persistence.entity.PassbookPass;
import com.apple.iossystems.smp.persistence.entity.SecureElement;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.SMPCardEvent;

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
            emailRecord = createEmailRecord(record, cardEvents, SMPEventDataServiceProxy.getDpanId(cardEvents));
        }

        return emailRecord;
    }

    private static EmailRecord createEmailRecord(EventRecord record, List<CardEvent> cardEvents, String dpanId)
    {
        SMPCardEvent smpCardEvent = SMPCardEvent.getSMPCardEvent(record.getAttributeValue(EventAttribute.CARD_EVENT.key()));
        String conversationId = record.getAttributeValue(EventAttribute.CONVERSATION_ID.key());
        String timestamp = record.getAttributeValue(EventAttribute.TIMESTAMP.key());

        AthenaCardEvent athenaCardEvent = AthenaCardEvent.fromJson(record.getAttributeValue(EventAttribute.ATHENA_CARD_EVENT.key()));
        ManageCardEvent manageCardEvent = ManageCardEvent.fromJson(record.getAttributeValue(EventAttribute.MANAGE_CARD_EVENT.key()));

        PassbookPass passbookPass = SMPEventDataServiceProxy.getPassbookPass(dpanId);
        SecureElement secureElement = SMPEventDataServiceProxy.getSecureElement(dpanId);

        String cardHolderName = SMPEventDataServiceProxy.getCardHolderName(athenaCardEvent, manageCardEvent, passbookPass);
        String cardHolderEmail = SMPEventDataServiceProxy.getCardHolderEmail(athenaCardEvent, manageCardEvent);

        String deviceName = SMPEventDataServiceProxy.getDeviceName(athenaCardEvent, manageCardEvent, passbookPass, secureElement);
        String deviceType = SMPEventDataServiceProxy.getDeviceType(athenaCardEvent, secureElement);
        String deviceImageUrl = SMPEventDataServiceProxy.getDeviceImageUrl(manageCardEvent);

        String dsid = SMPEventDataServiceProxy.getDsid(athenaCardEvent, passbookPass);
        String locale = SMPEventDataServiceProxy.getLocale(athenaCardEvent, manageCardEvent);

        boolean isFirstProvision = (smpCardEvent == SMPCardEvent.PROVISION_CARD) && SMPEventDataServiceProxy.isFirstProvision(secureElement);

        return EmailRecord.getBuilder().smpCardEvent(smpCardEvent).
                conversationId(conversationId).
                timestamp(timestamp).
                cardHolderName(cardHolderName).
                cardHolderEmail(cardHolderEmail).
                deviceName(deviceName).
                deviceType(deviceType).
                deviceImageUrl(deviceImageUrl).
                dsid(dsid).
                locale(locale).
                firstProvisionEvent(isFirstProvision).
                cards(SMPEventDataServiceProxy.getCards(cardEvents)).
                build();
    }
}