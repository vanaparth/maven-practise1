package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.pno.PNO;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class PutPendingCommandSMPEvent
{
    private static final Logger LOGGER = Logger.getLogger(PutPendingCommandSMPEvent.class);

    private final String conversationId;
    private final PNO pno;

    private PutPendingCommandSMPEvent(Builder builder)
    {
        conversationId = builder.conversationId;
        pno = builder.pno;
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }

    private EventRecords buildRecords()
    {
        EventRecord record = EventRecord.getInstance();

        record.setAttributeValue(EventAttribute.EVENT_TYPE.key(), EventType.REPORTS.getKey());
        record.setAttributeValue(EventAttribute.TIMESTAMP.key(), String.valueOf(System.currentTimeMillis()));
        record.setAttributeValue(EventAttribute.CONVERSATION_ID.key(), conversationId);

        SMPDeviceEvent.PUT_PENDING.setEvent(record);

        if (pno != null)
        {
            String pnoName = pno.getPnoName();

            if (pnoName != null)
            {
                SMPEventCode.writePNOName(record, EventAttribute.PNO, pnoName);
            }
        }

        EventRecords records = EventRecords.getInstance();
        records.add(record);

        return records;
    }

    public static class Builder
    {
        private String conversationId;
        private PNO pno;

        private Builder()
        {
        }

        public Builder conversationId(String value)
        {
            conversationId = value;
            return this;
        }

        public Builder pno(PNO value)
        {
            pno = value;
            return this;
        }

        public EventRecords build()
        {
            // Prevent any side effects
            try
            {
                return new PutPendingCommandSMPEvent(this).buildRecords();
            }
            catch (Exception e)
            {
                LOGGER.error(e);
                return EventRecords.getInstance();
            }
        }
    }
}