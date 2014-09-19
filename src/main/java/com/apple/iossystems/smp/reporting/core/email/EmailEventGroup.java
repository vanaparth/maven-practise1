package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.util.ValidValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Toch
 */
class EmailEventGroup
{
    private static final String CARD_GROUP_KEY = "CardGroup";
    private static final String CARD_SEPARATOR = "|||";
    private static final String CARD_ITEM_SEPARATOR = "///";

    private EmailEventGroup()
    {
    }

    private static String getEventGroupKey(EventRecord record)
    {
        return record.getAttributeValue(EventAttribute.CONVERSATION_ID.key());
    }

    static EventRecords groupEventRecords(EventRecords records)
    {
        List<EventRecord> list = records.getList();

        EventRecords groups = EventRecords.getInstance();

        Map<String, EventRecord> map = new HashMap<String, EventRecord>();

        for (EventRecord record : list)
        {
            String eventGroupKey = getEventGroupKey(record);

            EventRecord groupRecord = map.get(eventGroupKey);

            if (groupRecord == null)
            {
                groupRecord = record;

                map.put(eventGroupKey, groupRecord);

                groups.add(groupRecord);
            }

            String cardGroup = groupRecord.getAttributeValue(CARD_GROUP_KEY);

            if (cardGroup == null)
            {
                cardGroup = "";
            }
            else
            {
                cardGroup += CARD_SEPARATOR;
            }

            String cardDescription = ValidValue.getStringValueWithDefault(record.getAttributeValue(EventAttribute.CARD_DESCRIPTION.key()), "");
            String cardDisplayNumber = ValidValue.getStringValueWithDefault(record.getAttributeValue(EventAttribute.CARD_DISPLAY_NUMBER.key()), "");
            String cardEventStatus = ValidValue.getStringValueWithDefault(String.valueOf(CardEvent.hasValidCardStatus(record)), "");

            cardGroup += (cardDescription + CARD_ITEM_SEPARATOR + cardDisplayNumber + CARD_ITEM_SEPARATOR + cardEventStatus);

            groupRecord.setAttributeValue(CARD_GROUP_KEY, cardGroup);
        }

        return groups;
    }

    static List<Card> getCards(EventRecord record)
    {
        List<Card> cards = new ArrayList<Card>();

        String cardInfo = record.getAttributeValue(CARD_GROUP_KEY);

        if (cardInfo != null)
        {
            String[] cardTokens = cardInfo.split(CARD_SEPARATOR);

            if (cardTokens != null)
            {
                for (String cardToken : cardTokens)
                {
                    String[] cardItemTokens = cardToken.split(CARD_ITEM_SEPARATOR);

                    if ((cardItemTokens != null) && (cardItemTokens.length == 3))
                    {
                        cards.add(Card.getInstance(cardItemTokens[0], cardItemTokens[1], Boolean.valueOf(cardItemTokens[2])));
                    }
                }
            }
        }

        return cards;
    }
}