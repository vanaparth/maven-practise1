package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.persistence.entity.PassbookPass;
import com.apple.iossystems.smp.persistence.entity.SecureElement;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.SMPCardEvent;

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
        SMPCardEvent smpCardEvent = SMPCardEvent.getSMPCardEvent(record.getAttributeValue(EventAttribute.CARD_EVENT.key()));
        String conversationId = record.getAttributeValue(EventAttribute.CONVERSATION_ID.key());
        String timestamp = record.getAttributeValue(EventAttribute.TIMESTAMP.key());

        AthenaCardEvent athenaCardEvent = AthenaCardEvent.fromJson(record.getAttributeValue(EventAttribute.ATHENA_CARD_EVENT.key()));
        ManageCardEvent manageCardEvent = ManageCardEvent.fromJson(record.getAttributeValue(EventAttribute.MANAGE_CARD_EVENT.key()));

        PassbookPass passbookPass = SMPEventDataServiceProxy.getPassbookPass(manageCardEvent);
        SecureElement secureElement = SMPEventDataServiceProxy.getSecureElement(manageCardEvent);

        String cardHolderName = SMPEventDataServiceProxy.getCardHolderName(athenaCardEvent, manageCardEvent, passbookPass);
        String cardHolderEmail = SMPEventDataServiceProxy.getCardHolderEmail(athenaCardEvent, manageCardEvent);

        String deviceName = SMPEventDataServiceProxy.getDeviceName(athenaCardEvent, manageCardEvent, passbookPass, secureElement);
        String deviceType = SMPEventDataServiceProxy.getDeviceType(athenaCardEvent, secureElement);
        String deviceImageUrl = SMPEventDataServiceProxy.getDeviceImageUrl(manageCardEvent);

        String dsid = SMPEventDataServiceProxy.getDsid(athenaCardEvent, manageCardEvent, passbookPass);
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
                cards(SMPEventDataServiceProxy.getCards(athenaCardEvent, manageCardEvent)).
                manageCardEvent(manageCardEvent).
                build();
    }
}