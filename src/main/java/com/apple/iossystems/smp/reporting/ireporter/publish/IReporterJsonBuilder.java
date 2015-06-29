package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

/**
 * @author Toch
 */
public class IReporterJsonBuilder
{
    private IReporterJsonBuilder()
    {
    }

    public static String toJson(List<EventRecord> eventRecords)
    {
        JsonObject jsonObject = new JsonObject();

        jsonObject.addProperty("Version", "1.0");
        jsonObject.add("txn", eventRecordsToJsonArray(eventRecords));

        return jsonObject.toString();
    }

    private static JsonArray eventRecordsToJsonArray(List<EventRecord> eventRecords)
    {
        JsonArray jsonArray = new JsonArray();

        for (EventRecord eventRecord : eventRecords)
        {
            jsonArray.add(eventRecordToJson(eventRecord));
        }

        return jsonArray;
    }

    private static JsonObject eventRecordToJson(EventRecord eventRecord)
    {
        JsonObject jsonObject = new JsonObject();

        for (Map.Entry<String, String> entry : eventRecord.getData().entrySet())
        {
            jsonObject.addProperty(entry.getKey(), entry.getValue());
        }

        return jsonObject;
    }
}