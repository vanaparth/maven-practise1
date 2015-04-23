package com.apple.iossystems.smp.reporting.core.hubble;

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

    private static void incrementCountForEvent(String kpi)
    {
        OPERATIONAL_ANALYTICS.incrementCountForEvent(kpi);
    }

    public static void incrementCountForEvent(Metric metric, int count)
    {
        if (metric.hasKpi())
        {
            String kpi = metric.getKpi();

            for (int i = 0; i < count; i++)
            {
                incrementCountForEvent(kpi);
            }
        }
    }

    public static void incrementCountForEvent(Metric metric)
    {
        incrementCountForEvent(metric, 1);
    }

    public static void logTimingForEvent(Metric metric, long time)
    {
        if (metric.hasKpi())
        {
            String kpi = metric.getKpi();

            OPERATIONAL_ANALYTICS.logTimingForEvent(time, kpi);
        }
    }
}