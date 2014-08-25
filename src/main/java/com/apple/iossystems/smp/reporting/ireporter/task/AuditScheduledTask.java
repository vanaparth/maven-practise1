package com.apple.iossystems.smp.reporting.ireporter.task;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.analytics.Statistics;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfiguration;
import com.apple.iossystems.smp.reporting.ireporter.publish.AuditPublishService;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterAudit;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterPublishService;

/**
 * @author Toch
 */
public class AuditScheduledTask extends IReporterScheduledTask
{
    private AuditScheduledTask(PublishTaskHandler publishTaskHandler) throws Exception
    {
        super(publishTaskHandler, AuditPublishService.getInstance());
    }

    public static AuditScheduledTask getInstance(PublishTaskHandler publishTaskHandler) throws Exception
    {
        return new AuditScheduledTask(publishTaskHandler);
    }

    @Override
    IReporterConfiguration.Type getConfigurationType()
    {
        return IReporterConfiguration.Type.AUDIT;
    }

    @Override
    public void handlePublishEvent()
    {
        sendAudit();
    }

    private void sendAudit()
    {
        IReporterPublishService service = getService();
        Statistics statistics = getPublishTaskHandler().getStatistics();

        if (service.isEnabled() && service.publishDelayExpired())
        {
            int sent = statistics.getIntValue(Metric.IREPORTER_REPORTS_RECORDS_SENT);
            int failed = statistics.getIntValue(Metric.IREPORTER_REPORTS_RECORDS_FAILED);
            int backLog = statistics.getIntValue(Metric.IREPORTER_REPORTS_RECORDS_PENDING);
            int lost = statistics.getIntValue(Metric.IREPORTER_REPORTS_RECORDS_LOST);

            IReporterAudit auditData = IReporterAudit.getBuilder().sentCount(sent).failedCount(failed).backlogCount(backLog).lostCount(lost).build();

            if (service.sendRequest(auditData.toJson()))
            {
                statistics.increment(Metric.AUDIT_RECORDS_SENT);

                clearMetrics();
            }
            else
            {
                statistics.increment(Metric.AUDIT_RECORDS_FAILED);
            }
        }
    }

    private void clearMetrics()
    {
        Statistics statistics = getPublishTaskHandler().getStatistics();

        statistics.clear(Metric.IREPORTER_REPORTS_RECORDS_SENT);
        statistics.clear(Metric.IREPORTER_REPORTS_RECORDS_FAILED);
        statistics.clear(Metric.IREPORTER_REPORTS_RECORDS_PENDING);
        statistics.clear(Metric.IREPORTER_REPORTS_RECORDS_LOST);
    }
}