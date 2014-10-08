package com.apple.iossystems.smp.reporting.core.email;

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

        ProvisionCardEvent provisionCardEvent = ProvisionCardEvent.fromJson(record.getAttributeValue(EventAttribute.PROVISION_CARD_EVENT.key()));
        ManageCardEvent manageCardEvent = ManageCardEvent.fromJson(record.getAttributeValue(EventAttribute.MANAGE_CARD_EVENT.key()));

        SecureElement secureElement = SMPEventDataServiceProxy.getSecureElement(manageCardEvent);

        String cardHolderName = SMPEventDataServiceProxy.getCardHolderName(provisionCardEvent, manageCardEvent);
        String cardHolderEmail = SMPEventDataServiceProxy.getCardHolderEmail(provisionCardEvent, manageCardEvent);

        String deviceName = SMPEventDataServiceProxy.getDeviceName(provisionCardEvent, manageCardEvent);
        String deviceType = SMPEventDataServiceProxy.getDeviceType(provisionCardEvent, secureElement);
        String deviceImageUrl = SMPEventDataServiceProxy.getDeviceImageUrl(manageCardEvent);

        String dsid = SMPEventDataServiceProxy.getDsid(provisionCardEvent, manageCardEvent);
        String locale = SMPEventDataServiceProxy.getLocale(provisionCardEvent, manageCardEvent);

        boolean isFirstProvision = (smpCardEvent == SMPCardEvent.PROVISION_CARD) && SMPEventDataServiceProxy.isFirstProvision(provisionCardEvent);

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
                cards(SMPEventDataServiceProxy.getCards(provisionCardEvent, manageCardEvent)).
                manageCardEvent(manageCardEvent).
                build();
    }
}