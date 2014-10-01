package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.util.JsonObjectReader;
import com.apple.iossystems.smp.reporting.core.util.JsonObjectWriter;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @author Toch
 */
public class ManageCardEvent
{
    private final String cardHolderName;
    private final String cardHolderEmail;
    private final String dsid;
    private final String locale;
    private final String deviceName;
    private final String deviceImageUrl;
    private final CardEventSource cardEventSource;
    private final ManageCardAPI manageCardAPI;
    private final List<CardEvent> cardEvents;

    private ManageCardEvent(Builder builder)
    {
        cardHolderName = builder.cardHolderName;
        cardHolderEmail = builder.cardHolderEmail;
        dsid = builder.dsid;
        locale = builder.locale;
        deviceName = builder.deviceName;
        deviceImageUrl = builder.deviceImageUrl;
        cardEventSource = builder.cardEventSource;
        manageCardAPI = builder.manageCardAPI;
        cardEvents = builder.cardEvents;
    }

    public String getCardHolderName()
    {
        return cardHolderName;
    }

    public String getCardHolderEmail()
    {
        return cardHolderEmail;
    }

    public String getDsid()
    {
        return dsid;
    }

    public String getLocale()
    {
        return locale;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public String getDeviceImageUrl()
    {
        return deviceImageUrl;
    }

    public CardEventSource getCardEventSource()
    {
        return cardEventSource;
    }

    public ManageCardAPI getManageCardAPI()
    {
        return manageCardAPI;
    }

    public List<CardEvent> getCardEvents()
    {
        return cardEvents;
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private String cardHolderName;
        private String cardHolderEmail;
        private String dsid;
        private String locale;
        private String deviceName;
        private String deviceImageUrl;
        private CardEventSource cardEventSource;
        private ManageCardAPI manageCardAPI;
        private List<CardEvent> cardEvents;

        private Builder()
        {
        }

        public Builder cardHolderName(String value)
        {
            cardHolderName = value;
            return this;
        }

        public Builder cardHolderEmail(String value)
        {
            cardHolderEmail = value;
            return this;
        }

        public Builder dsid(String value)
        {
            dsid = value;
            return this;
        }

        public Builder locale(String value)
        {
            locale = value;
            return this;
        }

        public Builder deviceName(String value)
        {
            deviceName = value;
            return this;
        }

        public Builder deviceImageUrl(String value)
        {
            deviceImageUrl = value;
            return this;
        }

        public Builder cardEventSource(CardEventSource value)
        {
            cardEventSource = value;
            return this;
        }

        public Builder manageCardAPI(ManageCardAPI value)
        {
            manageCardAPI = value;
            return this;
        }

        public Builder cardEvents(List<CardEvent> value)
        {
            cardEvents = value;
            return this;
        }

        public ManageCardEvent build()
        {
            return new ManageCardEvent(this);
        }
    }

    private static Gson getGson()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(ManageCardEvent.class, new ManageCardEventAdapter());

        return gsonBuilder.create();
    }

    public static ManageCardEvent fromJson(String json)
    {
        if (json != null)
        {
            return getGson().fromJson(json, ManageCardEvent.class);
        }
        else
        {
            return new Builder().build();
        }
    }

    public String toJson()
    {
        JsonObject jsonObject = new JsonObject();

        JsonObjectWriter.addProperty(jsonObject, "cardHolderName", cardHolderName);
        JsonObjectWriter.addProperty(jsonObject, "cardHolderEmail", cardHolderEmail);
        JsonObjectWriter.addProperty(jsonObject, "dsid", dsid);
        JsonObjectWriter.addProperty(jsonObject, "locale", locale);
        JsonObjectWriter.addProperty(jsonObject, "deviceName", deviceName);
        JsonObjectWriter.addProperty(jsonObject, "deviceImageURL", deviceImageUrl);

        if (cardEventSource != null)
        {
            JsonObjectWriter.addProperty(jsonObject, "cardEventSource", cardEventSource.getCode());
        }

        if (manageCardAPI != null)
        {
            JsonObjectWriter.addProperty(jsonObject, "manageCardAPI", manageCardAPI.getCode());
        }

        if ((cardEvents != null) && (!cardEvents.isEmpty()))
        {
            JsonObjectWriter.addProperty(jsonObject, "cardEvents", CardEvent.getGson().toJson(cardEvents, List.class));
        }

        return jsonObject.toString();
    }

    private static class ManageCardEventAdapter implements JsonDeserializer<ManageCardEvent>
    {
        @Override
        public ManageCardEvent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String cardHolderName = JsonObjectReader.getAsString(jsonObject, "cardHolderName");
            String cardHolderEmail = JsonObjectReader.getAsString(jsonObject, "cardHolderEmail");
            String dsid = JsonObjectReader.getAsString(jsonObject, "dsid");
            String locale = JsonObjectReader.getAsString(jsonObject, "locale");
            String deviceName = JsonObjectReader.getAsString(jsonObject, "deviceName");
            String deviceImageUrl = JsonObjectReader.getAsString(jsonObject, "deviceImageURL");

            CardEventSource cardEventSource = null;

            String cardEventSourceCode = JsonObjectReader.getAsString(jsonObject, "cardEventSource");

            if (cardEventSourceCode != null)
            {
                cardEventSource = CardEventSource.getCardEventSource(cardEventSourceCode);
            }

            ManageCardAPI manageCardAPI = null;

            String manageCardApiCode = JsonObjectReader.getAsString(jsonObject, "manageCardAPI");

            if (manageCardApiCode != null)
            {
                manageCardAPI = ManageCardAPI.getManageCardAPI(manageCardApiCode);
            }

            List<CardEvent> cardEvents = null;

            JsonArray jsonArray = JsonObjectReader.getAsJsonArray(jsonObject, "cardEvents");

            if (jsonArray != null)
            {
                cardEvents = Arrays.asList(CardEvent.getGson().fromJson(jsonArray, CardEvent[].class));
            }

            return new Builder().cardHolderName(cardHolderName).
                    cardHolderEmail(cardHolderEmail).
                    dsid(dsid).
                    locale(locale).
                    deviceName(deviceName).
                    deviceImageUrl(deviceImageUrl).
                    cardEventSource(cardEventSource).
                    manageCardAPI(manageCardAPI).
                    cardEvents(cardEvents).build();
        }
    }
}