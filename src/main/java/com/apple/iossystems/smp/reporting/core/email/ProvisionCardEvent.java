package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.AthenaCardDescriptor;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class ProvisionCardEvent
{
    private final String cardHolderName;
    private final String cardHolderEmail;
    private final String cardDisplayNumber;
    private final String deviceName;
    private final String deviceType;
    private final String dsid;
    private final String locale;
    private final boolean firstProvision;

    private ProvisionCardEvent(Builder builder)
    {
        cardHolderName = builder.cardHolderName;
        cardHolderEmail = builder.cardHolderEmail;
        cardDisplayNumber = builder.cardDisplayNumber;
        deviceName = builder.deviceName;
        deviceType = builder.deviceType;
        dsid = builder.dsid;
        locale = builder.locale;
        firstProvision = builder.firstProvision;
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

    public String getDsid()
    {
        return dsid;
    }

    public String getLocale()
    {
        return locale;
    }

    public boolean isFirstProvision()
    {
        return firstProvision;
    }

    private static class Builder
    {
        private String cardHolderName;
        private String cardHolderEmail;
        private String cardDisplayNumber;
        private String deviceName;
        private String deviceType;
        private String dsid;
        private String locale;
        private boolean firstProvision;

        private Builder()
        {
        }

        private Builder cardHolderName(String value)
        {
            cardHolderName = value;
            return this;
        }

        private Builder cardHolderEmail(String value)
        {
            cardHolderEmail = value;
            return this;
        }

        private Builder cardDisplayNumber(String value)
        {
            cardDisplayNumber = value;
            return this;
        }

        private Builder deviceName(String value)
        {
            deviceName = value;
            return this;
        }

        private Builder deviceType(String value)
        {
            deviceType = value;
            return this;
        }

        private Builder dsid(String value)
        {
            dsid = value;
            return this;
        }

        private Builder locale(String value)
        {
            locale = value;
            return this;
        }

        private Builder firstProvision(boolean value)
        {
            firstProvision = value;
            return this;
        }

        private ProvisionCardEvent build()
        {
            return new ProvisionCardEvent(this);
        }
    }

    public static ProvisionCardEvent fromJson(String json)
    {
        if (json != null)
        {
            return new GsonBuilder().create().fromJson(json, ProvisionCardEvent.class);
        }
        else
        {
            return new Builder().build();
        }
    }

    public static String toJson(String conversationId, AthenaCardDescriptor athenaCardDescriptor)
    {
        ProvisionCardEvent provisionCardEvent;

        if ((conversationId != null) && (athenaCardDescriptor != null))
        {
            provisionCardEvent = new Builder().cardHolderName(athenaCardDescriptor.getCardHolderName()).
                    cardHolderEmail(NetworkCheckCardEventCache.get(NetworkCheckCardEventCache.Attribute.EMAIL, conversationId)).
                    cardDisplayNumber(athenaCardDescriptor.getLastFour()).
                    deviceName(athenaCardDescriptor.getDeviceName()).
                    deviceType(NetworkCheckCardEventCache.get(NetworkCheckCardEventCache.Attribute.DEVICE_TYPE, conversationId)).
                    dsid(athenaCardDescriptor.getDsId()).
                    locale(NetworkCheckCardEventCache.get(NetworkCheckCardEventCache.Attribute.LOCALE, conversationId)).
                    firstProvision(getFirstProvisionStatus(athenaCardDescriptor.getSeid())).build();
        }
        else
        {
            provisionCardEvent = new Builder().build();
        }

        return new GsonBuilder().create().toJson(provisionCardEvent);
    }

    private static final Logger LOGGER = Logger.getLogger(ProvisionCardEvent.class);

    private static boolean getFirstProvisionStatus(String seId)
    {
        boolean firstProvision = false;

        try
        {
            firstProvision = ((SMPEventDataServiceProxy.getProvisionCount(SMPEventDataServiceProxy.getSecureElementBySeId(seId))) == 0);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return firstProvision;
    }
}