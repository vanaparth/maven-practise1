package com.apple.iossystems.smp.reporting.hubble.analytics;

/**
 * @author Toch
 */
public class HubbleAnalytics
{
    private HubbleAnalytics()
    {
    }

    public static HubbleAnalytics getInstance()
    {
        return new HubbleAnalytics();
    }
}
