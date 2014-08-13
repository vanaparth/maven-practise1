package com.apple.iossystems.smp.reporting.core.analytics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Toch
 */
public class Analytics
{
    private static final Analytics INSTANCE = new Analytics();

    private Map<Metric, String> statistics = new ConcurrentHashMap<Metric, String>();

    private Analytics()
    {
    }

    public static Analytics getInstance()
    {
        return INSTANCE;
    }

    public void setMetric(Metric key, String value)
    {
        statistics.put(key, value);
    }

    public String getMetric(Metric key)
    {
        return statistics.get(key);
    }
}
