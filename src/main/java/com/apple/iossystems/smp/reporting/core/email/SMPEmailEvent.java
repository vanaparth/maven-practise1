package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.persistence.entity.PassbookPass;
import com.apple.iossystems.smp.reporting.core.event.*;

import java.util.List;

/**
 * @author Toch
 */
public class SMPEmailEvent
{
    private SMPEmailEvent()
    {
    }

    public static EventRecords getEventRecords(EventRecords records)
    {
        List<EventRecord> list = records.getList();

        EventRecords outputRecords = EventRecords.getInstance();

        for (EventRecord record : list)
        {
            if (isEmailRecord(record))
            {
                outputRecords.add(createEmailEventRecord(record));
            }
        }

        return outputRecords;
    }

    private static boolean isEmailRecord(EventRecord record)
    {
        String cardEventCode = record.getAttributeValue(EventAttribute.CARD_EVENT.key());

        return ((EventType.getEventType(record) == EventType.REPORTS) &&
                (cardEventCode.equals(SMPCardEvent.PROVISION_CARD.getCode()) || cardEventCode.equals(SMPCardEvent.SUSPEND_CARD.getCode()) || cardEventCode.equals(SMPCardEvent.UNLINK_CARD.getCode())));
    }

    private static void copyAttributeValue(EventRecord source, EventRecord destination, EventAttribute attribute)
    {
        String key = attribute.key();

        destination.setAttributeValue(key, source.getAttributeValue(key));
    }

    private static EventRecord createEmailEventRecord(EventRecord inputRecord)
    {
        EventRecord outputRecord = EventRecord.getInstance();

        String dpanId = inputRecord.getAttributeValue(EventAttribute.DPAN_ID.key());

        PassbookPass passbookPass = EmailContentService.getPassByDpanId(dpanId);

        copyAttributeValue(inputRecord, outputRecord, EventAttribute.CARD_EVENT);
        copyAttributeValue(inputRecord, outputRecord, EventAttribute.CONVERSATION_ID);
        copyAttributeValue(inputRecord, outputRecord, EventAttribute.TIMESTAMP);

        outputRecord.setAttributeValue(EventAttribute.EVENT_TYPE.key(), EventType.EMAIL.getKey());
        outputRecord.setAttributeValue(EventAttribute.CARD_HOLDER_NAME.key(), passbookPass.getCardHolderName());
        outputRecord.setAttributeValue(EventAttribute.CARD_HOLDER_EMAIL.key(), EmailContentService.getCardHolderEmail());
        outputRecord.setAttributeValue(EventAttribute.DEVICE_NAME.key(), EmailContentService.getDeviceName(passbookPass.getUserPrincipal(), EmailContentService.getDeviceSerialNumber(dpanId)));

        EmailContentService.setValuesFromPassbookPass(outputRecord, passbookPass);

        return outputRecord;
    }
}