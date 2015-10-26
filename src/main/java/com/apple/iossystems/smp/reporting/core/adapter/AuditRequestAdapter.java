package com.apple.iossystems.smp.reporting.core.adapter;

import com.apple.iossystems.smp.domain.jsonAdapter.JsonAdapter;
import com.apple.iossystems.smp.reporting.ireporter.publish.AuditRecord;
import com.apple.iossystems.smp.reporting.ireporter.publish.AuditRequest;
import com.apple.iossystems.smp.utils.JSONUtils;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Toch
 */
@JsonAdapter(AuditRequest.class)
@Component
public class AuditRequestAdapter implements JsonSerializer<AuditRequest>, JsonDeserializer<AuditRequest>
{
    private static final String VERSION = "Version";
    private static final String CONTENT = "txn";

    @Override
    public JsonElement serialize(AuditRequest src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject root = new JsonObject();

        JSONUtils.setAttributeValue(root, VERSION, src.getVersion());

        List<AuditRecord> auditRecords = src.getAuditRecords();

        if ((auditRecords != null) && (!auditRecords.isEmpty()))
        {
            root.add(CONTENT, context.serialize(auditRecords));
        }

        return root;
    }

    @Override
    public AuditRequest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject root = json.getAsJsonObject();

        JsonElement jsonElement = root.get(CONTENT);

        List<AuditRecord> auditRecords = (jsonElement != null) ? Arrays.asList((AuditRecord[]) context.deserialize(jsonElement, AuditRecord[].class)) : Collections.<AuditRecord>emptyList();

        return new AuditRequest(auditRecords);
    }
}