package com.apple.iossystems.smp.reporting.core.logging;

import com.apple.iossystems.logging.LogLevel;
import com.apple.iossystems.smp.broker.sanitation.KistaSanitizerFactory;
import com.apple.iossystems.smp.domain.SMPReportingKistaRequest;
import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.journal.SMPJournal;
import com.apple.iossystems.smp.journal.SMPJournalHelper;
import com.apple.iossystems.smp.journal.SMPJournalService;
import com.apple.iossystems.smp.journal.core.JournalEntryType;
import com.apple.iossystems.smp.reporting.core.email.ManageDeviceEvent;
import com.apple.iossystems.smp.reporting.core.email.ProvisionCardEvent;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.UUID;

/**
 * @author Toch
 */
public class KistaEventLogger
{
    private static final Logger LOGGER = Logger.getLogger(KistaEventLogger.class);

    private final boolean loggingEnabled;

    private final SMPJournal journal = SMPJournalService.getJournal();

    public KistaEventLogger(boolean loggingEnabled)
    {
        this.loggingEnabled = loggingEnabled;
    }

    public void log(EventRecords records)
    {
        for (EventRecord record : records.getList())
        {
            log(record);
        }
    }

    private void log(EventRecord record)
    {
        String conversationId = record.getAttributeValue(EventAttribute.CONVERSATION_ID.key());
        String seid = record.getAttributeValue(EventAttribute.SEID.key());
        String request = GsonBuilderFactory.getInstance().toJson(record.getData(), Map.class);

        publishEvent(conversationId, seid, request);
    }

    public void log(ProvisionCardEvent record)
    {
        String conversationId = record.getConversationId();
        String seid = record.getSeid();
        String request = GsonBuilderFactory.getInstance().toJson(record, ProvisionCardEvent.class);

        publishEvent(conversationId, seid, request);
    }

    public void log(ManageDeviceEvent record)
    {
        String conversationId = record.getConversationId();
        String seid = record.getSeid();
        String request = GsonBuilderFactory.getInstance().toJson(record, ManageDeviceEvent.class);

        publishEvent(conversationId, seid, request);
    }

    private void publishEvent(String conversationId, String seid, String request)
    {
        if (loggingEnabled)
        {
            try
            {
                doPublishEvent(conversationId, seid, request);
            }
            catch (Exception e)
            {
                LOGGER.error(e);
            }
        }
    }

    private void doPublishEvent(String conversationId, String seid, String request)
    {
        String requestId = UUID.randomUUID().toString();
        Map<String, String> data = SMPJournalHelper.buildMetadataMap("Reporting", null, null);
        request = KistaSanitizerFactory.getSanitizer().sanitize(request, SMPReportingKistaRequest.class);

        journal.record(LogLevel.INFO, seid, requestId, conversationId, request, JournalEntryType.REQUEST, data);
    }
}