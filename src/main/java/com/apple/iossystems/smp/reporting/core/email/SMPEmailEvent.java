package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.*;

import java.util.List;

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
        SMPDeviceEvent smpEvent = SMPDeviceEvent.getEvent(record);

        return ((smpEvent == SMPDeviceEvent.PROVISION_CARD) || (smpEvent == SMPDeviceEvent.SUSPEND_CARD) || (smpEvent == SMPDeviceEvent.UNLINK_CARD) || (smpEvent == SMPDeviceEvent.RESUME_CARD));
    }

    public static EventRecords getEventRecords(EventRecords records)
    {
        EventRecords outputRecords = EventRecords.getInstance();

        List<EventRecord> list = records.getList();

        if (!list.isEmpty())
        {
            EventRecord record = list.get(0);

            if (isEmailRecord(record))
            {
                EventRecord copy = record.getCopy();

                copy.setAttributeValue(EventAttribute.EVENT_TYPE.key(), EventType.EMAIL.getKey());

                outputRecords.add(copy);
            }
        }

        return outputRecords;
    }
}