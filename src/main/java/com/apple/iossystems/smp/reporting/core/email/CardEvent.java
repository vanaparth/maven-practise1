package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.util.JsonObjectReader;
import com.google.gson.*;

import java.lang.reflect.Type;

/**
 * @author Toch
 */
class CardEvent
{
    private final String dpanId;
    private final boolean status;

    private CardEvent(String dpanId, boolean status)
    {
        this.dpanId = dpanId;
        this.status = status;
    }

    public static CardEvent getInstance(String dpanId, boolean status)
    {
        return new CardEvent(dpanId, status);
    }

    public String getDpanId()
    {
        return dpanId;
    }

    public boolean getStatus()
    {
        return status;
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
        public JsonElement serialize(CardEvent cardEvent, Type type, JsonSerializationContext context)
        {
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("dpanId", cardEvent.dpanId);
            jsonObject.addProperty("status", cardEvent.status);

            return jsonObject;
        }

        @Override
        public CardEvent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String dpanId = JsonObjectReader.getAsString(jsonObject, "dpanId");
            boolean status = JsonObjectReader.getAsBoolean(jsonObject, "status");

            return new CardEvent(dpanId, status);
        }
    }
}