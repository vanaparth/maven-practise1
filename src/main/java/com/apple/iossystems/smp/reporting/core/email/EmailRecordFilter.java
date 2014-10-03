package com.apple.iossystems.smp.reporting.core.email;

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

        boolean doFilter = ((smpCardEvent == SMPCardEvent.SUSPEND_CARD) || (smpCardEvent == SMPCardEvent.UNLINK_CARD) || (smpCardEvent == SMPCardEvent.RESUME_CARD));

        return ((!doFilter || hasValidCardEventSource(record)) && hasRequiredValues(record));
    }

    private static boolean hasValidCardEventSource(EmailRecord record)
    {
        ManageCardEvent manageCardEvent = record.getManageCardEvent();

        if (manageCardEvent != null)
        {
            return (manageCardEvent.getCardEventSource() == CardEventSource.FMIP);
        }
        else
        {
            return true;
        }
    }

    public static boolean hasRequiredValues(EmailRecord record)
    {
        return (record.getCardHolderEmail() != null);
    }
}