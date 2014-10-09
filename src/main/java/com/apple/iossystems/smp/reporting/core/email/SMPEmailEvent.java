package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.*;

/**
 * @author Toch
 */
public class SMPEmailEvent
{
    private SMPEmailEvent()
    {
    }

    private static boolean isEmailRecord(EventRecord record)
    {
        SMPCardEvent smpCardEvent = SMPCardEvent.getSMPCardEvent(record);

        return ((smpCardEvent == SMPCardEvent.PROVISION_CARD) ||
                (smpCardEvent == SMPCardEvent.SUSPEND_CARD) ||
                (smpCardEvent == SMPCardEvent.UNLINK_CARD) ||
                (smpCardEvent == SMPCardEvent.RESUME_CARD));
    }

    public static EventRecords getEventRecords(EventRecords records)
    {
        EventRecords outputRecords = EventRecords.getInstance();

        if (!records.getList().isEmpty())
        {
            EventRecord record = records.getList().get(0);

            if (isEmailRecord(record))
            {
                record.setAttributeValue(EventAttribute.EVENT_TYPE.key(), EventType.EMAIL.getKey());
                outputRecords.add(record);
            }
        }

        return outputRecords;
    }
}