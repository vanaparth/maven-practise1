package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.pno.PNO;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class SMPEvent
{
    private static final Logger LOGGER = Logger.getLogger(SMPEvent.class);

    private final SMPCardEvent smpCardEvent;
    private final String conversationId;
    private final PNO pno;

    private SMPEvent(Builder builder)
    {
        smpCardEvent = builder.smpCardEvent;
        conversationId = builder.conversationId;
        pno = builder.pno;
    }

    public static class Builder
    {
        private SMPCardEvent smpCardEvent;
        private String conversationId;
        private PNO pno;

        private Builder()
        {
        }

        public Builder smpCardEvent(SMPCardEvent value)
        {
            smpCardEvent = value;
            return this;
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
                return new SMPEvent(this).buildRecords();
            }
            catch (Exception e)
            {
                LOGGER.error(e);
                return EventRecords.getInstance();
            }
        }
    }

    private EventRecords buildRecords()
    {
        EventRecord record = EventRecord.getInstance();

        record.setAttributeValue(EventAttribute.TIMESTAMP.key(), String.valueOf(System.currentTimeMillis()));
        record.setAttributeValue(EventAttribute.CONVERSATION_ID.key(), conversationId);

        if (smpCardEvent != null)
        {
            record.setAttributeValue(EventAttribute.CARD_EVENT.key(), smpCardEvent.getCode());
        }

        if (pno != null)
        {
            String pnoName = pno.getPnoName();

            if (pnoName != null)
            {
                record.setAttributeValue(EventAttribute.PNO.key(), SMPEventCode.getPNONameCode(pnoName));
            }
        }

        EventRecords records = EventRecords.getInstance();
        records.add(record);

        return records;
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }
}
