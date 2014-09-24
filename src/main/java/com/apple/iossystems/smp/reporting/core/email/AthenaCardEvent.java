package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.AccountDataDescriptor;
import com.apple.iossystems.smp.domain.AthenaCardDescriptor;
import com.google.gson.GsonBuilder;

/**
 * @author Toch
 */
public class AthenaCardEvent
{
    private final String cardHolderName;
    private final String cardHolderEmail;
    private final String cardDisplayNumber;
    private final String deviceLanguage;
    private final String deviceName;
    private final String deviceType;
    private final String dsid;

    private AthenaCardEvent(Builder builder)
    {
        cardHolderName = builder.cardHolderName;
        cardHolderEmail = builder.cardHolderEmail;
        cardDisplayNumber = builder.cardDisplayNumber;
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

    public String getCardDisplayNumber()
    {
        return cardDisplayNumber;
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
        private String cardDisplayNumber;
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

        public Builder cardDisplayNumber(String value)
        {
            cardDisplayNumber = value;
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

    public static String toJson(String conversationId, AthenaCardDescriptor athenaCardDescriptor)
    {
        AthenaCardEvent athenaEvent = new Builder().cardHolderName(athenaCardDescriptor.getCardHolderName()).
                cardHolderEmail(CacheService.get(getCacheKey(conversationId))).
                cardDisplayNumber(athenaCardDescriptor.getLastFour()).
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

    private static final long CACHE_TIMEOUT = 15 * 60 * 1000;

    public static void cache(String conversationId, AccountDataDescriptor accountDataDescriptor)
    {
        if ((conversationId != null) && (accountDataDescriptor != null))
        {
            String emailAddress = accountDataDescriptor.getEmailAddress();

            if (emailAddress != null)
            {
                CacheService.put(getCacheKey(conversationId), emailAddress, CACHE_TIMEOUT);
            }
        }
    }

    private static String getCacheKey(String conversationId)
    {
        String key = null;

        if (conversationId != null)
        {
            key = "smp_reporting_athena_card_event_" + conversationId;
        }

        return key;
    }
}