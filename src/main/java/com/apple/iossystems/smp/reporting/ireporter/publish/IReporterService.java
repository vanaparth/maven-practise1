package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;

/**
 * @author Toch
 */
public class IReporterService
{
    private PublishTaskHandler publishTaskHandler = PublishTaskHandler.getInstance();

    private IReporterService()
    {
    }

    public static IReporterService getInstance()
    {
        return new IReporterService();
    }

    public boolean postSMPEvent(EventRecord eventRecord)
    {
        return publishTaskHandler.add(eventRecord);
    }
}