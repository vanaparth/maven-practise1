package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.analytics.Analytics;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class IReporterService
{
    private static final IReporterService INSTANCE = newInstance();

    private EventRecords eventRecords = EventRecords.getInstance();

    private Analytics analytics = Analytics.getInstance();

    private IReporterPublishService reportsPublishService = ReportsPublishService.getInstance(analytics);

    private IReporterPublishService auditPublishService = AuditPublishService.getInstance(analytics);

    private PublishTaskHandler publishTaskHandler;

    private IReporterService() throws Exception
    {
    }

    private static IReporterService newInstance()
    {
        IReporterService instance = null;

        try
        {
            instance = new IReporterService();
            instance.initPublishTaskHandler();
        }
        catch (Exception e)
        {
            Logger.getLogger(IReporterService.class).error(e);
        }

        return instance;
    }

    public static IReporterService getInstance()
    {
        return INSTANCE;
    }

    private void initPublishTaskHandler()
    {
        publishTaskHandler = PublishTaskHandler.getInstance(this);
        publishTaskHandler.begin();
    }

    public synchronized void postSMPEvent(EventRecord eventRecord)
    {
        addEventRecord(eventRecord);

        if (reportsPublishService.isAcceptingPublish())
        {
            publishTaskHandler.add(eventRecords);
            eventRecords = EventRecords.getInstance();
        }
    }

    private void addEventRecord(EventRecord eventRecord)
    {
        eventRecords.add(eventRecord);

        analytics.setMetric(Metric.REPORTS_AVAILABLE_COUNT, String.valueOf(eventRecords.size()));
    }

    IReporterPublishService getReportsPublishService()
    {
        return reportsPublishService;
    }

    IReporterPublishService getAuditPublishService()
    {
        return auditPublishService;
    }

    Analytics getAnalytics()
    {
        return analytics;
    }
}