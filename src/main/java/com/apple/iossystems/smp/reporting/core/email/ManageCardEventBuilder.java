package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.Actor;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Toch
 */
public class ManageCardEventBuilder
{
    private static final String FIRST_NAME = "customerFirstName";
    private static final String LAST_NAME = "customerLastName";
    private static final String CARD_HOLDER_EMAIL = "customerEmail";
    private static final String LOCALE = "customerLocale";
    private static final String DEVICE_NAME = "deviceName";
    private static final String DEVICE_IMAGE_URL = "deviceImageURL";

    private String dsid;
    private ManageCardAPI manageCardAPI;
    private Actor actor;
    private Map<String, String> cardData;
    private List<CardEvent> cardEvents;

    private ManageCardEventBuilder()
    {
    }

    public static ManageCardEventBuilder getInstance()
    {
        return new ManageCardEventBuilder();
    }

    public ManageCardEventBuilder dsid(String value)
    {
        dsid = value;
        return this;
    }

    public ManageCardEventBuilder manageCardAPI(ManageCardAPI value)
    {
        manageCardAPI = value;
        return this;
    }

    public ManageCardEventBuilder actor(Actor value)
    {
        actor = value;
        return this;
    }

    public ManageCardEventBuilder cardData(Map<String, String> value)
    {
        cardData = value;
        return this;
    }

    public ManageCardEventBuilder cardEvents(EventRecords records)
    {
        cardEvents = getCardEvents(records);
        return this;
    }

    public ManageCardEvent build()
    {
        if (cardData == null)
        {
            cardData = new HashMap<String, String>();
        }

        if (cardEvents == null)
        {
            cardEvents = new ArrayList<CardEvent>();
        }

        String firstName = cardData.get(FIRST_NAME);
        String lastName = cardData.get(LAST_NAME);
        String cardHolderEmail = cardData.get(CARD_HOLDER_EMAIL);
        String locale = cardData.get(LOCALE);
        String deviceName = cardData.get(DEVICE_NAME);
        String deviceImageUrl = cardData.get(DEVICE_IMAGE_URL);

        return ManageCardEvent.getBuilder().cardHolderName(getCardHolderName(firstName, lastName)).
                cardHolderEmail(cardHolderEmail).
                dsid(dsid).
                locale(locale).
                deviceName(deviceName).
                deviceImageUrl(deviceImageUrl).
                cardEventSource(CardEventSource.getCardEventSource(actor)).
                manageCardAPI(manageCardAPI).
                cardEvents(cardEvents).build();
    }

    private List<CardEvent> getCardEvents(EventRecords records)
    {
        List<CardEvent> cardEvents = new ArrayList<CardEvent>();

        if (records != null)
        {
            for (EventRecord record : records.getList())
            {
                String dpanId = record.getAttributeValue(EventAttribute.DPAN_ID.key());
                boolean eventStatus = CardEventStatus.hasValidCardStatus(record);

                cardEvents.add(CardEvent.getInstance(dpanId, eventStatus));
            }
        }

        return cardEvents;
    }

    private String getCardHolderName(String firstName, String lastName)
    {
        String cardHolderName = null;

        if (firstName != null)
        {
            cardHolderName = firstName;
        }

        if (lastName != null)
        {
            if (cardHolderName != null)
            {
                cardHolderName += " " + lastName;
            }
            else
            {
                cardHolderName = lastName;
            }
        }

        return cardHolderName;
    }
}