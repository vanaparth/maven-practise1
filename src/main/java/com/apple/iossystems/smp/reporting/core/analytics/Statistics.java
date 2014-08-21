package com.apple.iossystems.smp.reporting.core.analytics;

import com.apple.iossystems.smp.reporting.core.util.ValidValue;
import com.apple.iossystems.smp.reporting.hubble.analytics.HubbleAnalytics;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Toch
 */
public class Statistics
{
    private Map<Metric, String> data = new ConcurrentHashMap<Metric, String>();

    private Statistics()
    {
    }

    public static Statistics getInstance()
    {
        return new Statistics();
    }

    public void set(Metric key, String value)
    {
        data.put(key, value);
    }

    public String get(Metric key)
    {
        return data.get(key);
    }

    public void increment(Metric key)
    {
        increment(key, 1);
    }

    public void increment(Metric key, int amount)
    {
        int value = getIntValue(key);

        data.put(key, String.valueOf(value + amount));
    }

    public int getIntValue(Metric key)
    {
        return ValidValue.getIntValueWithDefault(data.get(key), 0);
    }

    public long getLongValue(Metric key)
    {
        return ValidValue.getLongValueWithDefault(data.get(key), 0);
    }

    public void logToHubble(Metric metric)
    {
        if (metric.hasKpi())
        {
            HubbleAnalytics.log(metric.getKpi(), getLongValue(metric));
        }
    }

    public void clear(Metric metric)
    {
        set(metric, "0");
    }

    public void logToHubble(Metric[] metrics)
    {
        for (Metric metric : metrics)
        {
            logToHubble(metric);
        }
    }

    public void clear(Metric[] metrics)
    {
        for (Metric metric : metrics)
        {
            clear(metric);
        }
    }
}
