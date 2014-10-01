package com.apple.iossystems.smp.reporting.core.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author Toch
 */
public class JsonObjectReader
{
    private JsonObjectReader()
    {
    }

    public static String getAsString(JsonObject jsonObject, String key)
    {
        JsonElement jsonElement = jsonObject.get(key);

        return (jsonElement != null) ? jsonElement.getAsString() : null;
    }

    public static boolean getAsBoolean(JsonObject jsonObject, String key)
    {
        JsonElement jsonElement = jsonObject.get(key);

        return ((jsonElement != null) && jsonElement.getAsBoolean());
    }
}