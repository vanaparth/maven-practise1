package com.apple.iossystems.smp.reporting.core.adapter;

import com.apple.iossystems.smp.domain.jsonAdapter.JsonAdapter;
import com.apple.iossystems.smp.reporting.ireporter.publish.AuditRecord;
import com.apple.iossystems.smp.utils.JSONUtils;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * @author Toch
 */
@JsonAdapter(AuditRecord.class)
@Component
public class AuditRecordAdapter implements JsonSerializer<AuditRecord>, JsonDeserializer<AuditRecord>
{
    private static final String SENT = "sent";
    private static final String FAILED = "failed";
    private static final String BACKLOG = "backlog";
    private static final String LOST = "lost";

    @Override
    public JsonElement serialize(AuditRecord src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject root = new JsonObject();

        JSONUtils.setAttributeValue(root, SENT, src.getSent());
        JSONUtils.setAttributeValue(root, FAILED, src.getFailed());
        JSONUtils.setAttributeValue(root, BACKLOG, src.getBacklog());
        JSONUtils.setAttributeValue(root, LOST, src.getLost());

        return root;
    }

    @Override
    public AuditRecord deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject root = json.getAsJsonObject();

        return new AuditRecord(
                Integer.parseInt(JSONUtils.getAttributeValueAsStringWithDefault(root, SENT, "0")),
                Integer.parseInt(JSONUtils.getAttributeValueAsStringWithDefault(root, FAILED, "0")),
                Integer.parseInt(JSONUtils.getAttributeValueAsStringWithDefault(root, BACKLOG, "0")),
                Integer.parseInt(JSONUtils.getAttributeValueAsStringWithDefault(root, LOST, "0")));
    }
}