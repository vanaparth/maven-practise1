package com.apple.iossystems.smp.reporting.ireporter.configuration;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class IReporterConfiguration
{
    // URLs
    public static final String BASE_URL = "https://icloud4-e3.icloud.com";

    public static final String DEFAULT_REPORTS_CONFIGURATION_URL = BASE_URL + "/e3/rest/1/config/stockholm";

    public static final String DEFAULT_AUDIT_CONFIGURATION_URL = BASE_URL + "/e3/rest/1/config/stockholm_audit";

    public static final String DEFAULT_REPORTS_URL = BASE_URL + "/e3/rest/1/stockholm";

    public static final String DEFAULT_AUDIT_URL = BASE_URL + "/e3/rest/1/stockholm_audit";

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

    public enum Type
    {
        REPORTS(DEFAULT_REPORTS_CONFIGURATION_URL, DEFAULT_REPORTS_URL),
        AUDIT(DEFAULT_AUDIT_CONFIGURATION_URL, DEFAULT_AUDIT_URL);

        private String configurationURL;
        private String publishURL;

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

        public static IReporterConfiguration fromJson(String json)
        {
            ConfigurationResponse configurationResponse = new GsonBuilder().create().fromJson(json, ConfigurationResponse.class);

            Builder builder = Builder.getInstance();

            builder.publishURL(configurationResponse.endpoint.getURL()).
                    publishKey(configurationResponse.headers.xLoadText).
                    contentType(configurationResponse.headers.contentType).
                    publishEnabled(configurationResponse.shouldPublishFlag).
                    maxBatchSize(configurationResponse.batchSize).
                    publishFrequency(configurationResponse.publishFrequencyInSeconds * 1000).
                    configurationReloadFrequency(configurationResponse.configReloadFrequencyInMinutes * 60 * 1000);

            return builder.build();
        }

        private class ConfigurationResponse
        {
            private EndPoint endpoint;
            private String method;
            private Header headers;
            private boolean shouldPublishFlag;
            private int batchSize;
            private int publishFrequencyInSeconds;
            private int configReloadFrequencyInMinutes;

            private ConfigurationResponse()
            {
            }

            private class EndPoint
            {
                private String protocol;
                private String hostname;
                private String uri;

                private EndPoint()
                {
                }

                private String getURL()
                {
                    //return protocol + "://" + hostname + "/" + uri;
                    return BASE_URL + "/" + uri;
                }
            }

            private class Header
            {
                @SerializedName("X-LoadText")
                private String xLoadText;

                @SerializedName("Content-Type")
                private String contentType;

                private Header()
                {
                }
            }
        }
    }
}
