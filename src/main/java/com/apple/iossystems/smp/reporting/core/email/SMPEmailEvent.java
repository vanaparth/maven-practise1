package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.*;

import java.util.*;

/**
 * @author Toch
 */
public class SMPEmailEvent
{
    private static final String CARD_EVENTS_KEY = "CardEvents";

    private static final EventAttribute[] GROUP_EVENT_ATTRIBUTES = {EventAttribute.EVENT_TYPE, EventAttribute.CARD_EVENT, EventAttribute.TIMESTAMP, EventAttribute.CONVERSATION_ID};

    private SMPEmailEvent()
    {
    }

    static boolean isEmailRecord(EventRecord record)
    {
        SMPCardEvent smpCardEvent = SMPCardEvent.getSMPCardEvent(record.getAttributeValue(EventAttribute.CARD_EVENT.key()));

        return ((smpCardEvent == SMPCardEvent.PROVISION_CARD) ||
                (smpCardEvent == SMPCardEvent.SUSPEND_CARD) ||
                (smpCardEvent == SMPCardEvent.UNLINK_CARD) ||
                (smpCardEvent == SMPCardEvent.RESUME_CARD));
    }

    private static String getGroupKey(EventRecord record)
    {
        return record.getAttributeValue(EventAttribute.CONVERSATION_ID.key());
    }

    private static EventRecords groupEventRecords(EventRecords records)
    {
        List<EventRecord> list = records.getList();

        Map<String, GroupRecord> map = new HashMap<String, GroupRecord>();

        for (EventRecord record : list)
        {
            String groupKey = getGroupKey(record);

            if (groupKey != null)
            {
                GroupRecord groupRecord = map.get(groupKey);

                if (groupRecord == null)
                {
                    groupRecord = new GroupRecord(createGroupEventRecord(record));

                    map.put(groupKey, groupRecord);
                }

                String dpanId = record.getAttributeValue(EventAttribute.DPAN_ID.key());
                boolean status = CardEventStatus.hasValidCardStatus(record);

                groupRecord.addCardEvent(CardEvent.getInstance(dpanId, status));
            }
        }

        EventRecords outputRecords = EventRecords.getInstance();

        for (GroupRecord groupRecord : map.values())
        {
            EventRecord eventRecord = groupRecord.eventRecord;

            eventRecord.setAttributeValue(CARD_EVENTS_KEY, CardEvent.getGson().toJson(groupRecord.cardEvents, List.class));

            outputRecords.add(eventRecord);
        }

        return outputRecords;
    }

    private static EventRecord createGroupEventRecord(EventRecord record)
    {
        EventRecord groupEventRecord = EventRecord.getInstance();

        copyEventAttributes(record, groupEventRecord, GROUP_EVENT_ATTRIBUTES);

        return groupEventRecord;
    }

    private static void copyEventAttributes(EventRecord srcRecord, EventRecord destRecord, EventAttribute[] eventAttributes)
    {
        for (EventAttribute eventAttribute : eventAttributes)
        {
            copyEventAttribute(srcRecord, destRecord, eventAttribute);
        }
    }

    private static void copyEventAttribute(EventRecord srcRecord, EventRecord destRecord, EventAttribute eventAttribute)
    {
        String key = eventAttribute.key();

        destRecord.setAttributeValue(key, srcRecord.getAttributeValue(key));
    }

    private static class GroupRecord
    {
        private EventRecord eventRecord;
        private List<CardEvent> cardEvents = new ArrayList<CardEvent>();

        private GroupRecord(EventRecord eventRecord)
        {
            this.eventRecord = eventRecord;
        }

        private void addCardEvent(CardEvent cardEvent)
        {
            cardEvents.add(cardEvent);
        }
    }

    static List<CardEvent> getCardEvents(EventRecord record)
    {
        List<CardEvent> cardEvents;

        String json = record.getAttributeValue(CARD_EVENTS_KEY);

        if (json != null)
        {
            cardEvents = Arrays.asList(CardEvent.getGson().fromJson(json, CardEvent[].class));
        }
        else
        {
            cardEvents = new ArrayList<CardEvent>();
        }

        return cardEvents;
    }

    public static EventRecords getEventRecords(EventRecords records)
    {
        List<EventRecord> list = records.getList();

        EventRecords outputRecords = EventRecords.getInstance();

        for (EventRecord record : list)
        {
            if (isEmailRecord(record))
            {
                record.setAttributeValue(EventAttribute.EVENT_TYPE.key(), EventType.EMAIL.getKey());
                outputRecords.add(record);
            }
        }

        return groupEventRecords(outputRecords);
    }
}