package com.apple.iossystems.smp.reporting.hubble.analytics;

import com.apple.cds.analysis.OperationalAnalytics;
import com.apple.cds.keystone.core.OperationalAnalyticsManager;

/**
 * @author Toch
 */
public class HubbleAnalytics
{
    private static final OperationalAnalytics OPERATIONAL_ANALYTICS = OperationalAnalyticsManager.getInstance().getOperationalAnalytics();

    private HubbleAnalytics()
    {
    }

    public static void log(String kpi, long value)
    {
        OPERATIONAL_ANALYTICS.logTimingForEvent(value, kpi);
    }
}
