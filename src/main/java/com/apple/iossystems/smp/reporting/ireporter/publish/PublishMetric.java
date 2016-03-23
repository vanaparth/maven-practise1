package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;

/**
 * @author Toch
 */
class PublishMetric
{
    private static final PublishMetric REPORTS = getReportsPublishMetrics();
    private static final PublishMetric PAYMENT_REPORTS = getPaymentReportsPublishMetrics();
    private static final PublishMetric LOYALTY_REPORTS = getLoyaltyReportsPublishMetrics();

    private final Metric messagesSent;
    private final Metric recordsSent;
    private final Metric messagesFailed;
    private final Metric recordsFailed;
    private final Metric iReporterRecordsSent;
    private final Metric iReporterRecordsFailed;
    private final Metric auditRecordsSent;
    private final Metric auditRecordsFailed;
    private final Metric iReporterTiming;
    private final Metric[] auditMetrics;

    private PublishMetric(Builder builder)
    {
        messagesSent = builder.messagesSent;
        recordsSent = builder.recordsSent;
        messagesFailed = builder.messagesFailed;
        recordsFailed = builder.recordsFailed;
        iReporterRecordsSent = builder.iReporterRecordsSent;
        iReporterRecordsFailed = builder.iReporterRecordsFailed;
        auditRecordsSent = builder.auditRecordsSent;
        auditRecordsFailed = builder.auditRecordsFailed;
        iReporterTiming = builder.iReporterTiming;
        auditMetrics = builder.auditMetrics;
    }

    static PublishMetric getReportsMetrics()
    {
        return REPORTS;
    }

    static PublishMetric getPaymentReportsMetrics()
    {
        return PAYMENT_REPORTS;
    }

    static PublishMetric getLoyaltyReportsMetrics()
    {
        return LOYALTY_REPORTS;
    }

    Metric getMessagesSentMetric()
    {
        return messagesSent;
    }

    Metric getRecordsSentMetric()
    {
        return recordsSent;
    }

    Metric getMessagesFailedMetric()
    {
        return messagesFailed;
    }

    Metric getRecordsFailedMetric()
    {
        return recordsFailed;
    }

    Metric getIReporterRecordsSent()
    {
        return iReporterRecordsSent;
    }

    Metric getIReporterRecordsFailed()
    {
        return iReporterRecordsFailed;
    }

    Metric getAuditRecordsSent()
    {
        return auditRecordsSent;
    }

    Metric getAuditRecordsFailed()
    {
        return auditRecordsFailed;
    }

    Metric getIReporterTiming()
    {
        return iReporterTiming;
    }

    Metric[] getAuditMetrics()
    {
        return auditMetrics;
    }

    private static PublishMetric getReportsPublishMetrics()
    {
        return new Builder().
                messagesSent(Metric.REPORTS_MESSAGES_SENT).
                recordsSent(Metric.REPORTS_RECORDS_SENT).
                messagesFailed(Metric.REPORTS_MESSAGES_FAILED).
                recordsFailed(Metric.REPORTS_RECORDS_FAILED).
                iReporterRecordsSent(Metric.IREPORTER_REPORTS_RECORDS_SENT).
                iReporterRecordsFailed(Metric.IREPORTER_REPORTS_RECORDS_FAILED).
                auditRecordsSent(Metric.AUDIT_RECORDS_SENT).
                auditRecordsFailed(Metric.AUDIT_RECORDS_FAILED).
                iReporterTiming(Metric.IREPORTER_TIMING).
                auditMetrics(new Metric[]{Metric.IREPORTER_REPORTS_RECORDS_SENT, Metric.IREPORTER_REPORTS_RECORDS_FAILED}).
                build();
    }

    private static PublishMetric getPaymentReportsPublishMetrics()
    {
        return new Builder().
                messagesSent(Metric.PAYMENT_REPORTS_MESSAGES_SENT).
                recordsSent(Metric.PAYMENT_REPORTS_RECORDS_SENT).
                messagesFailed(Metric.PAYMENT_REPORTS_MESSAGES_FAILED).
                recordsFailed(Metric.PAYMENT_REPORTS_RECORDS_FAILED).
                iReporterRecordsSent(Metric.PAYMENT_IREPORTER_REPORTS_RECORDS_SENT).
                iReporterRecordsFailed(Metric.PAYMENT_IREPORTER_REPORTS_RECORDS_FAILED).
                auditRecordsSent(Metric.PAYMENT_AUDIT_RECORDS_SENT).
                auditRecordsFailed(Metric.PAYMENT_AUDIT_RECORDS_FAILED).
                iReporterTiming(Metric.PAYMENT_IREPORTER_TIMING).
                auditMetrics(new Metric[]{Metric.PAYMENT_IREPORTER_REPORTS_RECORDS_SENT, Metric.PAYMENT_IREPORTER_REPORTS_RECORDS_FAILED}).
                build();
    }

    private static PublishMetric getLoyaltyReportsPublishMetrics()
    {
        return new Builder().
                messagesSent(Metric.LOYALTY_REPORTS_MESSAGES_SENT).
                recordsSent(Metric.LOYALTY_REPORTS_RECORDS_SENT).
                messagesFailed(Metric.LOYALTY_REPORTS_MESSAGES_FAILED).
                recordsFailed(Metric.LOYALTY_REPORTS_RECORDS_FAILED).
                iReporterRecordsSent(Metric.LOYALTY_IREPORTER_REPORTS_RECORDS_SENT).
                iReporterRecordsFailed(Metric.LOYALTY_IREPORTER_REPORTS_RECORDS_FAILED).
                auditRecordsSent(Metric.LOYALTY_AUDIT_RECORDS_SENT).
                auditRecordsFailed(Metric.LOYALTY_AUDIT_RECORDS_FAILED).
                iReporterTiming(Metric.LOYALTY_IREPORTER_TIMING).
                auditMetrics(new Metric[]{Metric.LOYALTY_IREPORTER_REPORTS_RECORDS_SENT, Metric.LOYALTY_IREPORTER_REPORTS_RECORDS_FAILED}).
                build();
    }

    private static class Builder
    {
        private Metric messagesSent;
        private Metric recordsSent;
        private Metric messagesFailed;
        private Metric recordsFailed;
        private Metric iReporterRecordsSent;
        private Metric iReporterRecordsFailed;
        private Metric auditRecordsSent;
        private Metric auditRecordsFailed;
        private Metric iReporterTiming;
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

        private Builder iReporterTiming(Metric value)
        {
            iReporterTiming = value;
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
}