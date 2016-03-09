package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.reporting.core.util.Calendar;
import org.apache.log4j.Logger;

import java.text.DecimalFormat;

/**
 * @author Toch
 */
public class PaymentEvent
{
    private static final Logger LOGGER = Logger.getLogger(PaymentEvent.class);

    private final String currency;
    private final String merchantId;
    private final String merchantName;
    private final String seid;
    private final String transactionId;
    private final float transactionAmount;
    private final PaymentTransactionStatus transactionStatus;

    private PaymentEvent(Builder builder)
    {
        currency = builder.currency;
        merchantId = builder.merchantId;
        merchantName = builder.merchantName;
        seid = builder.seid;
        transactionId = builder.transactionId;
        transactionAmount = builder.transactionAmount;
        transactionStatus = builder.transactionStatus;
    }

    private String formatTransactionAmount(float value)
    {
        return new DecimalFormat("0.00").format(value);
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }

    private EventRecords buildRecords()
    {
        EventRecord record = EventRecord.getInstance();

        record.setAttributeValue(EventAttribute.EVENT_TYPE.key(), EventType.PAYMENT.getKey());

        record.setAttributeValue(EventAttribute.TIMESTAMP.key(), String.valueOf(Calendar.getCurrentHourInMilliseconds()));
        record.setAttributeValue(EventAttribute.CURRENCY.key(), currency);
        record.setAttributeValue(EventAttribute.MERCHANT_ID.key(), getMerchantInfo());
        record.setAttributeValue(EventAttribute.SEID.key(), seid);
        record.setAttributeValue(EventAttribute.TRANSACTION_ID.key(), transactionId);
        record.setAttributeValue(EventAttribute.TRANSACTION_AMOUNT.key(), formatTransactionAmount(transactionAmount));

        String transactionStatusKey = EventAttribute.TRANSACTION_STATUS.key();

        if (transactionStatus != null)
        {
            record.setAttributeValue(transactionStatusKey, transactionStatus.getCode());
        }
        else
        {
            record.setAttributeValue(transactionStatusKey, PaymentTransactionStatus.SUCCESS.getCode());
        }

        EventRecords records = EventRecords.getInstance();
        records.add(record);

        return records;
    }

    private String getMerchantInfo()
    {
        String merchantInfo = null;

        if (merchantId != null)
        {
            merchantInfo = merchantId;
        }

        if (merchantName != null)
        {
            if (merchantInfo != null)
            {
                merchantInfo += "\t" + merchantName;
            }
            else
            {
                merchantInfo = merchantName;
            }
        }

        return merchantInfo;
    }

    public static class Builder
    {
        private String currency;
        private String merchantId;
        private String merchantName;
        private String seid;
        private String transactionId;
        private float transactionAmount;
        private PaymentTransactionStatus transactionStatus;

        private Builder()
        {
        }

        public Builder currency(String value)
        {
            currency = value;
            return this;
        }

        public Builder merchantId(String value)
        {
            merchantId = value;
            return this;
        }

        public Builder merchantName(String value)
        {
            merchantName = value;
            return this;
        }

        public Builder seid(String value)
        {
            seid = value;
            return this;
        }

        public Builder transactionId(String value)
        {
            transactionId = value;
            return this;
        }

        public Builder transactionAmount(float value)
        {
            transactionAmount = value;
            return this;
        }

        public Builder transactionStatus(PaymentTransactionStatus value)
        {
            transactionStatus = value;
            return this;
        }

        private float getTransactionAmount(float value)
        {
            return (Math.round(value / 10f) / 10f);
        }

        private void completeBuild()
        {
            transactionAmount = getTransactionAmount(transactionAmount);
        }

        public EventRecords build()
        {
            // Prevent any side effects
            try
            {
                completeBuild();

                return new PaymentEvent(this).buildRecords();
            }
            catch (Exception e)
            {
                LOGGER.error(e.getMessage(), e);

                return EventRecords.getInstance();
            }
        }
    }
}