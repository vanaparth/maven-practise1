package com.apple.iossystems.smp.reporting.core.util;

import com.google.gson.JsonObject;

/**
 * @author Toch
 */
public class JsonObjectWriter
{
    private JsonObjectWriter()
    {
    }

    public static void addProperty(JsonObject jsonObject, String key, String value)
    {
        if (value != null)
        {
            jsonObject.addProperty(key, value);
        }
    }

    public static void addProperty(JsonObject jsonObject, String key, Boolean value)
    {
        if (value != null)
        {
            jsonObject.addProperty(key, value);
        }
    }
}