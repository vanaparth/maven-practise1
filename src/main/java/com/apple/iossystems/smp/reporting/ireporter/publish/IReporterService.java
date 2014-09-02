package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;

/**
 * @author Toch
 */
public class IReporterService
{
    private PublishTaskHandler publishTaskHandler = PublishTaskHandler.getInstance();

    private IReporterService() throws Exception
    {
    }

    public static IReporterService getInstance() throws Exception
    {
        return new IReporterService();
    }

    public boolean postSMPEvent(EventRecord eventRecord)
    {
        return publishTaskHandler.add(eventRecord);
    }
}