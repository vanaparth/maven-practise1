package com.apple.iossystems.smp.reporting.ireporter.configuration;

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

    private void updateConfiguration()
    {
        configuration = loadConfiguration();

        timestamp = System.currentTimeMillis();
    }

    private boolean configurationExpired()
    {
        return Timer.delayExpired(timestamp, configuration.getConfigurationReloadFrequency());
    }

    private boolean reloadConfiguration()
    {
        if (configurationExpired())
        {
            updateConfiguration();

            return true;
        }

        return false;
    }

    public final IReporterConfiguration getConfiguration()
    {
        reloadConfiguration();

        return configuration;
    }

    public final ConfigurationEvent getConfigurationEvent()
    {
        IReporterConfiguration lastConfiguration = configuration;

        boolean updated = reloadConfiguration();

        return ConfigurationEvent.getInstance(updated, !configuration.isEquals(lastConfiguration));
    }
}