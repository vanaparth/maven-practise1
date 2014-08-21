package com.apple.iossystems.smp.reporting.core.analytics;

/**
 * @author Toch
 */
public enum Metric
{
    // Hubble IReporter Reports Metrics
    REPORTS_RECORDS_SENT("kpi.ireporter.transactions.published"),
    REPORTS_MESSAGES_SENT("kpi.ireporter.messages.published"),
    REPORTS_RECORDS_FAILED("kpi.ireporter.transactions.rejected"),
    REPORTS_MESSAGES_FAILED("kpi.ireporter.messages.rejected"),
    REPORTS_RECORDS_LOST("kpi.ireporter.transactions.lost"),
    REPORTS_RECORDS_PENDING("kpi.ireporter.transactions.pending"),
    REPORTS_CONFIGURATION_REQUESTED("kpi.ireporter.configuration.fetched"),
    REPORTS_CONFIGURATION_CHANGED("kpi.ireporter.configuration.changed"),
    // Hubble IReporter Audit Metrics
    AUDIT_RECORDS_SENT("kpi.ireporter.transactions.published.audit"),
    AUDIT_RECORDS_FAILED("kpi.ireporter.transactions.rejected.audit"),
    AUDIT_RECORDS_LOST("kpi.ireporter.transactions.lost.audit"),
    AUDIT_RECORDS_PENDING("kpi.ireporter.transactions.pending.audit"),
    AUDIT_CONFIGURATION_REQUESTED("kpi.ireporter.configuration.fetched.audit"),
    AUDIT_CONFIGURATION_CHANGED("kpi.ireporter.configuration.changed.audit"),
    // Other Hubble IReporter Metrics
    IREPORTER_UNREACHABLE("kpi.ireporter.unreachable"),
    // IReporter Reports Metrics sent to IReporter
    IREPORTER_REPORTS_RECORDS_SENT(""),
    IREPORTER_REPORTS_RECORDS_FAILED(""),
    IREPORTER_REPORTS_RECORDS_LOST(""),
    IREPORTER_REPORTS_RECORDS_PENDING("");

    private final String kpi;

    private Metric(String kpi)
    {
        this.kpi = kpi;
    }

    public String getKpi()
    {
        return kpi;
    }

    public boolean hasKpi()
    {
        return (!kpi.isEmpty());
    }
}
