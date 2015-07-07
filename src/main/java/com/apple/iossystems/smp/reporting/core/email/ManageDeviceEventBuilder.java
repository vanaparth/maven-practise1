package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.Actor;
import com.apple.iossystems.smp.persistence.entity.SecureElement;
import com.apple.iossystems.smp.reporting.core.event.SMPEventDataServiceClient;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Toch
 */
public class ManageDeviceEventBuilder
{
    private static final String FIRST_NAME = "customerFirstName";
    private static final String LAST_NAME = "customerLastName";
    private static final String EMAIL = "customerEmail";
    private static final String LOCALE = "customerLocale";
    private static final String TIMEZONE = "customerTZ";
    private static final String DEVICE_NAME = "deviceName";
    private static final String DEVICE_IMAGE_URL = "deviceImageURL";

    private String timestamp;
    private String dsid;
    private Actor actor;
    private FmipSource fmipSource;
    private Map<String, String> cardData;
    private List<CardEvent> cardEvents;

    private static final SMPEventDataServiceClient SMP_EVENT_DATA_SERVICE_CLIENT = SMPEventDataServiceClient.getInstance();

    private ManageDeviceEventBuilder()
    {
    }

    public static ManageDeviceEventBuilder getInstance()
    {
        return new ManageDeviceEventBuilder();
    }

    public ManageDeviceEventBuilder timestamp(String value)
    {
        timestamp = value;
        return this;
    }

    public ManageDeviceEventBuilder dsid(String value)
    {
        dsid = value;
        return this;
    }

    public ManageDeviceEventBuilder actor(Actor value)
    {
        actor = value;
        return this;
    }

    public ManageDeviceEventBuilder fmipSource(BigInteger value)
    {
        fmipSource = FmipSource.fromCertificate(value);
        return this;
    }

    public ManageDeviceEventBuilder fmipSourceFromRequestReason(String value)
    {
        fmipSource = FmipSource.fromRequestReason(value);
        return this;
    }

    public ManageDeviceEventBuilder cardData(Map<String, String> value)
    {
        cardData = value;
        return this;
    }

    public ManageDeviceEventBuilder cardEvents(List<CardEvent> value)
    {
        cardEvents = value;
        return this;
    }

    public ManageDeviceEvent build()
    {
        if (cardData == null)
        {
            cardData = new HashMap<>();
        }

        if (cardEvents == null)
        {
            cardEvents = new ArrayList<>();
        }

        String firstName = cardData.get(FIRST_NAME);
        String lastName = cardData.get(LAST_NAME);
        String cardHolderEmail = cardData.get(EMAIL);
        String timezone = cardData.get(TIMEZONE);
        String locale = cardData.get(LOCALE);
        String deviceName = cardData.get(DEVICE_NAME);
        String deviceImageUrl = cardData.get(DEVICE_IMAGE_URL);

        return ManageDeviceEvent.getBuilder().cardHolderName(getCardHolderName(firstName, lastName)).
                cardHolderEmail(cardHolderEmail).
                dsid(dsid).
                timestamp(timestamp).
                timezone(timezone).
                locale(locale).
                deviceName(deviceName).
                deviceType(getDeviceType(cardEvents)).
                deviceImageUrl(deviceImageUrl).
                manageDeviceEventSource(ManageDeviceEventSource.fromActor(actor)).
                fmipSource(fmipSource).
                cardEvents(cardEvents).build();
    }

    private String getCardHolderName(String firstName, String lastName)
    {
        StringBuilder cardHolderName = new StringBuilder();

        if (StringUtils.isNotBlank(firstName))
        {
            cardHolderName.append(firstName);
        }

        if (StringUtils.isNotBlank(lastName))
        {
            if (cardHolderName.length() > 0)
            {
                cardHolderName.append(" ");
            }

            cardHolderName.append(lastName);
        }

        return cardHolderName.toString();
    }

    private String getDeviceType(List<CardEvent> cardEvents)
    {
        SecureElement secureElement = null;

        for (CardEvent cardEvent : cardEvents)
        {
            secureElement = SMP_EVENT_DATA_SERVICE_CLIENT.getSecureElementByDpanId(cardEvent.getDpanId());

            if (secureElement != null)
            {
                break;
            }
        }

        return (secureElement != null) ? SMP_EVENT_DATA_SERVICE_CLIENT.getDeviceType(secureElement) : null;
    }
}