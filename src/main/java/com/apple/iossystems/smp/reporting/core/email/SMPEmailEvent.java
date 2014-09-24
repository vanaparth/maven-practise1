package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.*;
import com.google.gson.GsonBuilder;

import java.util.*;

/**
 * @author Toch
 */
public class SMPEmailEvent
{
    private static final String CARD_EVENTS_KEY = "CardEvents";

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
        Map<String, GroupRecord> map = new HashMap<String, GroupRecord>();

        for (EventRecord record : records.getList())
        {
            String groupKey = getGroupKey(record);

            if (groupKey != null)
            {
                GroupRecord groupRecord = map.get(groupKey);

                if (groupRecord == null)
                {
                    groupRecord = new GroupRecord(record);

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

            eventRecord.setAttributeValue(CARD_EVENTS_KEY, new GsonBuilder().create().toJson(groupRecord.cardEvents, List.class));

            outputRecords.add(eventRecord);
        }

        return outputRecords;
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
            cardEvents = Arrays.asList(new GsonBuilder().create().fromJson(json, CardEvent[].class));
        }
        else
        {
            cardEvents = new ArrayList<CardEvent>();
        }

        return cardEvents;
    }

    public static EventRecords getEventRecords(EventRecords records)
    {
        EventRecords outputRecords = EventRecords.getInstance();

        for (EventRecord record : records.getList())
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