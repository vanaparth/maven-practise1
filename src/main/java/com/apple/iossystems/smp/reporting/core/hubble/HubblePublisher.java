package com.apple.iossystems.smp.reporting.core.hubble;

import com.apple.cds.analysis.OperationalAnalytics;
import com.apple.cds.keystone.core.OperationalAnalyticsManager;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class HubblePublisher
{
    private static final Logger LOGGER = Logger.getLogger(HubblePublisher.class);

    private OperationalAnalytics operationalAnalytics = OperationalAnalyticsManager.getInstance().getOperationalAnalytics();

    private HubblePublisher()
    {
    }

    public static HubblePublisher getInstance()
    {
        return new HubblePublisher();
    }

    private void incrementCountForEvent(String kpi)
    {
        operationalAnalytics.incrementCountForEvent(kpi);
    }

    public void incrementCountForEvent(Metric metric, int count)
    {
        if (metric.hasKpi())
        {
            String kpi = metric.getKpi();

            for (int i = 0; i < count; i++)
            {
                incrementCountForEvent(kpi);
            }

            LOGGER.info("Published to Hubble " + kpi + ": " + count);
        }
    }

    public void incrementCountForEvent(Metric metric)
    {
        incrementCountForEvent(metric, 1);
    }

    public void logTimingForEvent(Metric metric, long time)
    {
        if (metric.hasKpi())
        {
            String kpi = metric.getKpi();

            operationalAnalytics.logTimingForEvent(time, kpi);

            LOGGER.info("Published to Hubble " + kpi + ": " + time);
        }
    }
}