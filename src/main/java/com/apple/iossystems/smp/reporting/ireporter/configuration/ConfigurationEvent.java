package com.apple.iossystems.smp.reporting.ireporter.configuration;

/**
 * @author Toch
 */
public enum ConfigurationEvent
{
    NONE(false, false),
    UPDATED(true, false),
    MODIFIED(true, true);

    private final boolean updated;
    private final boolean modified;

    private ConfigurationEvent(boolean updated, boolean modified)
    {
        this.updated = updated;
        this.modified = modified;
    }

    public static ConfigurationEvent getInstance(boolean updated, boolean modified)
    {
        if (!updated && !modified)
        {
            return NONE;
        }
        else if (updated && !modified)
        {
            return UPDATED;
        }
        else
        {
            return MODIFIED;
        }
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