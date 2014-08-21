package com.apple.iossystems.smp.reporting.ireporter.task;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledEvent;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledNotification;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTaskHandler;

/**
 * @author Toch
 */
public class HubbleScheduledTask implements ScheduledTaskHandler
{
    private PublishTaskHandler publishTaskHandler;

    private static final Metric[] METRICS =
            {
                    Metric.REPORTS_RECORDS_SENT,
                    Metric.REPORTS_MESSAGES_SENT,
                    Metric.REPORTS_RECORDS_FAILED,
                    Metric.REPORTS_MESSAGES_FAILED,
                    Metric.REPORTS_RECORDS_LOST,
                    Metric.REPORTS_RECORDS_PENDING,
                    Metric.REPORTS_CONFIGURATION_REQUESTED,
                    Metric.REPORTS_CONFIGURATION_CHANGED,
                    Metric.AUDIT_RECORDS_SENT,
                    Metric.AUDIT_RECORDS_FAILED,
                    Metric.AUDIT_RECORDS_LOST,
                    Metric.AUDIT_RECORDS_PENDING,
                    Metric.AUDIT_CONFIGURATION_REQUESTED,
                    Metric.AUDIT_CONFIGURATION_CHANGED
            };

    private HubbleScheduledTask(PublishTaskHandler publishTaskHandler)
    {
        this.publishTaskHandler = publishTaskHandler;
    }

    public static HubbleScheduledTask getInstance(PublishTaskHandler publishTaskHandler)
    {
        return new HubbleScheduledTask(publishTaskHandler);
    }

    @Override
    public void begin()
    {
        long initialDelay = 1000;
        long publishFrequency = 15 * 60 * 1000;

        startScheduledTask(ScheduledEvent.PUBLISH, initialDelay, publishFrequency);
    }

    private void startScheduledTask(ScheduledEvent scheduledEvent, long initialDelay, long period)
    {
        ScheduledNotification.getInstance(this, scheduledEvent, initialDelay, period);
    }

    @Override
    public void handleEvent(ScheduledEvent scheduledEvent)
    {
        if (scheduledEvent == ScheduledEvent.PUBLISH)
        {
            handlePublishEvent();
        }
    }

    private void handlePublishEvent()
    {
        publishMetrics();
        //clearMetrics();
    }

    private void publishMetrics()
    {
        publishTaskHandler.getStatistics().logToHubble(METRICS);
    }

    private void clearMetrics()
    {
        publishTaskHandler.getStatistics().clear(METRICS);
    }
}
