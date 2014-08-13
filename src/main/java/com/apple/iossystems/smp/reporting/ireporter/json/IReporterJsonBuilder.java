package com.apple.iossystems.smp.reporting.ireporter.json;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Toch
 */
public class IReporterJsonBuilder
{
    private IReporterJsonBuilder()
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

        Set<Map.Entry<String, String>> entrySet = e.getData().entrySet();

        for (Map.Entry<String, String> entry : entrySet)
        {
            jsonObject.addProperty(entry.getKey(), entry.getValue());
        }

        return jsonObject;
    }
}