package com.apple.iossystems.smp.reporting.core.logging;

import com.apple.iossystems.logging.LogLevel;
import com.apple.iossystems.smp.broker.sanitation.KistaSanitizerFactory;
import com.apple.iossystems.smp.domain.SMPReportingKistaRequest;
import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.journal.SMPJournal;
import com.apple.iossystems.smp.journal.SMPJournalHelper;
import com.apple.iossystems.smp.journal.SMPJournalService;
import com.apple.iossystems.smp.journal.core.JournalConstants;
import com.apple.iossystems.smp.journal.core.JournalEntryType;
import com.apple.iossystems.smp.reporting.core.email.ManageDeviceEvent;
import com.apple.iossystems.smp.reporting.core.email.ProvisionCardEvent;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterEvent;
import org.apache.commons.lang.StringUtils;
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

    private final SMPJournal smpJournal;

    public KistaEventLogger(boolean loggingEnabled)
    {
        this.loggingEnabled = loggingEnabled;

        smpJournal = getSMPJournal(loggingEnabled);
    }

    private SMPJournal getSMPJournal(boolean loggingEnabled)
    {
        SMPJournal smpJournal = null;

        if (loggingEnabled)
        {
            try
            {
                smpJournal = SMPJournalService.getJournal();
            }
            catch (Exception e)
            {
                LOGGER.error(e.getMessage(), e);
            }
        }

        return smpJournal;
    }

    private boolean loggingEnabled()
    {
        return (loggingEnabled && (smpJournal != null));
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
        String request = GsonBuilderFactory.getInstance().toJson(IReporterEvent.processEventRecord(record).getData(), Map.class);

        EventType eventType = EventType.getEventType(record.getAttributeValue(EventAttribute.EVENT_TYPE.key()));

        if( null != eventType ) {
            publishEvent(conversationId, seid, request, eventType);
        }
    }

    public void log(ProvisionCardEvent record)
    {
        String conversationId = record.getConversationId();
        String seid = record.getSeid();
        String request = GsonBuilderFactory.getInstance().toJson(record, ProvisionCardEvent.class);

        publishEvent(conversationId, seid, request, EventType.EMAIL);
    }

    public void log(ManageDeviceEvent record)
    {
        String conversationId = record.getConversationId();
        String seid = record.getSeid();
        String request = GsonBuilderFactory.getInstance().toJson(record, ManageDeviceEvent.class);

        publishEvent(conversationId, seid, request, EventType.EMAIL);
    }

    private void publishEvent(String conversationId, String seid, String request, EventType eventType)
    {
        if (loggingEnabled())
        {
            try
            {
                doPublishEvent(conversationId, seid, request, eventType);
            }
            catch (Exception e)
            {
                LOGGER.error(e.getMessage(), e);
            }
        }
    }

    private void doPublishEvent(String conversationId, String seid, String request, EventType eventType)
    {
        if (StringUtils.isBlank(conversationId))
        {
            conversationId = UUID.randomUUID().toString();
        }

        request = KistaSanitizerFactory.getSanitizer().sanitize(request, SMPReportingKistaRequest.class);

        smpJournal.record(LogLevel.INFO, seid, UUID.randomUUID().toString(), conversationId, request, JournalEntryType.REQUEST, getKistaMap(eventType));
    }

    private Map<String, String> getKistaMap(EventType eventType)
    {
        Map<String, String> map = SMPJournalHelper.buildMetadataMap("Reporting", null, null);

        map.put(JournalConstants.ACTION, "reporting");

        setTitle(map, eventType);

        return map;
    }

    private void setTitle(Map<String, String> map, EventType eventType)
    {
        String title = null;

        if (eventType == EventType.PAYMENT)
        {
            title = "Oslo";
        }
        else if (eventType == EventType.EMAIL)
        {
            title = "Email";
        }

        if (title != null)
        {
            map.put(JournalConstants.TITLE, title);
        }
    }
}