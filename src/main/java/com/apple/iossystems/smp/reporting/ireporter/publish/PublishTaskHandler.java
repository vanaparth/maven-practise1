package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecordList;
import com.apple.iossystems.smp.reporting.core.event.SMPEventRecordJsonBuilder;
import com.apple.iossystems.smp.reporting.core.util.ValueSelector;
import com.apple.iossystems.smp.reporting.ireporter.analytics.IReporterMetric;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Toch
 */
class PublishTaskHandler implements Runnable
{
    private BlockingQueue<EventRecordList> queue = new LinkedBlockingQueue<EventRecordList>();

    private IReporterService iReporterService;

    private PublishTaskHandler(IReporterService iReporterService)
    {
        this.iReporterService = iReporterService;
    }

    static PublishTaskHandler getInstance(IReporterService iReporterService)
    {
        return new PublishTaskHandler(iReporterService);
    }

    @Override
    public void run()
    {
        while (true)
        {
            consume();
        }
    }

    private void consume()
    {
        try
        {
            publish(queue.take().getList());
        }
        catch (Exception e)
        {
        }
    }

    void begin()
    {
        Executors.newSingleThreadExecutor().execute(this);
    }

    void add(EventRecordList e)
    {
        queue.offer(e);
    }

    private void publish(List<EventRecord> list)
    {
        if (sendReports(list))
        {
            sendAudit();
        }
    }

    private boolean sendReports(List<EventRecord> list)
    {
        boolean success = iReporterService.getReportsPublishService().sendRequest(SMPEventRecordJsonBuilder.toJson(list));

        if (success)
        {
            iReporterService.getAnalytics().updateMetricStatistics(IReporterMetric.REPORTS_PUBLISH_TIME, System.currentTimeMillis());
            iReporterService.getAnalytics().updateMetricStatistics(IReporterMetric.REPORTS_SENT_COUNT, list.size());
        }

        return success;
    }

    private boolean sendAudit()
    {
        int sentCount = ValueSelector.getIntValueWithDefault(iReporterService.getAnalytics().getMetricStatistics(IReporterMetric.REPORTS_SENT_COUNT), 0);

        IReporterAudit auditData = IReporterAudit.Builder.getInstance().sentCount(sentCount).failedCount(0).backlogCount(0).build();

        boolean success = iReporterService.getAuditPublishService().sendRequest(auditData.toJson());

        if (success)
        {
            iReporterService.getAnalytics().updateMetricStatistics(IReporterMetric.AUDIT_PUBLISH_TIME, System.currentTimeMillis());
        }

        return success;
    }
}
