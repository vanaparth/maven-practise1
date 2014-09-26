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
    private final boolean status;
    private final CardEventSource cardEventSource;

    private CardEvent(String dpanId, boolean status, CardEventSource cardEventSource)
    {
        this.dpanId = dpanId;
        this.status = status;
        this.cardEventSource = cardEventSource;
    }

    public static CardEvent getInstance(String dpanId, boolean status, CardEventSource cardEventSource)
    {
        return new CardEvent(dpanId, status, cardEventSource);
    }

    public String getDpanId()
    {
        return dpanId;
    }

    public boolean getStatus()
    {
        return status;
    }

    public CardEventSource getCardEventSource()
    {
        return cardEventSource;
    }

    private static class CardEventAdapter implements JsonSerializer<CardEvent>, JsonDeserializer<CardEvent>
    {
        @Override
        public JsonElement serialize(CardEvent cardEvent, Type type, JsonSerializationContext context) throws JsonParseException
        {
            JsonObject jsonObject = new JsonObject();

            JsonObjectWriter.addProperty(jsonObject, "dpanId", cardEvent.dpanId);
            JsonObjectWriter.addProperty(jsonObject, "status", cardEvent.status);

            if (cardEvent.cardEventSource != null)
            {
                JsonObjectWriter.addProperty(jsonObject, "cardEventSourceCode", cardEvent.cardEventSource.getCode());
            }

            return jsonObject;
        }

        @Override
        public CardEvent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String dpanId = JsonObjectReader.getAsString(jsonObject, "dpanId");
            boolean status = JsonObjectReader.getAsBoolean(jsonObject, "status");
            CardEventSource cardEventSource = CardEventSource.getSource(JsonObjectReader.getAsString(jsonObject, "cardEventSourceCode"));

            return new CardEvent(dpanId, status, cardEventSource);
        }
    }

    public static Gson getGson()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(CardEvent.class, new CardEventAdapter());

        return gsonBuilder.create();
    }
}