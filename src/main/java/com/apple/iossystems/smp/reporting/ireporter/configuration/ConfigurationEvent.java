package com.apple.iossystems.smp.reporting.ireporter.configuration;

/**
 * @author Toch
 */
public class ConfigurationEvent
{
    private final IReporterConfiguration configuration;
    private final boolean updated;
    private final boolean modified;

    private ConfigurationEvent(IReporterConfiguration configuration, boolean updated, boolean modified)
    {
        this.configuration = configuration;
        this.updated = updated;
        this.modified = modified;
    }

    public static ConfigurationEvent getInstance(IReporterConfiguration configuration, boolean updated, boolean modified)
    {
        return new ConfigurationEvent(configuration, updated, modified);
    }

    public IReporterConfiguration getConfiguration()
    {
        return configuration;
    }

    public boolean isUpdated()
    {
        return updated;
    }

    public boolean isModified()
    {
        return modified;
    }
}
