package com.apple.iossystems.smp.reporting.core.email;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Toch
 */
public class EmailEvent
{
    private final String cardEvent;
    private final String cardHolderName;
    private final String cardHolderEmail;
    private final String cardDescription;
    private final String cardDisplayNumber;
    private final String conversationId;
    private final String deviceName;
    private final String deviceType;
    private final String locale;
    private final String timestamp;

    private EmailEvent(Builder builder)
    {
        cardEvent = builder.cardEvent;
        cardHolderName = builder.cardHolderName;
        cardHolderEmail = builder.cardHolderEmail;
        cardDescription = builder.cardDescription;
        cardDisplayNumber = builder.cardDisplayNumber;
        conversationId = builder.conversationId;
        deviceName = builder.deviceName;
        deviceType = builder.deviceType;
        locale = builder.locale;
        timestamp = builder.timestamp;
    }

    public String getCardEvent()
    {
        return cardEvent;
    }

    public String getCardHolderName()
    {
        return cardHolderName;
    }

    public String getCardHolderEmail()
    {
        return cardHolderEmail;
    }

    public String getCardDescription()
    {
        return cardDescription;
    }

    public String getCardDisplayNumber()
    {
        return cardDisplayNumber;
    }

    public String getConversationId()
    {
        return conversationId;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    private String getDeviceType()
    {
        return deviceType;
    }

    private String getLocale()
    {
        return locale;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public Date getDate()
    {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.valueOf(timestamp));

        return calendar.getTime();
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private String cardEvent;
        private String cardHolderName;
        private String cardHolderEmail;
        private String cardDescription;
        private String cardDisplayNumber;
        private String conversationId;
        private String deviceName;
        private String deviceType;
        private String locale;
        private String timestamp;

        private Builder()
        {
        }

        public Builder cardEvent(String value)
        {
            cardEvent = value;
            return this;
        }

        public Builder cardHolderName(String value)
        {
            cardHolderName = value;
            return this;
        }

        public Builder cardHolderEmail(String value)
        {
            cardHolderEmail = value;
            return this;
        }

        public Builder cardDescription(String value)
        {
            cardDescription = value;
            return this;
        }

        public Builder cardDisplayNumber(String value)
        {
            cardDisplayNumber = value;
            return this;
        }

        public Builder conversationId(String value)
        {
            conversationId = value;
            return this;
        }

        public Builder deviceName(String value)
        {
            deviceName = value;
            return this;
        }

        public Builder deviceType(String value)
        {
            deviceType = value;
            return this;
        }

        public Builder locale(String value)
        {
            locale = value;
            return this;
        }

        public Builder timestamp(String value)
        {
            timestamp = value;
            return this;
        }

        public EmailEvent build()
        {
            return new EmailEvent(this);
        }
    }
}