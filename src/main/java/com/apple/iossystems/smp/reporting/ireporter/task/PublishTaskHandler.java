package com.apple.iossystems.smp.reporting.ireporter.task;

import com.apple.iossystems.smp.reporting.core.analytics.Statistics;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledEvent;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTaskHandler;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterPublishService;

/**
 * @author Toch
 */
public class PublishTaskHandler
{
    private EventRecords eventRecords = EventRecords.getInstance();

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
        int available = eventRecords.size();

        IReporterPublishService service = reportsScheduledTask.getService();

        return ((available >= service.getConfiguration().getMaxBatchSize()) || ((available > 0) && service.publishDelayExpired()));
    }

    public synchronized void add(EventRecord e)
    {
        eventRecords.add(e);

        handleAddEvent();
    }

    public synchronized EventRecords emptyQueue()
    {
        EventRecords records = eventRecords;

        eventRecords = EventRecords.getInstance();

        return records;
    }

    public synchronized boolean hasData()
    {
        return (!eventRecords.isEmpty());
    }

    Statistics getStatistics()
    {
        return statistics;
    }
}
