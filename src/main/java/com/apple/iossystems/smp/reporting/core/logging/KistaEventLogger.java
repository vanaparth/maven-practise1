package com.apple.iossystems.smp.reporting.core.logging;

import com.apple.iossystems.logging.LogLevel;
import com.apple.iossystems.smp.broker.sanitation.KistaSanitizerFactory;
import com.apple.iossystems.smp.domain.SMPReportingKistaRequest;
import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.journal.SMPJournal;
import com.apple.iossystems.smp.journal.SMPJournalHelper;
import com.apple.iossystems.smp.journal.SMPJournalService;
import com.apple.iossystems.smp.journal.core.JournalEntryType;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;

import java.util.Map;
import java.util.UUID;

/**
 * @author Toch
 */
public class KistaEventLogger
{
    private final boolean loggingEnabled;

    private final SMPJournal journal = SMPJournalService.getJournal();

    public KistaEventLogger(boolean loggingEnabled)
    {
        this.loggingEnabled = loggingEnabled;
    }

    public void log(EventRecords records)
    {
        if (loggingEnabled)
        {
            for (EventRecord record : records.getList())
            {
                log(record);
            }
        }
    }

    private void log(EventRecord record)
    {
        logEvent(record);
    }

    private void logEvent(EventRecord record)
    {
        String seid = record.getAttributeValue(EventAttribute.SEID.key());
        String conversationId = record.getAttributeValue(EventAttribute.CONVERSATION_ID.key());
        String requestId = UUID.randomUUID().toString();

        Map<String, String> data = SMPJournalHelper.buildMetadataMap("IReporter", null, null);
        String redactedRequestBody = KistaSanitizerFactory.getSanitizer().sanitize(GsonBuilderFactory.getInstance().toJson(record.getData(), Map.class), SMPReportingKistaRequest.class);

        journal.record(LogLevel.INFO, seid, requestId, conversationId, redactedRequestBody, JournalEntryType.REQUEST, data);
    }
}