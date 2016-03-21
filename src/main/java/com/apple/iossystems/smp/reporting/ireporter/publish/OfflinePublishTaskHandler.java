package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;

/**
 * @author Toch
 */
class OfflinePublishTaskHandler implements EventTaskHandler
{
    private OfflinePublishTaskHandler()
    {
    }

    static OfflinePublishTaskHandler getInstance()
    {
        return new OfflinePublishTaskHandler();
    }

    @Override
    public void handleEvent()
    {
    }

    @Override
    public void processEventRecord(EventRecord record)
    {
    }

    @Override
    public void shutdown()
    {
    }
}