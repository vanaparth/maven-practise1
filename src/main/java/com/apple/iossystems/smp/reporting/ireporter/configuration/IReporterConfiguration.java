package com.apple.iossystems.smp.reporting.ireporter.configuration;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
import com.google.gson.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class IReporterConfiguration
{
    // URLs
    public static final String BASE_URL = ApplicationConfigurationManager.getIReporterURL();

    public static final String DEFAULT_REPORTS_CONFIGURATION_URL = BASE_URL + "/e3/rest/1/config/stockholm";

    public static final String DEFAULT_AUDIT_CONFIGURATION_URL = BASE_URL + "/e3/rest/1/config/stockholm_audit";

    public static final String DEFAULT_PAYMENT_REPORTS_CONFIGURATION_URL = BASE_URL + "/e3/rest/1/config/oslo";

    public static final String DEFAULT_REPORTS_URL = BASE_URL + "/e3/rest/1/stockholm";

    public static final String DEFAULT_AUDIT_URL = BASE_URL + "/e3/rest/1/stockholm_audit";

    public static final String DEFAULT_PAYMENT_REPORTS_URL = BASE_URL + "/e3/rest/1/oslo";

    // Other configuration settings
    public static final String DEFAULT_PUBLISH_KEY = "QWERTYUIOPASDF12";

    public static final String DEFAULT_CONTENT_TYPE = "application/json";

    public static final boolean DEFAULT_PUBLISH_ENABLED = true;

    public static final int DEFAULT_MAX_BATCH_SIZE = 100;

    // Publish frequency in milliseconds
    public static final int DEFAULT_PUBLISH_FREQUENCY = 2 * 60 * 1000;

    // Configuration reload frequency in milliseconds
    public static final int DEFAULT_CONFIGURATION_RELOAD_FREQUENCY = 60 * 60 * 1000;


    private final String publishURL;

    private final String publishKey;

    private final String contentType;

    private final boolean publishEnabled;

    private final int maxBatchSize;

    private final int publishFrequency;

    private final int configurationReloadFrequency;


    private IReporterConfiguration(Builder builder)
    {
        publishURL = builder.publishURL;
        publishKey = builder.publishKey;
        contentType = builder.contentType;
        publishEnabled = builder.publishEnabled;
        maxBatchSize = builder.maxBatchSize;
        publishFrequency = builder.publishFrequency;
        configurationReloadFrequency = builder.configurationReloadFrequency;
    }

    private static IReporterConfiguration getInstance(Builder builder)
    {
        return new IReporterConfiguration(builder);
    }

    public String getPublishURL()
    {
        return publishURL;
    }

    public String getPublishKey()
    {
        return publishKey;
    }

    public String getContentType()
    {
        return contentType;
    }

    public boolean isPublishEnabled()
    {
        return publishEnabled;
    }

    public int getMaxBatchSize()
    {
        return maxBatchSize;
    }

    public int getPublishFrequency()
    {
        return publishFrequency;
    }

    public int getConfigurationReloadFrequency()
    {
        return configurationReloadFrequency;
    }

    public Map<String, String> getRequestHeaders()
    {
        Map<String, String> headers = new HashMap<String, String>();

        headers.put("X-LoadText", getPublishKey());
        headers.put("Content-Type", getContentType());
        headers.put("METHOD", "POST");

        return headers;
    }

    public boolean isEquals(IReporterConfiguration configuration)
    {
        return ((this == configuration) || (configurationEquals(configuration)));
    }

    private boolean configurationEquals(IReporterConfiguration configuration)
    {
        return ((publishURL.equalsIgnoreCase(configuration.publishURL)) &&
                (publishKey.equalsIgnoreCase(configuration.publishKey)) &&
                (contentType.equalsIgnoreCase(configuration.contentType)) &&
                (publishEnabled == configuration.publishEnabled) &&
                (maxBatchSize == configuration.maxBatchSize) &&
                (publishFrequency == configuration.publishFrequency) &&
                (configurationReloadFrequency == configuration.configurationReloadFrequency));
    }

    public enum Type
    {
        REPORTS(DEFAULT_REPORTS_CONFIGURATION_URL, DEFAULT_REPORTS_URL),
        AUDIT(DEFAULT_AUDIT_CONFIGURATION_URL, DEFAULT_AUDIT_URL),
        PAYMENT_REPORTS(DEFAULT_PAYMENT_REPORTS_CONFIGURATION_URL, DEFAULT_PAYMENT_REPORTS_URL);

        private final String configurationURL;
        private final String publishURL;

        private Type(String configurationURL, String publishURL)
        {
            this.configurationURL = configurationURL;
            this.publishURL = publishURL;
        }

        public String getConfigurationURL()
        {
            return configurationURL;
        }

        public String getPublishURL()
        {
            return publishURL;
        }
    }

    public static class Builder
    {
        private String publishURL;

        private String publishKey;

        private String contentType;

        private boolean publishEnabled;

        private int maxBatchSize;

        private int publishFrequency;

        private int configurationReloadFrequency;

        private Builder()
        {
        }

        private static Builder getInstance()
        {
            return new Builder();
        }

        public Builder publishURL(String value)
        {
            publishURL = value;
            return this;
        }

        public Builder publishKey(String value)
        {
            publishKey = value;
            return this;
        }

        public Builder contentType(String value)
        {
            contentType = value;
            return this;
        }

        public Builder publishEnabled(boolean value)
        {
            publishEnabled = value;
            return this;
        }

        public Builder maxBatchSize(int value)
        {
            maxBatchSize = value;
            return this;
        }

        public Builder publishFrequency(int value)
        {
            publishFrequency = value;
            return this;
        }

        public Builder configurationReloadFrequency(int value)
        {
            configurationReloadFrequency = value;
            return this;
        }

        private void validate()
        {
            int defaultMinBatchSize = 1;
            int defaultMaxBatchSize = 1000;
            int minPublishFrequency = 60 * 1000;
            int maxPublishFrequency = 60 * 60 * 1000;
            int minConfigurationReloadFrequency = 60 * 60 * 1000;
            int maxConfigurationReloadFrequency = 24 * 60 * 60 * 1000;

            if ((maxBatchSize < defaultMinBatchSize) || (maxBatchSize > defaultMaxBatchSize))
            {
                maxBatchSize = defaultMaxBatchSize;
            }

            if ((publishFrequency < minPublishFrequency) || (publishFrequency > maxPublishFrequency))
            {
                publishFrequency = maxPublishFrequency;
            }

            if ((configurationReloadFrequency < minConfigurationReloadFrequency) || (configurationReloadFrequency > maxConfigurationReloadFrequency))
            {
                configurationReloadFrequency = maxConfigurationReloadFrequency;
            }
        }

        public IReporterConfiguration build()
        {
            validate();

            return IReporterConfiguration.getInstance(this);
        }

        public static IReporterConfiguration fromDefault(Type configurationType)
        {
            Builder builder = Builder.getInstance();

            builder.publishURL(configurationType.getPublishURL()).
                    publishKey(DEFAULT_PUBLISH_KEY).
                    contentType(DEFAULT_CONTENT_TYPE).
                    publishEnabled(DEFAULT_PUBLISH_ENABLED).
                    maxBatchSize(DEFAULT_MAX_BATCH_SIZE).
                    publishFrequency(DEFAULT_PUBLISH_FREQUENCY).
                    configurationReloadFrequency(DEFAULT_CONFIGURATION_RELOAD_FREQUENCY);

            return builder.build();
        }

        public static IReporterConfiguration fromJson(IReporterConfiguration.Type configurationType, String json)
        {
            try
            {
                return getGson().fromJson(json, Builder.class).build();
            }
            catch (Exception e)
            {
                return fromDefault(configurationType);
            }
        }

        private static Gson getGson()
        {
            GsonBuilder gsonBuilder = new GsonBuilder();

            gsonBuilder.registerTypeAdapter(Builder.class, new BuilderDeserializer());

            return gsonBuilder.create();
        }

        private static class BuilderDeserializer implements JsonDeserializer<Builder>
        {
            @Override
            public Builder deserialize(JsonElement jsonElement, java.lang.reflect.Type type, JsonDeserializationContext context) throws JsonParseException
            {
                JsonObject jsonObject = jsonElement.getAsJsonObject();

                // String method = jsonObject.get("method").getAsString();
                boolean shouldPublishFlag = jsonObject.get("shouldPublishFlag").getAsBoolean();
                int batchSize = jsonObject.get("batchSize").getAsInt();
                int publishFrequencyInSeconds = jsonObject.get("publishFrequencyInSeconds").getAsInt();
                int configReloadFrequencyInMinutes = jsonObject.get("configReloadFrequencyInMinutes").getAsInt();

                JsonObject headersObject = jsonObject.get("headers").getAsJsonObject();

                String xLoadText = headersObject.get("X-LoadText").getAsString();
                String contentType = headersObject.get("Content-Type").getAsString();

                JsonObject endPointObject = jsonObject.getAsJsonObject("endpoint");

                // String protocol = endPointObject.get("protocol").getAsString();
                // String hostname = endPointObject.get("hostname").getAsString();
                String uri = endPointObject.get("uri").getAsString();

                return new Builder().publishURL(getURL(uri)).
                        publishKey(xLoadText).
                        contentType(contentType).
                        publishEnabled(shouldPublishFlag).
                        maxBatchSize(batchSize).
                        publishFrequency(publishFrequencyInSeconds * 1000).
                        configurationReloadFrequency(configReloadFrequencyInMinutes * 60 * 1000);
            }

            private String getURL(String uri)
            {
                //return protocol + "://" + hostname + uri;
                return BASE_URL + uri;
            }
        }
    }
}