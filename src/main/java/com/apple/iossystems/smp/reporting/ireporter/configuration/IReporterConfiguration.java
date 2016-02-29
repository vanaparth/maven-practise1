package com.apple.iossystems.smp.reporting.ireporter.configuration;

import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class IReporterConfiguration
{
    private static final String BASE_URL = ApplicationConfiguration.getIReporterUrl();

    private static final String DEFAULT_REPORTS_CONFIGURATION_URL = BASE_URL + "/e3/rest/1/config/stockholm";

    private static final String DEFAULT_AUDIT_CONFIGURATION_URL = BASE_URL + "/e3/rest/1/config/stockholm_audit";

    private static final String DEFAULT_PAYMENT_REPORTS_CONFIGURATION_URL = BASE_URL + "/e3/rest/1/config/oslo";

    private static final String DEFAULT_PAYMENT_AUDIT_CONFIGURATION_URL = BASE_URL + "/e3/rest/1/config/oslo_audit";

    private static final String DEFAULT_REPORTS_URL = BASE_URL + "/e3/rest/1/stockholm";

    private static final String DEFAULT_AUDIT_URL = BASE_URL + "/e3/rest/1/stockholm_audit";

    private static final String DEFAULT_PAYMENT_REPORTS_URL = BASE_URL + "/e3/rest/1/oslo";

    private static final String DEFAULT_PAYMENT_AUDIT_URL = BASE_URL + "/e3/rest/1/oslo_audit";

    private static final String DEFAULT_LOYALTY_REPORTS_CONFIGURATION_URL = BASE_URL + "/e3/rest/1/config/vas";

    private static final String DEFAULT_LOYALTY_REPORTS_URL = BASE_URL + "/e3/rest/1/vas";

    private static final String DEFAULT_LOYALTY_AUDIT_CONFIGURATION_URL = BASE_URL + "/e3/rest/1/config/vas_audit";

    private static final String DEFAULT_LOYALTY_AUDIT_URL = BASE_URL + "/e3/rest/1/vas_audit";

    private static final String DEFAULT_PUBLISH_KEY = "QWERTYUIOPASDF12";

    private static final String DEFAULT_CONTENT_TYPE = "application/json";

    private static final boolean DEFAULT_PUBLISH_ENABLED = true;

    private static final int DEFAULT_MAX_BATCH_SIZE = 100;

    private static final int DEFAULT_PUBLISH_FREQUENCY = 60 * 1000;

    private static final int DEFAULT_CONFIGURATION_RELOAD_FREQUENCY = 60 * 60 * 1000;

    private final String protocol;
    private final String hostname;
    private final String uri;
    private final String publishUrl;
    private final String publishKey;
    private final String contentType;
    private final boolean publishEnabled;
    private final int maxBatchSize;
    private final int publishFrequency;
    private final int configurationReloadFrequency;

    private IReporterConfiguration(Builder builder)
    {
        protocol = builder.protocol;
        hostname = builder.hostname;
        uri = builder.uri;
        publishUrl = builder.publishUrl;
        publishKey = builder.publishKey;
        contentType = builder.contentType;
        publishEnabled = builder.publishEnabled;
        maxBatchSize = builder.maxBatchSize;
        publishFrequency = builder.publishFrequency;
        configurationReloadFrequency = builder.configurationReloadFrequency;
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }

    public String getProtocol()
    {
        return protocol;
    }

    public String getHostname()
    {
        return hostname;
    }

    public String getUri()
    {
        return uri;
    }

    public String getPublishUrl()
    {
        return publishUrl;
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
        Map<String, String> headers = new HashMap<>();

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
        return ((configuration != null) &&
                (publishUrl.equalsIgnoreCase(configuration.publishUrl)) &&
                (publishKey.equalsIgnoreCase(configuration.publishKey)) &&
                (contentType.equalsIgnoreCase(configuration.contentType)) &&
                (publishEnabled == configuration.publishEnabled) &&
                (maxBatchSize == configuration.maxBatchSize) &&
                (publishFrequency == configuration.publishFrequency) &&
                (configurationReloadFrequency == configuration.configurationReloadFrequency));
    }

    public static IReporterConfiguration getConfiguration(IReporterConfiguration.Type configurationType, String json)
    {
        IReporterConfiguration configuration = null;

        try
        {
            configuration = GsonBuilderFactory.getInstance().fromJson(json, IReporterConfiguration.class);
        }
        catch (Exception e)
        {
            Logger.getLogger(IReporterConfiguration.class).error(e);
        }

        return (configuration != null) ? configuration : getDefaultConfiguration(configurationType);
    }

    public static IReporterConfiguration getDefaultConfiguration(Type configurationType)
    {
        return new Builder().publishUrl(configurationType.getPublishUrl()).
                publishKey(DEFAULT_PUBLISH_KEY).
                contentType(DEFAULT_CONTENT_TYPE).
                publishEnabled(DEFAULT_PUBLISH_ENABLED).
                maxBatchSize(DEFAULT_MAX_BATCH_SIZE).
                publishFrequency(DEFAULT_PUBLISH_FREQUENCY).
                configurationReloadFrequency(DEFAULT_CONFIGURATION_RELOAD_FREQUENCY).build();
    }

    public enum Type
    {
        REPORTS(DEFAULT_REPORTS_CONFIGURATION_URL, DEFAULT_REPORTS_URL),
        AUDIT(DEFAULT_AUDIT_CONFIGURATION_URL, DEFAULT_AUDIT_URL),
        PAYMENT_REPORTS(DEFAULT_PAYMENT_REPORTS_CONFIGURATION_URL, DEFAULT_PAYMENT_REPORTS_URL),
        PAYMENT_AUDIT(DEFAULT_PAYMENT_AUDIT_CONFIGURATION_URL, DEFAULT_PAYMENT_AUDIT_URL),
        LOYALTY_REPORTS(DEFAULT_LOYALTY_REPORTS_CONFIGURATION_URL, DEFAULT_LOYALTY_REPORTS_URL),
        LOYALTY_AUDIT(DEFAULT_LOYALTY_AUDIT_CONFIGURATION_URL, DEFAULT_LOYALTY_AUDIT_URL);

        private final String configurationUrl;
        private final String publishUrl;

        Type(String configurationUrl, String publishUrl)
        {
            this.configurationUrl = configurationUrl;
            this.publishUrl = publishUrl;
        }

        public String getConfigurationUrl()
        {
            return configurationUrl;
        }

        public String getPublishUrl()
        {
            return publishUrl;
        }
    }

    public static class Builder
    {
        private String protocol;
        private String hostname;
        private String uri;
        private String publishUrl;
        private String publishKey;
        private String contentType;
        private boolean publishEnabled;
        private int maxBatchSize;
        private int publishFrequency;
        private int configurationReloadFrequency;

        private Builder()
        {
        }

        public Builder protocol(String value)
        {
            protocol = value;
            return this;
        }

        public Builder hostname(String value)
        {
            hostname = value;
            return this;
        }

        public Builder uri(String value)
        {
            uri = value;
            return this;
        }

        public Builder publishUrl(String value)
        {
            publishUrl = value;
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

        public IReporterConfiguration build()
        {
            completeBuild();

            validate();

            return new IReporterConfiguration(this);
        }

        public void completeBuild()
        {
            if (publishUrl == null)
            {
                publishUrl = getUrl(protocol, hostname, uri);
            }
        }

        private void validate()
        {
            int defaultMinBatchSize = 1;
            int defaultMaxBatchSize = 100;
            int minPublishFrequency = 60 * 1000;
            int maxPublishFrequency = 60 * 60 * 1000;
            int minConfigurationReloadFrequency = 60 * 1000;
            int maxConfigurationReloadFrequency = 60 * 60 * 1000;

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

        private String getUrl(String protocol, String hostname, String uri)
        {
            if (protocol == null)
            {
                protocol = "https";
            }

            if (uri == null)
            {
                uri = "";
            }

            return protocol + "://" + hostname + uri;
        }
    }
}