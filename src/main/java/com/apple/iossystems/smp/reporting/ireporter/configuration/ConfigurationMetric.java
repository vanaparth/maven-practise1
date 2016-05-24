package com.apple.iossystems.smp.reporting.ireporter.configuration;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;

/**
 * @author Toch
 */
enum ConfigurationMetric
{
    REPORTS(Metric.REPORTS_CONFIGURATION_REQUESTED, Metric.REPORTS_CONFIGURATION_CHANGED),
    AUDIT(Metric.AUDIT_CONFIGURATION_REQUESTED, Metric.AUDIT_CONFIGURATION_CHANGED),
    PAYMENT_REPORTS(Metric.PAYMENT_REPORTS_CONFIGURATION_REQUESTED, Metric.PAYMENT_REPORTS_CONFIGURATION_CHANGED),
    PAYMENT_AUDIT(Metric.PAYMENT_AUDIT_CONFIGURATION_REQUESTED, Metric.PAYMENT_AUDIT_CONFIGURATION_CHANGED),
    LOYALTY_REPORTS(Metric.LOYALTY_REPORTS_CONFIGURATION_REQUESTED, Metric.LOYALTY_REPORTS_CONFIGURATION_CHANGED),
    LOYALTY_AUDIT(Metric.LOYALTY_AUDIT_CONFIGURATION_REQUESTED, Metric.LOYALTY_AUDIT_CONFIGURATION_CHANGED);

    private final Metric requestedMetric;
    private final Metric changedMetric;

    ConfigurationMetric(Metric requestedMetric, Metric changedMetric)
    {
        this.requestedMetric = requestedMetric;
        this.changedMetric = changedMetric;
    }

    Metric getRequestedMetric()
    {
        return requestedMetric;
    }

    Metric getChangedMetric()
    {
        return changedMetric;
    }
}