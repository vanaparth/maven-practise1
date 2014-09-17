package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
public class EmailPublishService
{
    private EmailPublishService()
    {
    }

    public static EmailPublishService getInstance()
    {
        return new EmailPublishService();
    }

    public void send(EventRecords records)
    {
        for (EventRecord record : records.getList())
        {
            send(record);
        }
    }

    private void send(EventRecord record)
    {
    }
}