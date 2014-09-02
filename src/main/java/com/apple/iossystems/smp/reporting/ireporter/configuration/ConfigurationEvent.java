package com.apple.iossystems.smp.reporting.ireporter.configuration;

/**
 * @author Toch
 */
public class ConfigurationEvent
{
    private final boolean updated;
    private final boolean modified;

    private ConfigurationEvent(boolean updated, boolean modified)
    {
        this.updated = updated;
        this.modified = modified;
    }

    public static ConfigurationEvent getInstance(boolean updated, boolean modified)
    {
        return new ConfigurationEvent(updated, modified);
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