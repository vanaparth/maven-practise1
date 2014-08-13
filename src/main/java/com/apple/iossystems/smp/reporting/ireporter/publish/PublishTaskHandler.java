package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.util.ValidValue;
import com.apple.iossystems.smp.reporting.ireporter.json.IReporterJsonBuilder;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Toch
 */
class PublishTaskHandler implements Runnable
{
    private static final Logger LOGGER = Logger.getLogger(PublishTaskHandler.class);

    private BlockingQueue<EventRecords> queue = new LinkedBlockingQueue<EventRecords>();

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
            LOGGER.error(e);
        }
    }

    void begin()
    {
        Executors.newSingleThreadExecutor().execute(this);
    }

    void add(EventRecords e)
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
        boolean success = iReporterService.getReportsPublishService().sendRequest(IReporterJsonBuilder.toJson(list));

        if (success)
        {
            iReporterService.getAnalytics().setMetric(Metric.REPORTS_PUBLISH_TIME, String.valueOf(System.currentTimeMillis()));
            iReporterService.getAnalytics().setMetric(Metric.REPORTS_SENT_COUNT, String.valueOf(list.size()));
        }

        return success;
    }

    private boolean sendAudit()
    {
        int sentCount = ValidValue.getIntValueWithDefault(iReporterService.getAnalytics().getMetric(Metric.REPORTS_SENT_COUNT), 0);

        IReporterAudit auditData = IReporterAudit.getBuilder().sentCount(sentCount).failedCount(0).backlogCount(0).build();

        boolean success = iReporterService.getAuditPublishService().sendRequest(auditData.toJson());

        if (success)
        {
            iReporterService.getAnalytics().setMetric(Metric.AUDIT_PUBLISH_TIME, String.valueOf(System.currentTimeMillis()));
        }

        return success;
    }
}
