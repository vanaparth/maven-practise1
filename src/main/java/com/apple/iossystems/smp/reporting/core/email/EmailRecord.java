package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.event.SMPCardEvent;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Toch
 */
class EmailRecord
{
    private final SMPCardEvent smpCardEvent;
    private final String conversationId;
    private final String timestamp;

    private final String cardHolderName;
    private final String cardHolderEmail;
    private final String deviceName;
    private final String deviceType;
    private final String deviceImageUrl;
    private final String dsid;
    private final String locale;

    private final boolean firstProvisionEvent;
    private final List<Card> cards;

    private final ManageCardEvent manageCardEvent;

    private EmailRecord(Builder builder)
    {
        smpCardEvent = builder.smpCardEvent;
        conversationId = builder.conversationId;
        timestamp = builder.timestamp;

        cardHolderName = builder.cardHolderName;
        cardHolderEmail = builder.cardHolderEmail;
        deviceName = builder.deviceName;
        deviceType = builder.deviceType;
        deviceImageUrl = builder.deviceImageUrl;
        dsid = builder.dsid;
        locale = builder.locale;

        firstProvisionEvent = builder.firstProvisionEvent;
        cards = builder.cards;

        manageCardEvent = builder.manageCardEvent;
    }

    public SMPCardEvent getSMPCardEvent()
    {
        return smpCardEvent;
    }

    public String getConversationId()
    {
        return conversationId;
    }

    public String getCardHolderName()
    {
        return cardHolderName;
    }

    public String getCardHolderEmail()
    {
        return cardHolderEmail;
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

    public String getDsid()
    {
        return dsid;
    }

    public String getLocale()
    {
        return locale;
    }

    public boolean isFirstProvisionEvent()
    {
        return firstProvisionEvent;
    }

    public List<Card> getCards()
    {
        return cards;
    }

    public ManageCardEvent getManageCardEvent()
    {
        return manageCardEvent;
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
        private SMPCardEvent smpCardEvent;
        private String conversationId;
        private String timestamp;

        private String cardHolderName;
        private String cardHolderEmail;
        private String deviceName;
        private String deviceType;
        private String deviceImageUrl;
        private String dsid;
        private String locale;

        private boolean firstProvisionEvent;
        private List<Card> cards;

        private ManageCardEvent manageCardEvent;

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

        public Builder firstProvisionEvent(boolean value)
        {
            firstProvisionEvent = value;
            return this;
        }

        public Builder cards(List<Card> value)
        {
            cards = value;
            return this;
        }

        public Builder manageCardEvent(ManageCardEvent value)
        {
            manageCardEvent = value;
            return this;
        }

        private void validate()
        {
            if (timestamp == null)
            {
                timestamp = String.valueOf(System.currentTimeMillis());
            }

            if (deviceImageUrl == null)
            {
                deviceImageUrl = "https://statici.icloud.com/fmipmobile/deviceImages-4.0/iPhone/iPhone7,1-e1e4e3-e1ccb5/online-nolocation_iphone__2x.png";
            }
        }

        public EmailRecord build()
        {
            validate();

            return new EmailRecord(this);
        }
    }
}