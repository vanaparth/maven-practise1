package com.apple.iossystems.smp.reporting.core.analytics;

import com.apple.iossystems.smp.reporting.core.util.ValidValue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Toch
 */
public class Statistics
{
    private Map<Metric, String> data = new ConcurrentHashMap<>();

    private Statistics()
    {
    }

    public static Statistics getInstance()
    {
        return new Statistics();
    }

    private void set(Metric key, String value)
    {
        data.put(key, value);
    }

    private void clear(Metric metric)
    {
        set(metric, "0");
    }

    public void clear(Metric[] metrics)
    {
        for (Metric metric : metrics)
        {
            clear(metric);
        }
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
}