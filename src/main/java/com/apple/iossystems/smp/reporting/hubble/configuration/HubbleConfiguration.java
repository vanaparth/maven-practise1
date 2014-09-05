package com.apple.iossystems.smp.reporting.hubble.configuration;

/**
 * @author Toch
 */
public class HubbleConfiguration
{
    private HubbleConfiguration()
    {
    }

    public static HubbleConfiguration getInstance()
    {
        return new HubbleConfiguration();
    }
}