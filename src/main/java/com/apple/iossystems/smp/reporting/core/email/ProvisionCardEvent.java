package com.apple.iossystems.smp.reporting.core.email;

/**
 * @author Toch
 */
public class ProvisionCardEvent
{
    private final String conversationId;
    private final String timestamp;
    private final String cardHolderName;
    private final String cardHolderEmail;
    private final String cardDisplayNumber;
    private final String deviceName;
    private final String deviceType;
    private final String dpanId;
    private final String dsid;
    private final String locale;

    private ProvisionCardEvent(Builder builder)
    {
        conversationId = builder.conversationId;
        timestamp = builder.timestamp;
        cardHolderName = builder.cardHolderName;
        cardHolderEmail = builder.cardHolderEmail;
        cardDisplayNumber = builder.cardDisplayNumber;
        deviceName = builder.deviceName;
        deviceType = builder.deviceType;
        dpanId = builder.dpanId;
        dsid = builder.dsid;
        locale = builder.locale;
    }

    public String getConversationId()
    {
        return conversationId;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public String getCardHolderName()
    {
        return cardHolderName;
    }

    public String getCardHolderEmail()
    {
        return cardHolderEmail;
    }

    public String getCardDisplayNumber()
    {
        return cardDisplayNumber;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public String getDpanId()
    {
        return dpanId;
    }

    public String getDsid()
    {
        return dsid;
    }

    public String getLocale()
    {
        return locale;
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private String conversationId;
        private String timestamp;
        private String cardHolderName;
        private String cardHolderEmail;
        private String cardDisplayNumber;
        private String deviceName;
        private String deviceType;
        private String dpanId;
        private String dsid;
        private String locale;

        private Builder()
        {
        }

        public Builder conversationId(String value)
        {
            conversationId = value;
            return this;
        }

        public Builder timestamp(String value)
        {
            timestamp = value;
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

        public Builder cardDisplayNumber(String value)
        {
            cardDisplayNumber = value;
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

        public Builder dpanId(String value)
        {
            dpanId = value;
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

        public ProvisionCardEvent build()
        {
            return new ProvisionCardEvent(this);
        }
    }
}