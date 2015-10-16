package com.apple.iossystems.smp.reporting.core.validation;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;

/**
 * @author Toch
 */
public class SMPPublishEventValidation
{
    private final SMPEventValidationMap smpEventValidationMap = SMPEventValidationMap.getInstance();

    private SMPPublishEventValidation()
    {
    }

    public static SMPPublishEventValidation getInstance()
    {
        return new SMPPublishEventValidation();
    }

    public boolean validate(EventRecords records)
    {
        boolean result = true;

        for (EventRecord record : records.getList())
        {
            if (!validate(record))
            {
                result = false;
                break;
            }
        }

        return result;
    }

    public boolean validate(EventRecord record)
    {
        return smpEventValidationMap.isValid(record);
    }
}