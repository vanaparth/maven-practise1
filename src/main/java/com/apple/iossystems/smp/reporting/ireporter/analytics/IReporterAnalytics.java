package com.apple.iossystems.smp.reporting.ireporter.analytics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Toch
 */
public class IReporterAnalytics
{
    private Map<IReporterMetric, Object> statistics = new ConcurrentHashMap<IReporterMetric, Object>();

    private IReporterAnalytics()
    {
    }

    public static IReporterAnalytics getInstance()
    {
        return new IReporterAnalytics();
    }

    public void updateMetricStatistics(IReporterMetric key, Object value)
    {
        statistics.put(key, value);
    }

    public Object getMetricStatistics(IReporterMetric key)
    {
        return statistics.get(key);
    }
}
