package com.apple.iossystems.smp.reporting.ireporter.configuration;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
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
    public static final String BASE_URL = ApplicationConfigurationManager.getIReporterURL();

    public static final String DEFAULT_REPORTS_CONFIGURATION_URL = BASE_URL + ApplicationConfigurationManager.getIReporterReportsConfigurationURL();

    public static final String DEFAULT_AUDIT_CONFIGURATION_URL = BASE_URL + ApplicationConfigurationManager.getIReporterAuditConfigurationURL();

    public static final String DEFAULT_REPORTS_URL = BASE_URL + ApplicationConfigurationManager.getIReporterReportsURL();

    public static final String DEFAULT_AUDIT_URL = BASE_URL + ApplicationConfigurationManager.getIReporterAuditURL();

    // Other configuration settings
    public static final String DEFAULT_PUBLISH_KEY = ApplicationConfigurationManager.getIReporterPublishKey();

    public static final String DEFAULT_CONTENT_TYPE = ApplicationConfigurationManager.getIReporterContentType();

    public static final boolean DEFAULT_PUBLISH_ENABLED = ApplicationConfigurationManager.getIReporterPublishEnable();

    public static final int DEFAULT_MAX_BATCH_SIZE = ApplicationConfigurationManager.getIReporterMaxBatchSize();

    // Publish frequency in milliseconds
    public static final int DEFAULT_PUBLISH_FREQUENCY = ApplicationConfigurationManager.getIReporterPublishFrequency();

    // Configuration reload frequency in milliseconds
    public static final int DEFAULT_CONFIGURATION_RELOAD_FREQUENCY = ApplicationConfigurationManager.getIReporterConfigurationReloadFrequency();


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
        AUDIT(DEFAULT_AUDIT_CONFIGURATION_URL, DEFAULT_AUDIT_URL);

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