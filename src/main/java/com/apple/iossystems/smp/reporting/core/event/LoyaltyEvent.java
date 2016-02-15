package com.apple.iossystems.smp.reporting.core.event;


/**
 * Created by scottblakesley on 12/15/15.
 */
public class LoyaltyEvent {

    private LoyaltyEventTypeEnum loyaltyEventType;
    private long eventTime;
    private String merchantId;
    private String token;
    private String source;

    public LoyaltyEvent(Builder builder) {
        this.loyaltyEventType = builder.loyaltyEventType;
        this.eventTime = builder.eventTime;
        this.merchantId = builder.merchantId;
        this.token = builder.token;
        this.source = builder.source;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    private EventRecords buildRecords() {
        EventRecord record = EventRecord.getInstance();
        record.setAttributeValue(EventAttribute.EVENT_TYPE.key(), EventType.LOYALTY.getKey());

        record.setAttributeValue(EventAttribute.EVENT.key(), loyaltyEventType.getEventType() );
        record.setAttributeValue(EventAttribute.TIMESTAMP.key(), String.valueOf(eventTime));
        record.setAttributeValue(EventAttribute.MERCHANT_ID.key(), merchantId);
        record.setAttributeValue(EventAttribute.TOKEN_ID.key(), token);
        record.setAttributeValue(EventAttribute.SOURCE.key(), source);

        EventRecords records = EventRecords.getInstance();
        records.add(record);
        return records;
    }

    public static class Builder {
        private LoyaltyEventTypeEnum loyaltyEventType;
        private long eventTime;
        private String merchantId;
        private String token;
        private String source;

        private Builder() { }

        public Builder setLoyaltyEventType(LoyaltyEventTypeEnum loyaltyEventType) {
            this.loyaltyEventType = loyaltyEventType;
            return this;
        }

        public Builder setEventTime(long eventTime) {
            this.eventTime = eventTime;
            return this;
        }

        public Builder setMerchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public Builder setToken(String token) {
            this.token = token;
            return this;
        }

        public Builder setSource(String source) {
            this.source = source;
            return this;
        }

        public EventRecords build() {
            return new LoyaltyEvent(this).buildRecords();
        }
    }
}
