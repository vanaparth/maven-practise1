package com.apple.iossystems.smp.reporting.core.analytics;

/**
 * @author Toch
 */
public enum Metric
{
    // IReporter Reports Metrics for Hubble
    REPORTS_RECORDS_SENT("kpi.ireporter.transactions.published"),
    REPORTS_MESSAGES_SENT("kpi.ireporter.messages.published"),
    REPORTS_RECORDS_FAILED("kpi.ireporter.transactions.rejected"),
    REPORTS_MESSAGES_FAILED("kpi.ireporter.messages.rejected"),
    REPORTS_CONFIGURATION_REQUESTED("kpi.ireporter.configuration.fetched"),
    REPORTS_CONFIGURATION_CHANGED("kpi.ireporter.configuration.changed"),
    // IReporter Audit Metrics for Hubble
    AUDIT_RECORDS_SENT("kpi.ireporter.transactions.published.audit"),
    AUDIT_RECORDS_FAILED("kpi.ireporter.transactions.rejected.audit"),
    AUDIT_CONFIGURATION_REQUESTED("kpi.ireporter.configuration.fetched.audit"),
    AUDIT_CONFIGURATION_CHANGED("kpi.ireporter.configuration.changed.audit"),
    // IReporter Reports Metrics for IReporter
    IREPORTER_REPORTS_RECORDS_SENT(""),
    IREPORTER_REPORTS_RECORDS_FAILED(""),
    IREPORTER_REPORTS_RECORDS_PENDING(""),
    IREPORTER_REPORTS_RECORDS_LOST(""),

    // Oslo IReporter Reports Metrics for Hubble
    PAYMENT_REPORTS_RECORDS_SENT("kpi.ireporter.transactions.published.oslo"),
    PAYMENT_REPORTS_MESSAGES_SENT("kpi.ireporter.messages.published.oslo"),
    PAYMENT_REPORTS_RECORDS_FAILED("kpi.ireporter.transactions.rejected.oslo"),
    PAYMENT_REPORTS_MESSAGES_FAILED("kpi.ireporter.messages.rejected.oslo"),
    PAYMENT_REPORTS_CONFIGURATION_REQUESTED("kpi.ireporter.configuration.fetched.oslo"),
    PAYMENT_REPORTS_CONFIGURATION_CHANGED("kpi.ireporter.configuration.changed.oslo"),
    // Oslo IReporter Audit Metrics for Hubble
    PAYMENT_AUDIT_RECORDS_SENT("kpi.ireporter.transactions.published.audit.oslo"),
    PAYMENT_AUDIT_RECORDS_FAILED("kpi.ireporter.transactions.rejected.audit.oslo"),
    PAYMENT_AUDIT_CONFIGURATION_REQUESTED("kpi.ireporter.configuration.fetched.audit.oslo"),
    PAYMENT_AUDIT_CONFIGURATION_CHANGED("kpi.ireporter.configuration.changed.audit.oslo"),
    // Oslo IReporter Reports Metrics for IReporter
    PAYMENT_IREPORTER_REPORTS_RECORDS_SENT(""),
    PAYMENT_IREPORTER_REPORTS_RECORDS_FAILED(""),
    PAYMENT_IREPORTER_REPORTS_RECORDS_PENDING(""),
    PAYMENT_IREPORTER_REPORTS_RECORDS_LOST(""),

    // Stockholm/Oslo Performance Monitoring
    IREPORTER_TIMING("kpi.smp.ireporter.timing"),
    PAYMENT_IREPORTER_TIMING("kpi.smp.ireporter.timing.oslo"),

    // Loyalty/MerchantServices
    LOYALTY_REPORTS_RECORDS_SENT("kpi.ireporter.transactions.published.loyalty"),
    LOYALTY_REPORTS_MESSAGES_SENT("kpi.ireporter.messages.published.loyalty"),
    LOYALTY_REPORTS_MESSAGES_FAILED("kpi.ireporter.messages.rejected.loyalty"),
    LOYALTY_REPORTS_RECORDS_FAILED("kpi.ireporter.transactions.rejected.loyalty"),
    LOYALTY_AUDIT_RECORDS_SENT("kpi.ireporter.transactions.published.audit.loyalty"),
    LOYALTY_AUDIT_RECORDS_FAILED("kpi.ireporter.transactions.rejected.audit.loyalty"),
    LOYALTY_AUDIT_CONFIGURATION_REQUESTED("kpi.ireporter.configuration.fetched.audit.loyalty"),
    LOYALTY_AUDIT_CONFIGURATION_CHANGED("kpi.ireporter.configuration.changed.audit.loyalty"),
    LOYALTY_IREPORTER_REPORTS_RECORDS_SENT(""),
    LOYALTY_IREPORTER_REPORTS_RECORDS_FAILED(""),
    LOYALTY_IREPORTER_REPORTS_RECORDS_PENDING(""),
    LOYALTY_IREPORTER_REPORTS_RECORDS_LOST(""),
    LOYALTY_IREPORTER_TIMING("kpi.smp.ireporter.timing.loyalty"),
    LOYALTY_REPORTS_CONFIGURATION_REQUESTED("kpi.ireporter.configuration.fetched.loyalty"),
    LOYALTY_REPORTS_CONFIGURATION_CHANGED("kpi.ireporter.configuration.changed.loyalty");

    private final String kpi;

    Metric(String kpi)
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