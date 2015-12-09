package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;

/**
 * @author Toch
 */
public class PublishTaskHandlerFactory
{
    private PublishTaskHandlerFactory()
    {
    }

    public static PublishTaskHandlerFactory getInstance()
    {
        return new PublishTaskHandlerFactory();
    }

    public EventTaskHandler getPublishTaskHandler()
    {
        if (ApplicationConfiguration.publishEventsEnabled())
        {
            return PublishTaskHandler.getInstance();
        }
        else
        {
            return OfflinePublishTaskHandler.getInstance();
        }
    }
}