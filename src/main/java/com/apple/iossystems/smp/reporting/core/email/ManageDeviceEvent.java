package com.apple.iossystems.smp.reporting.core.email;

import java.util.List;

/**
 * @author Toch
 */
public class ManageDeviceEvent
{
    private final String cardHolderName;
    private final String cardHolderEmail;
    private final String dsid;
    private final String timestamp;
    private final String timezone;
    private final String locale;
    private final String deviceName;
    private final String deviceType;
    private final String deviceImageUrl;
    private final ManageDeviceEventSource manageDeviceEventSource;
    private final FmipSource fmipSource;
    private final List<CardEvent> cardEvents;

    private ManageDeviceEvent(Builder builder)
    {
        cardHolderName = builder.cardHolderName;
        cardHolderEmail = builder.cardHolderEmail;
        dsid = builder.dsid;
        timestamp = builder.timestamp;
        timezone = builder.timezone;
        locale = builder.locale;
        deviceName = builder.deviceName;
        deviceType = builder.deviceType;
        deviceImageUrl = builder.deviceImageUrl;
        manageDeviceEventSource = builder.manageDeviceEventSource;
        fmipSource = builder.fmipSource;
        cardEvents = builder.cardEvents;
    }

    public String getCardHolderName()
    {
        return cardHolderName;
    }

    public String getCardHolderEmail()
    {
        return cardHolderEmail;
    }

    public String getDsid()
    {
        return dsid;
    }

    public String getTimestamp()
    {
        return timestamp;
    }

    public String getTimezone()
    {
        return timezone;
    }

    public String getLocale()
    {
        return locale;
    }

    public String getDeviceName()
    {
        return deviceName;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public String getDeviceImageUrl()
    {
        return deviceImageUrl;
    }

    public ManageDeviceEventSource getManageDeviceEventSource()
    {
        return manageDeviceEventSource;
    }

    public FmipSource getFmipSource()
    {
        return fmipSource;
    }

    public List<CardEvent> getCardEvents()
    {
        return cardEvents;
    }

    public static Builder getBuilder()
    {
        return new Builder();
    }

    public static class Builder
    {
        private String cardHolderName;
        private String cardHolderEmail;
        private String dsid;
        private String timestamp;
        private String timezone;
        private String locale;
        private String deviceName;
        private String deviceType;
        private String deviceImageUrl;
        private ManageDeviceEventSource manageDeviceEventSource;
        private FmipSource fmipSource;
        private List<CardEvent> cardEvents;

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

        public Builder dsid(String value)
        {
            dsid = value;
            return this;
        }

        public Builder timestamp(String value)
        {
            timestamp = value;
            return this;
        }

        public Builder timezone(String value)
        {
            timezone = value;
            return this;
        }

        public Builder locale(String value)
        {
            locale = value;
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

        public Builder deviceImageUrl(String value)
        {
            deviceImageUrl = value;
            return this;
        }

        public Builder manageDeviceEventSource(ManageDeviceEventSource value)
        {
            manageDeviceEventSource = value;
            return this;
        }

        public Builder fmipSource(FmipSource value)
        {
            fmipSource = value;
            return this;
        }

        public Builder cardEvents(List<CardEvent> value)
        {
            cardEvents = value;
            return this;
        }

        public ManageDeviceEvent build()
        {
            return new ManageDeviceEvent(this);
        }
    }
}