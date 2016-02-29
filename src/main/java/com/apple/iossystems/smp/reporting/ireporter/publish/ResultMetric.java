package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;

/**
 * @author Toch
 */
public class ResultMetric
{
    private final Metric successMetric;
    private final Metric failedMetric;

    public ResultMetric(Metric successMetric, Metric failedMetric)
    {
        this.successMetric = successMetric;
        this.failedMetric = failedMetric;
    }

    public Metric getSuccessMetric()
    {
        return successMetric;
    }

    public Metric getFailedMetric()
    {
        return failedMetric;
    }
}