package com.apple.iossystems.smp.reporting.core.event;


/**
 * Created by scottblakesley on 12/15/15.
 */
public class LoyaltyEvent
{
    private final LoyaltyEventTypeEnum loyaltyEventType;
    private final long eventTime;
    private final String merchantId;
    private final String token;

    public LoyaltyEvent(Builder builder)
    {
        loyaltyEventType = builder.loyaltyEventType;
        eventTime = builder.eventTime;
        merchantId = builder.merchantId;
        token = builder.token;
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }

    private EventRecords buildRecords()
    {
        EventRecord record = EventRecord.getInstance();

        record.setAttributeValue(EventAttribute.EVENT_TYPE.key(), EventType.LOYALTY.getKey());

        record.setAttributeValue(EventAttribute.EVENT.key(), loyaltyEventType.getEventType());
        record.setAttributeValue(EventAttribute.TIMESTAMP.key(), String.valueOf(eventTime));
        record.setAttributeValue(EventAttribute.MERCHANT_ID.key(), merchantId);
        record.setAttributeValue(EventAttribute.TOKEN_ID.key(), token);

        EventRecords records = EventRecords.getInstance();
        records.add(record);

        return records;
    }

    public static class Builder
    {
        private LoyaltyEventTypeEnum loyaltyEventType;
        private long eventTime;
        private String merchantId;
        private String token;

        private Builder()
        {
        }

        public Builder setLoyaltyEventType(LoyaltyEventTypeEnum value)
        {
            loyaltyEventType = value;
            return this;
        }

        public Builder setEventTime(long value)
        {
            eventTime = value;
            return this;
        }

        public Builder setMerchantId(String value)
        {
            merchantId = value;
            return this;
        }

        public Builder setToken(String value)
        {
            token = value;
            return this;
        }

        public EventRecords build()
        {
            return new LoyaltyEvent(this).buildRecords();
        }
    }
}