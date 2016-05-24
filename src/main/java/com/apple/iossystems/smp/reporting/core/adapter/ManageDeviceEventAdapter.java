package com.apple.iossystems.smp.reporting.core.adapter;

import com.apple.iossystems.smp.domain.jsonAdapter.JsonAdapter;
import com.apple.iossystems.smp.reporting.core.email.CardEvent;
import com.apple.iossystems.smp.reporting.core.email.FmipSource;
import com.apple.iossystems.smp.reporting.core.email.ManageDeviceEvent;
import com.apple.iossystems.smp.reporting.core.email.ManageDeviceEventSource;
import com.apple.iossystems.smp.utils.JSONUtils;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Toch
 */
@JsonAdapter(ManageDeviceEvent.class)
@Component
public class ManageDeviceEventAdapter implements JsonSerializer<ManageDeviceEvent>, JsonDeserializer<ManageDeviceEvent>
{
    private static final String EVENT_TYPE = "eventType";
    private static final String CARD_HOLDER_NAME = "cardHolderName";
    private static final String CARD_HOLDER_EMAIL = "cardHolderEmail";
    private static final String CONVERSATION_ID = "conversationId";
    private static final String DSID = "dsid";
    private static final String SEID = "seid";
    private static final String TIMESTAMP = "timestamp";
    private static final String TIMEZONE = "timezone";
    private static final String LOCALE = "locale";
    private static final String DEVICE_NAME = "deviceName";
    private static final String DEVICE_TYPE = "deviceType";
    private static final String DEVICE_IMAGE_URL = "deviceImageURL";
    private static final String MANAGE_DEVICE_EVENT_SOURCE = "manageDeviceEventSource";
    private static final String FMIP_SOURCE = "fmipSource";
    private static final String CARD_EVENTS = "cardEvents";

    @Override
    public JsonElement serialize(ManageDeviceEvent src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject root = new JsonObject();

        JSONUtils.setAttributeValue(root, EVENT_TYPE, src.getEventType());
        JSONUtils.setAttributeValue(root, CARD_HOLDER_NAME, src.getCardHolderName());
        JSONUtils.setAttributeValue(root, CARD_HOLDER_EMAIL, src.getCardHolderEmail());
        JSONUtils.setAttributeValue(root, CONVERSATION_ID, src.getConversationId());
        JSONUtils.setAttributeValue(root, DSID, src.getDsid());
        JSONUtils.setAttributeValue(root, SEID, src.getSeid());
        JSONUtils.setAttributeValue(root, TIMESTAMP, src.getTimestamp());
        JSONUtils.setAttributeValue(root, TIMEZONE, src.getTimezone());
        JSONUtils.setAttributeValue(root, LOCALE, src.getLocale());
        JSONUtils.setAttributeValue(root, DEVICE_NAME, src.getDeviceName());
        JSONUtils.setAttributeValue(root, DEVICE_TYPE, src.getDeviceType());
        JSONUtils.setAttributeValue(root, DEVICE_IMAGE_URL, src.getDeviceImageUrl());

        root.add(CARD_EVENTS, context.serialize(src.getCardEvents(), List.class));

        ManageDeviceEventSource manageDeviceEventSource = src.getManageDeviceEventSource();

        if (manageDeviceEventSource != null)
        {
            JSONUtils.setAttributeValue(root, MANAGE_DEVICE_EVENT_SOURCE, manageDeviceEventSource.getCode());
        }

        FmipSource fmipSource = src.getFmipSource();

        if (fmipSource != null)
        {
            JSONUtils.setAttributeValue(root, FMIP_SOURCE, fmipSource.getCode());
        }

        return root;
    }

    @Override
    public ManageDeviceEvent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject root = json.getAsJsonObject();

        JsonElement jsonElement = root.get(CARD_EVENTS);

        List<CardEvent> cardEvents = (jsonElement != null) ? Arrays.asList((CardEvent[]) context.deserialize(jsonElement, CardEvent[].class)) : Collections.<CardEvent>emptyList();

        return ManageDeviceEvent.getBuilder().
                eventType(JSONUtils.getAttributeValueAsString(root, EVENT_TYPE)).
                cardHolderName(JSONUtils.getAttributeValueAsString(root, CARD_HOLDER_NAME)).
                cardHolderEmail(JSONUtils.getAttributeValueAsString(root, CARD_HOLDER_EMAIL)).
                conversationId(JSONUtils.getAttributeValueAsString(root, CONVERSATION_ID)).
                dsid(JSONUtils.getAttributeValueAsString(root, DSID)).
                seid(JSONUtils.getAttributeValueAsString(root, SEID)).
                timestamp(JSONUtils.getAttributeValueAsString(root, TIMESTAMP)).
                timezone(JSONUtils.getAttributeValueAsString(root, TIMEZONE)).
                locale(JSONUtils.getAttributeValueAsString(root, LOCALE)).
                deviceName(JSONUtils.getAttributeValueAsString(root, DEVICE_NAME)).
                deviceType(JSONUtils.getAttributeValueAsString(root, DEVICE_TYPE)).
                deviceImageUrl(JSONUtils.getAttributeValueAsString(root, DEVICE_IMAGE_URL)).
                manageDeviceEventSource(ManageDeviceEventSource.fromCode(JSONUtils.getAttributeValueAsString(root, MANAGE_DEVICE_EVENT_SOURCE))).
                fmipSource(FmipSource.fromCode(JSONUtils.getAttributeValueAsString(root, FMIP_SOURCE))).
                cardEvents(cardEvents).
                build();
    }
}