package com.apple.iossystems.smp.reporting.hubble.analytics;

import com.apple.cds.analysis.OperationalAnalytics;
import com.apple.cds.keystone.core.OperationalAnalyticsManager;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;

/**
 * @author Toch
 */
public class HubbleAnalytics
{
    private static final OperationalAnalytics OPERATIONAL_ANALYTICS = OperationalAnalyticsManager.getInstance().getOperationalAnalytics();

    private HubbleAnalytics()
    {
    }

    public static void log(Metric metric, long value)
    {
        if (metric.hasKpi())
        {
            log(metric.getKpi(), value);
        }
    }

    public static void log(String kpi, long value)
    {
        OPERATIONAL_ANALYTICS.logTimingForEvent(value, kpi);
    }

    public static void incrementCountForEvent(Metric metric)
    {
        if (metric.hasKpi())
        {
            incrementCountForEvent(metric.getKpi());
        }
    }

    public static void incrementCountForEvent(String kpi)
    {
        OPERATIONAL_ANALYTICS.incrementCountForEvent(kpi);
    }
}