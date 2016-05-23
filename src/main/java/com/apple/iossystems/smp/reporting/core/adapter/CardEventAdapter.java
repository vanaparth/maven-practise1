package com.apple.iossystems.smp.reporting.core.adapter;

import com.apple.iossystems.smp.domain.jsonAdapter.JsonAdapter;
import com.apple.iossystems.smp.reporting.core.email.CardEvent;
import com.apple.iossystems.smp.utils.JSONUtils;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * @author Toch
 */
@JsonAdapter(CardEvent.class)
@Component
public class CardEventAdapter implements JsonSerializer<CardEvent>, JsonDeserializer<CardEvent>
{
    private static final String DPAN_ID = "dpanId";
    private static final String CARD_DISPLAY_NUMBER = "cardDisplayNumber";
    private static final String CARD_DESCRIPTION = "cardDescription";
    private static final String EVENT_STATUS = "eventStatus";
    private static final String TRUTH_ON_CARD = "truthOnCard";

    @Override
    public JsonElement serialize(CardEvent src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject root = new JsonObject();

        JSONUtils.setAttributeValue(root, DPAN_ID, src.getDpanId());
        JSONUtils.setAttributeValue(root, CARD_DISPLAY_NUMBER, src.getCardDisplayNumber());
        JSONUtils.setAttributeValue(root, CARD_DESCRIPTION, src.getCardDescription());
        JSONUtils.setAttributeValue(root, EVENT_STATUS, src.isSuccessful());
        JSONUtils.setAttributeValue(root, TRUTH_ON_CARD, src.isTruthOnCard());

        return root;
    }

    @Override
    public CardEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject root = json.getAsJsonObject();

        return new CardEvent(JSONUtils.getAttributeValueAsString(root, DPAN_ID),
                JSONUtils.getAttributeValueAsString(root, CARD_DISPLAY_NUMBER),
                JSONUtils.getAttributeValueAsString(root, CARD_DESCRIPTION),
                JSONUtils.getAttributeValueAsBooleanWithDefault(root, EVENT_STATUS, true),
                JSONUtils.getAttributeValueAsBooleanWithDefault(root, TRUTH_ON_CARD, false));
    }
}