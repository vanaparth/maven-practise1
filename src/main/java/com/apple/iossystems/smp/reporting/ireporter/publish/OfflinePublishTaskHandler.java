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

    public static OfflinePublishTaskHandler getInstance()
    {
        return new OfflinePublishTaskHandler();
    }

    @Override
    public final void handleEvent()
    {
    }

    @Override
    public void processEventRecord(EventRecord record)
    {
    }
}