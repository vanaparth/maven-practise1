package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.util.JsonObjectReader;
import com.apple.iossystems.smp.reporting.core.util.JsonObjectWriter;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * @author Toch
 */
public class ManageCardEvent
{
    private final String cardHolderName;
    private final String cardHolderEmail;
    private final String locale;
    private final String deviceName;
    private final String deviceImageUrl;

    private ManageCardEvent(Builder builder)
    {
        cardHolderName = builder.getCardHolderName();
        cardHolderEmail = builder.cardHolderEmail;
        locale = builder.locale;
        deviceName = builder.deviceName;
        deviceImageUrl = builder.deviceImageUrl;
    }

    public String getCardHolderName()
    {
        return cardHolderName;
    }

    public String getCardHolderEmail()
    {
        return cardHolderEmail;
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

    private static class Builder
    {
        private String firstName;
        private String lastName;
        private String cardHolderEmail;
        private String locale;
        private String deviceName;
        private String deviceImageUrl;

        private Builder()
        {
        }

        private Builder firstName(String value)
        {
            firstName = value;
            return this;
        }

        private Builder lastName(String value)
        {
            lastName = value;
            return this;
        }

        private Builder cardHolderEmail(String value)
        {
            cardHolderEmail = value;
            return this;
        }

        private Builder locale(String value)
        {
            locale = value;
            return this;
        }

        private Builder deviceName(String value)
        {
            deviceName = value;
            return this;
        }

        public Builder deviceImageUrl(String value)
        {
            deviceImageUrl = value;
            return this;
        }

        private String getCardHolderName()
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

        private ManageCardEvent build()
        {
            return new ManageCardEvent(this);
        }
    }

    private static final String FIRST_NAME = "customerFirstName";
    private static final String LAST_NAME = "customerLastName";
    private static final String CARD_HOLDER_EMAIL = "customerEmail";
    private static final String LOCALE = "customerLocale";
    private static final String DEVICE_NAME = "deviceName";
    private static final String DEVICE_IMAGE_URL = "deviceImageURL";

    public static String toJson(Map<String, String> map)
    {
        JsonObject jsonObject = new JsonObject();

        JsonObjectWriter.addProperty(jsonObject, FIRST_NAME, map.get(FIRST_NAME));
        JsonObjectWriter.addProperty(jsonObject, LAST_NAME, map.get(LAST_NAME));
        JsonObjectWriter.addProperty(jsonObject, CARD_HOLDER_EMAIL, map.get(CARD_HOLDER_EMAIL));
        JsonObjectWriter.addProperty(jsonObject, LOCALE, map.get(LOCALE));
        JsonObjectWriter.addProperty(jsonObject, DEVICE_NAME, map.get(DEVICE_NAME));
        JsonObjectWriter.addProperty(jsonObject, DEVICE_IMAGE_URL, map.get(DEVICE_IMAGE_URL));

        return jsonObject.toString();
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

    private static Gson getGson()
    {
        GsonBuilder gsonBuilder = new GsonBuilder();

        gsonBuilder.registerTypeAdapter(ManageCardEvent.class, new ManageCardEventAdapter());

        return gsonBuilder.create();
    }

    private static class ManageCardEventAdapter implements JsonDeserializer<ManageCardEvent>
    {
        @Override
        public ManageCardEvent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext context) throws JsonParseException
        {
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            String firstName = JsonObjectReader.getAsString(jsonObject, FIRST_NAME);
            String lastName = JsonObjectReader.getAsString(jsonObject, LAST_NAME);
            String cardHolderEmail = JsonObjectReader.getAsString(jsonObject, CARD_HOLDER_EMAIL);
            String locale = JsonObjectReader.getAsString(jsonObject, LOCALE);
            String deviceName = JsonObjectReader.getAsString(jsonObject, DEVICE_NAME);
            String deviceImageUrl = JsonObjectReader.getAsString(jsonObject, DEVICE_IMAGE_URL);

            return new Builder().firstName(firstName).
                    lastName(lastName).
                    cardHolderEmail(cardHolderEmail).
                    locale(locale).
                    deviceName(deviceName).
                    deviceImageUrl(deviceImageUrl).build();
        }
    }
}