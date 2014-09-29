package com.apple.iossystems.smp.reporting.ireporter.configuration;

import com.apple.iossystems.smp.reporting.core.hubble.HubbleAnalytics;
import com.apple.iossystems.smp.reporting.core.timer.Timer;

/**
 * @author Toch
 */
public abstract class IReporterConfigurationService
{
    private IReporterConfiguration configuration;

    private long timestamp;

    IReporterConfigurationService()
    {
        updateConfiguration();
    }

    abstract IReporterConfiguration loadConfiguration();

    abstract ConfigurationMetric getConfigurationMetric();

    private void updateConfiguration()
    {
        IReporterConfiguration lastConfiguration = configuration;

        configuration = loadConfiguration();

        timestamp = System.currentTimeMillis();

        logConfigurationEvent(configuration, lastConfiguration);
    }

    private boolean configurationExpired()
    {
        return Timer.delayExpired(timestamp, configuration.getConfigurationReloadFrequency());
    }

    private void reloadConfiguration()
    {
        if (configurationExpired())
        {
            updateConfiguration();
        }
    }

    public final IReporterConfiguration getConfiguration()
    {
        reloadConfiguration();

        return configuration;
    }

    private void logConfigurationEvent(IReporterConfiguration configuration1, IReporterConfiguration configuration2)
    {
        ConfigurationMetric configurationMetric = getConfigurationMetric();

        HubbleAnalytics.incrementCountForEvent(configurationMetric.getRequestedMetric());

        if (((configuration1 != null) && (configuration2 == null)) ||
                ((configuration1 == null) && (configuration2 != null)) ||
                ((configuration1 != null) && (!configuration1.isEquals(configuration2))))
        {
            HubbleAnalytics.incrementCountForEvent(configurationMetric.getChangedMetric());
        }
    }
}