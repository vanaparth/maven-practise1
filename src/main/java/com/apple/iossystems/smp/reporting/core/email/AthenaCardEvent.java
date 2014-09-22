package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.AthenaCardDescriptor;
import com.google.gson.GsonBuilder;

/**
 * @author Toch
 */
public class AthenaCardEvent
{
    private final String cardHolderName;
    private final String cardHolderEmail;
    private final String cardDescription;
    private final String deviceLanguage;
    private final String deviceName;
    private final String deviceType;
    private final String dsid;

    private AthenaCardEvent(Builder builder)
    {
        cardHolderName = builder.cardHolderName;
        cardHolderEmail = builder.cardHolderEmail;
        cardDescription = builder.cardDescription;
        deviceLanguage = builder.deviceLanguage;
        deviceName = builder.deviceName;
        deviceType = builder.deviceType;
        dsid = builder.dsid;
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

    public String getDeviceLanguage()
    {
        return deviceLanguage;
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

    private static class Builder
    {
        private String cardHolderName;
        private String cardHolderEmail;
        private String cardDescription;
        private String deviceLanguage;
        private String deviceName;
        private String deviceType;
        private String dsid;

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

        public Builder deviceLanguage(String value)
        {
            deviceLanguage = value;
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

        public AthenaCardEvent build()
        {
            return new AthenaCardEvent(this);
        }
    }

    public static String toJson(AthenaCardDescriptor athenaCardDescriptor)
    {
        AthenaCardEvent athenaEvent = new Builder().cardHolderName(athenaCardDescriptor.getCardHolderName()).
                cardDescription(athenaCardDescriptor.getLastFour()).
                deviceLanguage(athenaCardDescriptor.getDeviceLanguage()).
                deviceName(athenaCardDescriptor.getDeviceName()).
                deviceType(athenaCardDescriptor.getDeviceType()).
                dsid(athenaCardDescriptor.getDsId()).build();

        return new GsonBuilder().create().toJson(athenaEvent);
    }

    public static AthenaCardEvent fromJson(String json)
    {
        if (json != null)
        {
            return new GsonBuilder().create().fromJson(json, AthenaCardEvent.class);
        }
        else
        {
            return new Builder().build();
        }
    }
}