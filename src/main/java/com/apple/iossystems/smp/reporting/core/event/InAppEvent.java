package com.apple.iossystems.smp.reporting.core.event;

import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class InAppEvent
{
    private static final Logger LOGGER = Logger.getLogger(InAppEvent.class);

    private final String merchantId;
    private final String applicationId;
    private final InAppTransactionStatus transactionStatus;
    private final float transactionAmount;

    private InAppEvent(Builder builder)
    {
        merchantId = builder.merchantId;
        applicationId = builder.applicationId;
        transactionStatus = builder.transactionStatus;
        transactionAmount = builder.transactionAmount;
    }

    public String getMerchantId()
    {
        return merchantId;
    }

    public String getApplicationId()
    {
        return applicationId;
    }

    public InAppTransactionStatus getTransactionStatus()
    {
        return transactionStatus;
    }

    public float getTransactionAmount()
    {
        return transactionAmount;
    }

    public static class Builder
    {
        private String merchantId;
        private String applicationId;
        private InAppTransactionStatus transactionStatus;
        private float transactionAmount;

        private Builder()
        {
        }

        public Builder merchantId(String value)
        {
            merchantId = value;
            return this;
        }

        public Builder applicationId(String value)
        {
            applicationId = value;
            return this;
        }

        public Builder transactionStatus(InAppTransactionStatus value)
        {
            transactionStatus = value;
            return this;
        }

        public Builder transactionAmount(float value)
        {
            transactionAmount = value;
            return this;
        }

        public EventRecords build()
        {
            // Prevent any side effects
            try
            {
                return new InAppEvent(this).buildRecords();
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

        record.setAttributeValue(EventAttribute.APPLICATION_ID.key(), applicationId);
        record.setAttributeValue(EventAttribute.MERCHANT_ID.key(), merchantId);
        record.setAttributeValue(EventAttribute.TRANSACTION_AMOUNT.key(), String.valueOf(transactionAmount));

        if (transactionStatus != null)
        {
            record.setAttributeValue(EventAttribute.TRANSACTION_STATUS.key(), transactionStatus.getCode());
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
