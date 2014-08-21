package com.apple.iossystems.smp.reporting.ireporter.task;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.analytics.Statistics;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledEvent;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledNotification;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTaskHandler;
import com.apple.iossystems.smp.reporting.ireporter.configuration.ConfigurationEvent;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfiguration;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterPublishService;

/**
 * @author Toch
 */
abstract class IReporterScheduledTask implements ScheduledTaskHandler
{
    private PublishTaskHandler publishTaskHandler;
    private IReporterPublishService publishService;

    IReporterScheduledTask(PublishTaskHandler publishTaskHandler, IReporterPublishService publishService)
    {
        this.publishTaskHandler = publishTaskHandler;
        this.publishService = publishService;
    }

    @Override
    public final void begin()
    {
        IReporterConfiguration configuration = publishService.getConfiguration();

        int initialDelay = 1000;
        int configurationReloadFrequency = configuration.getConfigurationReloadFrequency();
        int publishFrequency = configuration.getPublishFrequency();

        startScheduledTask(ScheduledEvent.CONFIGURATION, initialDelay, configurationReloadFrequency);
        startScheduledTask(ScheduledEvent.PUBLISH, initialDelay, publishFrequency);
    }

    private void startScheduledTask(ScheduledEvent scheduledEvent, long initialDelay, long period)
    {
        ScheduledNotification.getInstance(this, scheduledEvent, initialDelay, period);
    }

    @Override
    public final void handleEvent(ScheduledEvent event)
    {
        if (event == ScheduledEvent.CONFIGURATION)
        {
            handleConfigurationEvent();
        }
        else if (event == ScheduledEvent.PUBLISH)
        {
            handlePublishEvent();
        }
    }

    abstract void handlePublishEvent();

    abstract IReporterConfiguration.Type getConfigurationType();

    final IReporterPublishService getService()
    {
        return publishService;
    }

    final PublishTaskHandler getPublishTaskHandler()
    {
        return publishTaskHandler;
    }

    private void handleConfigurationEvent()
    {
        IReporterConfiguration.Type configurationType = getConfigurationType();

        Metric metricUpdated;
        Metric metricModified;

        if (configurationType == IReporterConfiguration.Type.REPORTS)
        {
            metricUpdated = Metric.REPORTS_CONFIGURATION_REQUESTED;
            metricModified = Metric.REPORTS_CONFIGURATION_CHANGED;
        }
        else if (configurationType == IReporterConfiguration.Type.AUDIT)
        {
            metricUpdated = Metric.AUDIT_CONFIGURATION_REQUESTED;
            metricModified = Metric.AUDIT_CONFIGURATION_CHANGED;
        }
        else
        {
            return;
        }

        Statistics statistics = publishTaskHandler.getStatistics();

        ConfigurationEvent configurationEvent = getService().getConfigurationService().getConfigurationEvent();

        if (configurationEvent.isUpdated())
        {
            statistics.increment(metricUpdated);

            if (configurationEvent.isModified())
            {
                statistics.increment(metricModified);
            }
        }
    }
}