package com.apple.iossystems.smp.reporting.core.adapter;

import com.apple.iossystems.smp.domain.jsonAdapter.JsonAdapter;
import com.apple.iossystems.smp.reporting.core.email.ProvisionCardEvent;
import com.apple.iossystems.smp.utils.JSONUtils;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * @author Toch
 */
@JsonAdapter(ProvisionCardEvent.class)
@Component
public class ProvisionCardEventAdapter implements JsonSerializer<ProvisionCardEvent>, JsonDeserializer<ProvisionCardEvent>
{
    private static final String CONVERSATION_ID = "conversationId";
    private static final String TIMESTAMP = "timestamp";
    private static final String CARD_HOLDER_NAME = "cardHolderName";
    private static final String CARD_HOLDER_EMAIL = "cardHolderEmail";
    private static final String CARD_DISPLAY_NUMBER = "cardDisplayNumber";
    private static final String DEVICE_NAME = "deviceName";
    private static final String DEVICE_TYPE = "deviceType";
    private static final String DSID = "dsid";
    private static final String LOCALE = "locale";
    private static final String FIRST_PROVISION = "firstProvision";

    @Override
    public JsonElement serialize(ProvisionCardEvent src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject root = new JsonObject();

        JSONUtils.setAttributeValue(root, CONVERSATION_ID, src.getConversationId());
        JSONUtils.setAttributeValue(root, TIMESTAMP, src.getTimestamp());
        JSONUtils.setAttributeValue(root, CARD_HOLDER_NAME, src.getCardHolderName());
        JSONUtils.setAttributeValue(root, CARD_HOLDER_EMAIL, src.getCardHolderEmail());
        JSONUtils.setAttributeValue(root, CARD_DISPLAY_NUMBER, src.getCardDisplayNumber());
        JSONUtils.setAttributeValue(root, DEVICE_NAME, src.getDeviceName());
        JSONUtils.setAttributeValue(root, DEVICE_TYPE, src.getDeviceType());
        JSONUtils.setAttributeValue(root, DSID, src.getDsid());
        JSONUtils.setAttributeValue(root, LOCALE, src.getLocale());
        JSONUtils.setAttributeValue(root, FIRST_PROVISION, src.isFirstProvision());

        return root;
    }

    @Override
    public ProvisionCardEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject root = json.getAsJsonObject();

        return ProvisionCardEvent.getBuilder().
                conversationId(JSONUtils.getAttributeValueAsString(root, CONVERSATION_ID)).
                timestamp(JSONUtils.getAttributeValueAsString(root, TIMESTAMP)).
                cardHolderName(JSONUtils.getAttributeValueAsString(root, CARD_HOLDER_NAME)).
                cardHolderEmail(JSONUtils.getAttributeValueAsString(root, CARD_HOLDER_EMAIL)).
                cardDisplayNumber(JSONUtils.getAttributeValueAsString(root, CARD_DISPLAY_NUMBER)).
                deviceName(JSONUtils.getAttributeValueAsString(root, DEVICE_NAME)).
                deviceType(JSONUtils.getAttributeValueAsString(root, DEVICE_TYPE)).
                dsid(JSONUtils.getAttributeValueAsString(root, DSID)).
                locale(JSONUtils.getAttributeValueAsString(root, LOCALE)).
                firstProvision(Boolean.TRUE.equals(JSONUtils.getAttributeValueAsBoolean(root, FIRST_PROVISION))).build();
    }
}