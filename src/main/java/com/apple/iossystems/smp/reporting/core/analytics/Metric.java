package com.apple.iossystems.smp.reporting.core.analytics;

import org.apache.commons.lang.StringUtils;

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
    LOYALTY_IREPORTER_TIMING("kpi.smp.ireporter.timing.loyalty"),
    LOYALTY_REPORTS_CONFIGURATION_REQUESTED("kpi.ireporter.configuration.fetched.loyalty"),
    LOYALTY_REPORTS_CONFIGURATION_CHANGED("kpi.ireporter.configuration.changed.loyalty"),

    // Other
    PUBLISH_REPORTS_QUEUE("kpi.reporting.publish.reports.queue"),
    PUBLISH_PAYMENT_QUEUE("kpi.reporting.publish.payment.queue"),
    PUBLISH_LOYALTY_QUEUE("kpi.reporting.publish.loyalty.queue"),

    PUBLISH_REPORTS_QUEUE_FAILED("kpi.reporting.publish.reports.queue.failed"),
    PUBLISH_PAYMENT_QUEUE_FAILED("kpi.reporting.publish.payment.queue.failed"),
    PUBLISH_LOYALTY_QUEUE_FAILED("kpi.reporting.publish.loyalty.queue.failed"),

    PUBLISH_REPORTS_BACKLOG_QUEUE("kpi.reporting.publish.reports.backlog.queue"),
    PUBLISH_PAYMENT_BACKLOG_QUEUE("kpi.reporting.publish.payment.backlog.queue"),
    PUBLISH_LOYALTY_BACKLOG_QUEUE("kpi.reporting.publish.loyalty.backlog.queue"),

    PUBLISH_REPORTS_BACKLOG_QUEUE_FAILED("kpi.reporting.publish.reports.backlog.queue.failed"),
    PUBLISH_PAYMENT_BACKLOG_QUEUE_FAILED("kpi.reporting.publish.payment.backlog.queue.failed"),
    PUBLISH_LOYALTY_BACKLOG_QUEUE_FAILED("kpi.reporting.publish.loyalty.backlog.queue.failed"),

    CONSUME_REPORTS_QUEUE("kpi.reporting.consume.reports.queue"),
    CONSUME_PAYMENT_QUEUE("kpi.reporting.consume.payment.queue"),
    CONSUME_LOYALTY_QUEUE("kpi.reporting.consume.loyalty.queue"),

    CONSUME_BACKLOG_QUEUE("kpi.reporting.consume.backlog.queue"),
    CONSUME_BACKLOG_QUEUE_FAILED("kpi.reporting.consume.backlog.queue.failed"),

    HTTP_REQUEST_FAILED("kpi.reporting.http.request.failed");

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
        return StringUtils.isNotBlank(kpi);
    }
}