package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.SMPCardEvent;

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
        SMPCardEvent smpCardEvent = record.getSMPCardEvent();

        boolean isManageCardEvent = ((smpCardEvent == SMPCardEvent.SUSPEND_CARD) || (smpCardEvent == SMPCardEvent.UNLINK_CARD) || (smpCardEvent == SMPCardEvent.RESUME_CARD));

        return ((!isManageCardEvent || hasValidManageCardEventSource(record)) && hasRequiredValues(record));
    }

    public static boolean isProvisionEventRecord(EventRecord record)
    {
        return (SMPCardEvent.getSMPCardEvent(record) == SMPCardEvent.PROVISION_CARD);
    }

    public static boolean isManageCardEventRecord(EventRecord record)
    {
        SMPCardEvent smpCardEvent = SMPCardEvent.getSMPCardEvent(record);

        return ((smpCardEvent == SMPCardEvent.SUSPEND_CARD) || (smpCardEvent == SMPCardEvent.UNLINK_CARD) || (smpCardEvent == SMPCardEvent.RESUME_CARD));
    }

    private static boolean hasValidManageCardEventSource(EmailRecord record)
    {
        ManageCardEvent manageCardEvent = record.getManageCardEvent();

        return ((manageCardEvent == null) || (manageCardEvent.getCardEventSource() == CardEventSource.FMIP));
    }

    public static boolean hasRequiredValues(EmailRecord record)
    {
        return (record.getCardHolderEmail() != null);
    }
}