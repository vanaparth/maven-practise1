package com.apple.iossystems.smp.reporting.core.email;

import java.util.List;

/**
 * @author Toch
 */
public class ManageDeviceEvent
{
    private final String eventType;
    private final String cardHolderName;
    private final String cardHolderEmail;
    private final String conversationId;
    private final String dsid;
    private final String seid;
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
        eventType = builder.eventType;
        cardHolderName = builder.cardHolderName;
        cardHolderEmail = builder.cardHolderEmail;
        conversationId = builder.conversationId;
        dsid = builder.dsid;
        seid = builder.seid;
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

    public String getEventType()
    {
        return eventType;
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

    public String getDsid()
    {
        return dsid;
    }

    public String getSeid()
    {
        return seid;
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
        private String eventType;
        private String cardHolderName;
        private String cardHolderEmail;
        private String conversationId;
        private String dsid;
        private String seid;
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

        public Builder eventType(String value)
        {
            eventType = value;
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

        public Builder conversationId(String value)
        {
            conversationId = value;
            return this;
        }

        public Builder dsid(String value)
        {
            dsid = value;
            return this;
        }

        public Builder seid(String value)
        {
            seid = value;
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

        public Builder copy(ManageDeviceEvent manageDeviceEvent)
        {
            return new Builder().
                    eventType(manageDeviceEvent.eventType).
                    cardHolderName(manageDeviceEvent.cardHolderName).
                    cardHolderEmail(manageDeviceEvent.cardHolderEmail).
                    conversationId(manageDeviceEvent.conversationId).
                    dsid(manageDeviceEvent.dsid).
                    seid(manageDeviceEvent.seid).
                    timestamp(manageDeviceEvent.timestamp).
                    timezone(manageDeviceEvent.timezone).
                    locale(manageDeviceEvent.locale).
                    deviceName(manageDeviceEvent.deviceName).
                    deviceType(manageDeviceEvent.deviceType).
                    deviceImageUrl(manageDeviceEvent.deviceImageUrl).
                    manageDeviceEventSource(manageDeviceEvent.manageDeviceEventSource).
                    fmipSource(manageDeviceEvent.fmipSource).
                    cardEvents(manageDeviceEvent.cardEvents);
        }
    }
}