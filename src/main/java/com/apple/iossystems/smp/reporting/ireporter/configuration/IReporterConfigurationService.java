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

    private void reloadConfiguration()
    {
        if (configurationExpired())
        {
            updateConfiguration();
        }
    }

    private boolean configurationExpired()
    {
        return Timer.delayExpired(timestamp, configuration.getConfigurationReloadFrequency());
    }

    public final IReporterConfiguration getConfiguration()
    {
        reloadConfiguration();

        return configuration;
    }
}
