package com.apple.iossystems.smp.reporting.core.adapter;

import com.apple.iossystems.smp.domain.jsonAdapter.JsonAdapter;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfiguration;
import com.apple.iossystems.smp.utils.JSONUtils;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

/**
 * @author Toch
 */
@JsonAdapter(IReporterConfiguration.class)
@Component
public class IReporterConfigurationAdapter implements JsonSerializer<IReporterConfiguration>, JsonDeserializer<IReporterConfiguration>
{
    private static final String BATCH_SIZE = "batchSize";
    private static final String CONFIG_RELOAD_FREQUENCY = "configReloadFrequencyInMinutes";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String END_POINT = "endpoint";
    private static final String HEADERS = "headers";
    private static final String HOST_NAME = "hostname";
    private static final String PROTOCOL = "protocol";
    private static final String PUBLISH_FLAG = "shouldPublishFlag";
    private static final String PUBLISH_FREQUENCY = "publishFrequencyInSeconds";
    private static final String URI = "uri";
    private static final String X_LOAD_TEXT = "X-LoadText";


    @Override
    public JsonElement serialize(IReporterConfiguration src, Type typeOfSrc, JsonSerializationContext context)
    {
        JsonObject root = new JsonObject();

        JsonObject headers = new JsonObject();
        JSONUtils.setAttributeValue(headers, X_LOAD_TEXT, src.getPublishKey());
        JSONUtils.setAttributeValue(headers, CONTENT_TYPE, src.getContentType());

        JsonObject endPoint = new JsonObject();
        JSONUtils.setAttributeValue(endPoint, PROTOCOL, src.getProtocol());
        JSONUtils.setAttributeValue(endPoint, HOST_NAME, src.getHostname());
        JSONUtils.setAttributeValue(endPoint, URI, src.getUri());

        JSONUtils.setAttributeValue(root, BATCH_SIZE, src.getMaxBatchSize());
        JSONUtils.setAttributeValue(root, CONFIG_RELOAD_FREQUENCY, src.getConfigurationReloadFrequency());
        JSONUtils.setAttributeValue(root, PUBLISH_FLAG, src.isPublishEnabled());
        JSONUtils.setAttributeValue(root, PUBLISH_FREQUENCY, src.getPublishFrequency());

        root.add(HEADERS, headers);
        root.add(END_POINT, endPoint);

        return root;
    }

    @Override
    public IReporterConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException
    {
        JsonObject root = json.getAsJsonObject();

        JsonObject headers = root.getAsJsonObject(HEADERS);
        String xLoadText = JSONUtils.getAttributeValueAsString(headers, X_LOAD_TEXT);
        String contentType = JSONUtils.getAttributeValueAsString(headers, CONTENT_TYPE);

        JsonObject endPoint = root.getAsJsonObject(END_POINT);
        String protocol = JSONUtils.getAttributeValueAsString(endPoint, PROTOCOL);
        String hostname = JSONUtils.getAttributeValueAsString(endPoint, HOST_NAME);
        String uri = JSONUtils.getAttributeValueAsString(endPoint, URI);

        return IReporterConfiguration.getBuilder().
                protocol(protocol).
                hostname(hostname).
                uri(uri).
                contentType(contentType).
                maxBatchSize(Integer.parseInt(JSONUtils.getAttributeValueAsStringWithDefault(root, BATCH_SIZE, "0"))).
                configurationReloadFrequency(Integer.parseInt(JSONUtils.getAttributeValueAsStringWithDefault(root, CONFIG_RELOAD_FREQUENCY, "0"))).
                publishEnabled(JSONUtils.getAttributeValueAsBooleanWithDefault(root, PUBLISH_FLAG, true)).
                publishFrequency(Integer.parseInt(JSONUtils.getAttributeValueAsStringWithDefault(root, PUBLISH_FREQUENCY, "0"))).
                publishKey(xLoadText).build();
    }
}