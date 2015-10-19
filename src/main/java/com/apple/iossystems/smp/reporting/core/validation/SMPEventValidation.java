package com.apple.iossystems.smp.reporting.core.validation;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
public class SMPEventValidation
{
    private final SMPEventValidationMap smpEventValidationMap = SMPEventValidationMap.getInstance();

    private SMPEventValidation()
    {
    }

    public static SMPEventValidation getInstance()
    {
        return new SMPEventValidation();
    }

    public EventRecords validate(EventRecords records)
    {
        EventRecords invalidRecords = EventRecords.getInstance();

        for (EventRecord record : records.getList())
        {
            if (!smpEventValidationMap.isValid(record))
            {
                invalidRecords.add(record);
            }
        }

        return invalidRecords;
    }
}