package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.SMPDeviceEvent;

/**
 * @author Toch
 */
class EmailRecordFilter
{
    private EmailRecordFilter()
    {
    }

    public static boolean isValidEmailRecord(EmailRecord record)
    {
        SMPDeviceEvent smpEvent = record.getSMPEvent();

        boolean isManageDeviceEvent = ((smpEvent == SMPDeviceEvent.SUSPEND_CARD) || (smpEvent == SMPDeviceEvent.UNLINK_CARD) || (smpEvent == SMPDeviceEvent.RESUME_CARD));

        return ((!isManageDeviceEvent || hasValidManageDeviceEventSource(record)) && hasRequiredValues(record));
    }

    public static boolean isManageDeviceEventRecord(EventRecord record)
    {
        SMPDeviceEvent smpEvent = SMPDeviceEvent.getSMPEvent(record);

        return ((smpEvent == SMPDeviceEvent.SUSPEND_CARD) || (smpEvent == SMPDeviceEvent.UNLINK_CARD) || (smpEvent == SMPDeviceEvent.RESUME_CARD));
    }

    private static boolean hasValidManageDeviceEventSource(EmailRecord record)
    {
        ManageDeviceEvent manageDeviceEvent = record.getManageDeviceEvent();

        return ((manageDeviceEvent == null) || (manageDeviceEvent.getManageDeviceEventSource() == ManageDeviceEventSource.FMIP));
    }

    public static boolean hasRequiredValues(EmailRecord record)
    {
        return (record.getCardHolderEmail() != null);
    }
}