package com.apple.iossystems.smp.reporting.ireporter.task;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.analytics.Statistics;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfiguration;
import com.apple.iossystems.smp.reporting.ireporter.json.IReporterJsonBuilder;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterPublishService;
import com.apple.iossystems.smp.reporting.ireporter.publish.ReportsPublishService;

import java.util.List;

/**
 * @author Toch
 */
public class ReportsScheduledTask extends IReporterScheduledTask
{
    private ReportsScheduledTask(PublishTaskHandler publishTaskHandler) throws Exception
    {
        super(publishTaskHandler, ReportsPublishService.getInstance());
    }

    public static ReportsScheduledTask getInstance(PublishTaskHandler publishTaskHandler) throws Exception
    {
        return new ReportsScheduledTask(publishTaskHandler);
    }

    @Override
    IReporterConfiguration.Type getConfigurationType()
    {
        return IReporterConfiguration.Type.REPORTS;
    }

    @Override
    public synchronized void handlePublishEvent()
    {
        IReporterPublishService service = getService();
        PublishTaskHandler publishTaskHandler = getPublishTaskHandler();

        if (service.isEnabled() && service.publishDelayExpired() && publishTaskHandler.hasData())
        {
            publishEventRecords(publishTaskHandler.emptyQueue());
        }
    }

    private void publishEventRecords(EventRecords records)
    {
        if (records.size() > 0)
        {
            sendEventRecords(records);
        }
    }

    private void sendEventRecords(EventRecords records)
    {
        IReporterPublishService service = getService();
        Statistics statistics = getPublishTaskHandler().getStatistics();
        List<EventRecord> list = records.getList();

        if (service.isEnabled() && service.publishDelayExpired())
        {
            boolean success = service.sendRequest(IReporterJsonBuilder.toJson(list));
            int count = list.size();

            if (success)
            {
                // Hubble
                statistics.increment(Metric.REPORTS_MESSAGES_SENT);
                statistics.increment(Metric.REPORTS_RECORDS_SENT, count);
                // IReporter
                statistics.increment(Metric.IREPORTER_REPORTS_RECORDS_SENT, count);
            }
            else
            {
                // Hubble
                statistics.increment(Metric.REPORTS_MESSAGES_FAILED);
                statistics.increment(Metric.REPORTS_RECORDS_FAILED, count);
                statistics.increment(Metric.IREPORTER_UNREACHABLE);
                // IReporter
                statistics.increment(Metric.IREPORTER_REPORTS_RECORDS_FAILED, count);
            }
        }
    }
}
