package com.apple.iossystems.smp.reporting.core.email;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Toch
 */
class EmailRecord
{
    private final String cardHolderName;
    private final String cardHolderEmail;
    private final String conversationId;
    private final String deviceName;
    private final String deviceType;
    private final String dsid;
    private final String locale;
    private final String timestamp;

    private EmailRecord(Builder builder)
    {
        cardHolderName = builder.cardHolderName;
        cardHolderEmail = builder.cardHolderEmail;
        conversationId = builder.conversationId;
        deviceName = builder.deviceName;
        deviceType = builder.deviceType;
        dsid = builder.dsid;
        locale = builder.locale;
        timestamp = builder.timestamp;
    }

    public String getCardHolderName()
    {
        return cardHolderName;
    }

    public String getCardHolderEmail()
    {
        return cardHolderEmail;
    }

    public String getConversationId()
    {
        return conversationId;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public String getDsid()
    {
        return dsid;
    }

    public String getLocale()
    {
        return locale;
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
        private String cardHolderName;
        private String cardHolderEmail;
        private String conversationId;
        private String deviceName;
        private String deviceType;
        private String dsid;
        private String locale;
        private String timestamp;

        private Builder()
        {
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

        public Builder dsid(String value)
        {
            dsid = value;
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

        public EmailRecord build()
        {
            return new EmailRecord(this);
        }
    }
}