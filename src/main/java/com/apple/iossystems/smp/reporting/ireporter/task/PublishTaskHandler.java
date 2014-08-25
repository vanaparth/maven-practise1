package com.apple.iossystems.smp.reporting.ireporter.task;

import com.apple.iossystems.smp.reporting.core.analytics.Statistics;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledEvent;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTaskHandler;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterPublishService;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Toch
 */
public class PublishTaskHandler
{
    private BlockingQueue<EventRecord> queue = new LinkedBlockingQueue<EventRecord>();

    private Statistics statistics = Statistics.getInstance();

    private IReporterScheduledTask reportsScheduledTask;

    private IReporterScheduledTask auditScheduledTask;

    private HubbleScheduledTask hubbleScheduledTask;

    private PublishTaskHandler() throws Exception
    {
    }

    public static PublishTaskHandler getInstance() throws Exception
    {
        PublishTaskHandler publishTaskHandler = new PublishTaskHandler();

        publishTaskHandler.startScheduledTasks();

        return publishTaskHandler;
    }

    private void startScheduledTasks() throws Exception
    {
        reportsScheduledTask = ReportsScheduledTask.getInstance(this);
        auditScheduledTask = AuditScheduledTask.getInstance(this);
        hubbleScheduledTask = HubbleScheduledTask.getInstance(this);

        startScheduledTask(reportsScheduledTask);
        startScheduledTask(auditScheduledTask);
        startScheduledTask(hubbleScheduledTask);
    }

    private void startScheduledTask(ScheduledTaskHandler scheduledTask)
    {
        scheduledTask.begin();
    }

    private void handleAddEvent()
    {
        if (reportsReady())
        {
            reportsScheduledTask.handleEvent(ScheduledEvent.PUBLISH);
        }
    }

    private boolean reportsReady()
    {
        int available = queue.size();

        IReporterPublishService service = reportsScheduledTask.getService();

        return ((available >= service.getConfiguration().getMaxBatchSize()) || ((available > 0) && service.publishDelayExpired()));
    }

    public void add(EventRecord e)
    {
        queue.offer(e);

        handleAddEvent();
    }

    public EventRecords emptyQueue()
    {
        EventRecords records;

        if (queue.isEmpty())
        {
            records = EventRecords.getEmpty();
        }
        else
        {
            records = EventRecords.getInstance();

            queue.drainTo(records.getList());
        }

        return records;
    }

    Statistics getStatistics()
    {
        return statistics;
    }
}