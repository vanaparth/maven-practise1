package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.util.JsonObjectReader;
import com.apple.iossystems.smp.reporting.core.util.JsonObjectWriter;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author Toch
 */
class CardEvent
{
    private final String dpanId;
    private final boolean eventStatus;

    private CardEvent(String dpanId, boolean eventStatus)
    {
        this.dpanId = dpanId;
        this.eventStatus = eventStatus;
    }

    public static CardEvent getInstance(String dpanId, boolean eventStatus)
    {
        return new CardEvent(dpanId, eventStatus);
    }

    public String getDpanId()
    {
        return dpanId;
    }

    public boolean getEventStatus()
    {
        return eventStatus;
    }

    public static Gson getGson()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(CardEvent.class, new CardEventAdapter());

        return gsonBuilder.create();
    }

    private static class CardEventAdapter implements JsonSerializer<CardEvent>, JsonDeserializer<CardEvent>
    {
        @Override
        public JsonElement serialize(CardEvent cardEvent, Type type, JsonSerializationContext context) throws JsonParseException
        {
            JsonObject jsonObject = new JsonObject();

            JsonObjectWriter.addProperty(jsonObject, "dpanId", cardEvent.dpanId);
            JsonObjectWriter.addProperty(jsonObject, "eventStatus", cardEvent.eventStatus);

            return jsonObject;
        }

        @Override
        public CardEvent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String dpanId = JsonObjectReader.getAsString(jsonObject, "dpanId");
            boolean eventStatus = JsonObjectReader.getAsBoolean(jsonObject, "eventStatus");

            return new CardEvent(dpanId, eventStatus);
        }
    }
}