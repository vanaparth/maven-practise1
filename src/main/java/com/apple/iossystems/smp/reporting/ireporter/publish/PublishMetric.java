package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;

/**
 * @author Toch
 */
class PublishMetric
{
    private static final PublishMetric REPORTS = getReportsPublishMetrics();
    private static final PublishMetric PAYMENT_REPORTS = getPaymentReportsPublishMetrics();

    private final Metric messagesSent;
    private final Metric recordsSent;
    private final Metric messagesFailed;
    private final Metric recordsFailed;

    private final Metric iReporterRecordsSent;
    private final Metric iReporterRecordsFailed;
    private final Metric iReporterRecordsPending;
    private final Metric iReporterRecordsLost;

    private final Metric auditRecordsSent;
    private final Metric auditRecordsFailed;

    private final Metric reportsConfigurationRequested;
    private final Metric reportsConfigurationChanged;
    private final Metric auditConfigurationRequested;
    private final Metric auditConfigurationChanged;

    private final Metric[] auditMetrics;

    private PublishMetric(Builder builder)
    {
        messagesSent = builder.messagesSent;
        recordsSent = builder.recordsSent;
        messagesFailed = builder.messagesFailed;
        recordsFailed = builder.recordsFailed;

        iReporterRecordsSent = builder.iReporterRecordsSent;
        iReporterRecordsFailed = builder.iReporterRecordsFailed;
        iReporterRecordsPending = builder.iReporterRecordsPending;
        iReporterRecordsLost = builder.iReporterRecordsLost;

        auditRecordsSent = builder.auditRecordsSent;
        auditRecordsFailed = builder.auditRecordsFailed;

        reportsConfigurationRequested = builder.reportsConfigurationRequested;
        reportsConfigurationChanged = builder.reportsConfigurationChanged;
        auditConfigurationRequested = builder.auditConfigurationRequested;
        auditConfigurationChanged = builder.auditConfigurationChanged;

        auditMetrics = builder.auditMetrics;
    }

    public static PublishMetric getReportsMetrics()
    {
        return REPORTS;
    }

    public static PublishMetric getPaymentReportsMetrics()
    {
        return PAYMENT_REPORTS;
    }

    public Metric getMessagesSentMetric()
    {
        return messagesSent;
    }

    public Metric getRecordsSentMetric()
    {
        return recordsSent;
    }

    public Metric getMessagesFailedMetric()
    {
        return messagesFailed;
    }

    public Metric getRecordsFailedMetric()
    {
        return recordsFailed;
    }

    public Metric getIReporterRecordsSent()
    {
        return iReporterRecordsSent;
    }

    public Metric getIReporterRecordsFailed()
    {
        return iReporterRecordsFailed;
    }

    public Metric getIReporterRecordsPending()
    {
        return iReporterRecordsPending;
    }

    public Metric getIReporterRecordsLost()
    {
        return iReporterRecordsLost;
    }

    public Metric getAuditRecordsSent()
    {
        return auditRecordsSent;
    }

    public Metric getAuditRecordsFailed()
    {
        return auditRecordsFailed;
    }

    public Metric getReportsConfigurationRequestedMetric()
    {
        return reportsConfigurationRequested;
    }

    public Metric getReportsConfigurationChangedMetric()
    {
        return reportsConfigurationChanged;
    }

    public Metric getAuditConfigurationRequestedMetric()
    {
        return auditConfigurationRequested;
    }

    public Metric getAuditConfigurationChangedMetric()
    {
        return auditConfigurationChanged;
    }

    public Metric[] getAuditMetrics()
    {
        return auditMetrics;
    }

    private static class Builder
    {
        private Metric messagesSent;
        private Metric recordsSent;
        private Metric messagesFailed;
        private Metric recordsFailed;

        private Metric iReporterRecordsSent;
        private Metric iReporterRecordsFailed;
        private Metric iReporterRecordsPending;
        private Metric iReporterRecordsLost;

        private Metric auditRecordsSent;
        private Metric auditRecordsFailed;

        private Metric reportsConfigurationRequested;
        private Metric reportsConfigurationChanged;
        private Metric auditConfigurationRequested;
        private Metric auditConfigurationChanged;

        private Metric[] auditMetrics;

        private Builder()
        {
        }

        private Builder messagesSent(Metric value)
        {
            messagesSent = value;
            return this;
        }

        private Builder recordsSent(Metric value)
        {
            recordsSent = value;
            return this;
        }

        private Builder messagesFailed(Metric value)
        {
            messagesFailed = value;
            return this;
        }

        private Builder recordsFailed(Metric value)
        {
            recordsFailed = value;
            return this;
        }

        private Builder iReporterRecordsSent(Metric value)
        {
            iReporterRecordsSent = value;
            return this;
        }

        private Builder iReporterRecordsFailed(Metric value)
        {
            iReporterRecordsFailed = value;
            return this;
        }

        private Builder iReporterRecordsPending(Metric value)
        {
            iReporterRecordsPending = value;
            return this;
        }

        private Builder iReporterRecordsLost(Metric value)
        {
            iReporterRecordsLost = value;
            return this;
        }

        private Builder auditRecordsSent(Metric value)
        {
            auditRecordsSent = value;
            return this;
        }

        private Builder auditRecordsFailed(Metric value)
        {
            auditRecordsFailed = value;
            return this;
        }

        private Builder reportsConfigurationRequested(Metric value)
        {
            reportsConfigurationRequested = value;
            return this;
        }

        private Builder reportsConfigurationChanged(Metric value)
        {
            reportsConfigurationChanged = value;
            return this;
        }

        private Builder auditConfigurationRequested(Metric value)
        {
            auditConfigurationRequested = value;
            return this;
        }

        private Builder auditConfigurationChanged(Metric value)
        {
            auditConfigurationChanged = value;
            return this;
        }

        private Builder auditMetrics(Metric[] value)
        {
            auditMetrics = value;
            return this;
        }

        private PublishMetric build()
        {
            return new PublishMetric(this);
        }
    }

    private static PublishMetric getReportsPublishMetrics()
    {
        return new Builder().messagesSent(Metric.REPORTS_MESSAGES_SENT).
                recordsSent(Metric.REPORTS_RECORDS_SENT).
                messagesFailed(Metric.REPORTS_MESSAGES_FAILED).
                recordsFailed(Metric.REPORTS_RECORDS_FAILED).
                iReporterRecordsSent(Metric.IREPORTER_REPORTS_RECORDS_SENT).
                iReporterRecordsFailed(Metric.IREPORTER_REPORTS_RECORDS_FAILED).
                iReporterRecordsPending(Metric.IREPORTER_REPORTS_RECORDS_PENDING).
                iReporterRecordsLost(Metric.IREPORTER_REPORTS_RECORDS_LOST).
                auditRecordsSent(Metric.AUDIT_RECORDS_SENT).
                auditRecordsFailed(Metric.AUDIT_RECORDS_FAILED).
                reportsConfigurationRequested(Metric.REPORTS_CONFIGURATION_REQUESTED).
                reportsConfigurationChanged(Metric.REPORTS_CONFIGURATION_CHANGED).
                auditConfigurationRequested(Metric.AUDIT_CONFIGURATION_REQUESTED).
                auditConfigurationChanged(Metric.AUDIT_CONFIGURATION_CHANGED).
                auditMetrics(new Metric[]{
                        Metric.IREPORTER_REPORTS_RECORDS_SENT,
                        Metric.IREPORTER_REPORTS_RECORDS_FAILED,
                        Metric.IREPORTER_REPORTS_RECORDS_PENDING,
                        Metric.IREPORTER_REPORTS_RECORDS_LOST
                }).build();
    }

    private static PublishMetric getPaymentReportsPublishMetrics()
    {
        return new Builder().messagesSent(Metric.PAYMENT_REPORTS_MESSAGES_SENT).
                recordsSent(Metric.PAYMENT_REPORTS_RECORDS_SENT).
                messagesFailed(Metric.PAYMENT_REPORTS_MESSAGES_FAILED).
                recordsFailed(Metric.PAYMENT_REPORTS_RECORDS_FAILED).
                iReporterRecordsSent(Metric.PAYMENT_IREPORTER_REPORTS_RECORDS_SENT).
                iReporterRecordsFailed(Metric.PAYMENT_IREPORTER_REPORTS_RECORDS_FAILED).
                iReporterRecordsPending(Metric.PAYMENT_IREPORTER_REPORTS_RECORDS_PENDING).
                iReporterRecordsLost(Metric.PAYMENT_IREPORTER_REPORTS_RECORDS_LOST).
                auditRecordsSent(Metric.PAYMENT_AUDIT_RECORDS_SENT).
                auditRecordsFailed(Metric.PAYMENT_AUDIT_RECORDS_FAILED).
                reportsConfigurationRequested(Metric.PAYMENT_REPORTS_CONFIGURATION_REQUESTED).
                reportsConfigurationChanged(Metric.PAYMENT_REPORTS_CONFIGURATION_CHANGED).
                auditConfigurationRequested(Metric.PAYMENT_AUDIT_CONFIGURATION_REQUESTED).
                auditConfigurationChanged(Metric.PAYMENT_AUDIT_CONFIGURATION_CHANGED).
                auditMetrics(new Metric[]{
                        Metric.PAYMENT_IREPORTER_REPORTS_RECORDS_SENT,
                        Metric.PAYMENT_IREPORTER_REPORTS_RECORDS_FAILED,
                        Metric.PAYMENT_IREPORTER_REPORTS_RECORDS_PENDING,
                        Metric.PAYMENT_IREPORTER_REPORTS_RECORDS_LOST
                }).build();
    }
}