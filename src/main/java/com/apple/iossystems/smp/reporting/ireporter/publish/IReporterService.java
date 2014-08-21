package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.ireporter.task.PublishTaskHandler;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class IReporterService
{
    private static final IReporterService INSTANCE = newInstance();

    private PublishTaskHandler publishTaskHandler = PublishTaskHandler.getInstance();

    private IReporterService() throws Exception
    {
    }

    private static IReporterService newInstance()
    {
        try
        {
            return new IReporterService();
        }
        catch (Exception e)
        {
            Logger.getLogger(IReporterService.class).error(e);
            return null;
        }
    }

    public static IReporterService getInstance()
    {
        return INSTANCE;
    }

    public void postSMPEvent(EventRecord eventRecord)
    {
        publishTaskHandler.add(eventRecord);
    }
}