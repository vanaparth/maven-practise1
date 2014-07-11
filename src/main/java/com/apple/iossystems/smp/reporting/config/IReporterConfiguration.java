package com.apple.iossystems.smp.reporting.config;

import com.apple.iossystems.smp.reporting.util.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class IReporterConfiguration implements BaseConfiguration
{
    private static final String DEFAULT_CONFIGURATION_URL = "https://e3.icloud.com/e3/rest/1/config/stockholm";

    private static final String DEFAULT_PUBLISH_URL = "https://e3.icloud.com/e3/rest/1/stockholm";

    private static final String DEFAULT_PUBLISH_KEY = "QWERTYUIOPASDF12";

    private static final String DEFAULT_CONTENT_TYPE = "application/json";

    private static final boolean DEFAULT_PUBLISH_ENABLED = true;

    public static final int DEFAULT_MAX_BATCH_SIZE = 100;

    // Default publish frequency in milliseconds
    private static final int DEFAULT_PUBLISH_FREQUENCY = 2 * 60 * 1000;

    // Default configuration reload frequency in milliseconds
    private static final int DEFAULT_CONFIGURATION_RELOAD_FREQUENCY = 60 * 60 * 1000;

    private static final Gson GSON = new GsonBuilder().create();

    /**
     * This is given to us directly
     */
    private static final String CONFIGURATION_URL = DEFAULT_CONFIGURATION_URL;

    /**
     * Configuration specific values
     */
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

    public static String getConfigurationURL()
    {
        return CONFIGURATION_URL;
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

    public Map<String, String> getHeadersForReports()
    {
        Map<String, String> headers = new HashMap<String, String>();

        headers.put("X-LoadText", getPublishKey());
        headers.put("Content-Type", getContentType());
        headers.put("METHOD", "POST");

        return headers;
    }

    public static class Builder
    {
        /**
         * Set defaults ?
         */
        private String publishURL = DEFAULT_PUBLISH_URL;

        private String publishKey = DEFAULT_PUBLISH_KEY;

        private String contentType = DEFAULT_CONTENT_TYPE;

        private boolean publishEnabled = DEFAULT_PUBLISH_ENABLED;

        private int maxBatchSize = DEFAULT_MAX_BATCH_SIZE;

        private int publishFrequency = DEFAULT_PUBLISH_FREQUENCY;

        private int configurationReloadFrequency = DEFAULT_CONFIGURATION_RELOAD_FREQUENCY;

        private Builder()
        {
        }

        public static Builder getInstance()
        {
            return new Builder();
        }

        public Builder publishURL(String val)
        {
            publishURL = val;
            return this;
        }

        public Builder publishKey(String val)
        {
            publishKey = val;
            return this;
        }

        public Builder contentType(String val)
        {
            contentType = val;
            return this;
        }

        public Builder publishEnabled(boolean val)
        {
            publishEnabled = val;
            return this;
        }

        public Builder maxBatchSize(int val)
        {
            maxBatchSize = val;
            return this;
        }

        public Builder publishFrequency(int val)
        {
            publishFrequency = val;
            return this;
        }

        public Builder configurationReloadFrequency(int val)
        {
            configurationReloadFrequency = val;
            return this;
        }

        public IReporterConfiguration build()
        {
            validate();
            return new IReporterConfiguration(this);
        }

        // TODO: check for non-initialized primitive variables e.g. int, boolean
        private void validate()
        {
            publishURL = (String) Utils.getValueWithDefault(publishURL, DEFAULT_PUBLISH_URL);
            publishKey = (String) Utils.getValueWithDefault(publishKey, DEFAULT_PUBLISH_KEY);
            contentType = (String) Utils.getValueWithDefault(contentType, DEFAULT_CONTENT_TYPE);
            publishEnabled = (Boolean) Utils.getValueWithDefault(publishEnabled, DEFAULT_PUBLISH_ENABLED);
            maxBatchSize = (Integer) Utils.getValueWithDefault(maxBatchSize, DEFAULT_MAX_BATCH_SIZE);
            publishFrequency = (Integer) Utils.getValueWithDefault(publishFrequency, DEFAULT_PUBLISH_FREQUENCY);
            configurationReloadFrequency = (Integer) Utils.getValueWithDefault(configurationReloadFrequency, DEFAULT_CONFIGURATION_RELOAD_FREQUENCY);
        }


        public static IReporterConfiguration fromJSON(String json)
        {
            IReporterConfigurationJSON config = GSON.fromJson(json, IReporterConfigurationJSON.class);

            Builder builder = Builder.getInstance();

            builder.publishURL(config.endpoint.getURL()).
                    publishKey(config.headers.xLoadText).
                    contentType(config.headers.contentType).
                    publishEnabled(config.shouldPublishFlag).
                    maxBatchSize(config.batchSize).
                    publishFrequency(config.publishFrequencyInSeconds * 1000).
                    configurationReloadFrequency(config.configReloadFrequencyInMinutes * 60 * 1000);

            return builder.build();
        }

        private class IReporterConfigurationJSON
        {
            // Set default values
            private EndPoint endpoint;
            private String method = "POST";
            private Header headers;
            private boolean shouldPublishFlag = DEFAULT_PUBLISH_ENABLED;
            private int batchSize = DEFAULT_MAX_BATCH_SIZE;
            private int publishFrequencyInSeconds = DEFAULT_PUBLISH_FREQUENCY / 1000;
            private int configReloadFrequencyInMinutes = DEFAULT_CONFIGURATION_RELOAD_FREQUENCY / (60 * 1000);

            private IReporterConfigurationJSON()
            {
            }

            private class EndPoint
            {
                // Set default values
                private String protocol = "https";
                private String hostname = "e3.icloud.com";
                private String uri = "/e3/rest/1/stockholm";

                // GSON needs a default constructor?
                private EndPoint()
                {
                }

                private String getURL()
                {
                    return protocol + "://" + hostname + "/" + uri;
                }
            }

            private class Header
            {
                @SerializedName("X-LoadText")
                private String xLoadText = DEFAULT_PUBLISH_KEY;

                @SerializedName("Content-Type")
                private String contentType = DEFAULT_CONTENT_TYPE;

                // GSON needs a default constructor?
                private Header()
                {
                }
            }
        }
    }
}
