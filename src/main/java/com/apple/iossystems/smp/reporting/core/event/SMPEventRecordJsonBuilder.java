package com.apple.iossystems.smp.reporting.core.event;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Toch
 */
public class SMPEventRecordJsonBuilder
{
    private SMPEventRecordJsonBuilder()
    {
    }

    public static String toJson(List<EventRecord> list)
    {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("Version", "1.0");
        jsonObject.add("txn", eventRecordsToJsonArray(list));

        return jsonObject.toString();
    }

    private static JsonArray eventRecordsToJsonArray(List<EventRecord> list)
    {
        JsonArray jsonArray = new JsonArray();

        for (EventRecord e : list)
        {
            jsonArray.add(eventRecordToJson(e));
        }

        return jsonArray;
    }

    private static JsonObject eventRecordToJson(EventRecord e)
    {
        JsonObject jsonObject = new JsonObject();

        Set<Map.Entry<SMPEventAttribute, String>> mapEntrySet = ((SMPEventRecord) e).getFields().entrySet();

        for (Map.Entry<SMPEventAttribute, String> mapEntry : mapEntrySet)
        {
            jsonObject.addProperty(mapEntry.getKey().getJsonKey(), mapEntry.getValue());
        }

        return jsonObject;
    }
}
