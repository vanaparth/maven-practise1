package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecordList;
import com.apple.iossystems.smp.reporting.ireporter.analytics.IReporterAnalytics;
import com.apple.iossystems.smp.reporting.ireporter.analytics.IReporterMetric;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class IReporterService
{
    private static IReporterService INSTANCE = newInstance();

    private EventRecordList eventRecords = EventRecordList.getInstance();

    private IReporterAnalytics analytics = IReporterAnalytics.getInstance();

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

        if (reportsPublishService.doPublish())
        {
            publishTaskHandler.add(eventRecords);
            eventRecords = EventRecordList.getInstance();
        }
    }

    private void addEventRecord(EventRecord eventRecord)
    {
        eventRecords.add(eventRecord);

        analytics.updateMetricStatistics(IReporterMetric.REPORTS_AVAILABLE_COUNT, eventRecords.size());
    }

    IReporterPublishService getReportsPublishService()
    {
        return reportsPublishService;
    }

    IReporterPublishService getAuditPublishService()
    {
        return auditPublishService;
    }

    IReporterAnalytics getAnalytics()
    {
        return analytics;
    }
}