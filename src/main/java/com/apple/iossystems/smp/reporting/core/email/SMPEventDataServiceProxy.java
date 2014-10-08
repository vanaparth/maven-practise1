package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.device.AbstractPass;
import com.apple.iossystems.smp.persistence.entity.PassbookPass;
import com.apple.iossystems.smp.persistence.entity.SecureElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
public class SMPEventDataServiceProxy
{
    private SMPEventDataServiceProxy()
    {
    }

    public static PassbookPass getPassbookPassByDpanId(String dpanId)
    {
        return (dpanId != null) ? SMPEventDataService.getPassByDpanId(dpanId) : null;
    }

    private static SecureElement getSecureElementByDpanId(String dpanId)
    {
        return (dpanId != null) ? SMPEventDataService.getSecureElementByDpanId(dpanId) : null;
    }

    public static SecureElement getSecureElementBySeId(String seId)
    {
        return (seId != null) ? SMPEventDataService.getSecureElementBySeId(seId) : null;
    }

    public static int getProvisionCount(SecureElement secureElement)
    {
        return (secureElement != null) ? secureElement.getProvisioningCount() : 0;
    }

    public static boolean isFirstProvision(ProvisionCardEvent provisionCardEvent)
    {
        return (provisionCardEvent != null) && provisionCardEvent.isFirstProvision();
    }

    public static String getDeviceImageUrl(ManageCardEvent manageCardEvent)
    {
        return (manageCardEvent != null) ? manageCardEvent.getDeviceImageUrl() : null;
    }

    public static String getCardDisplayNumberFromPassbookPass(PassbookPass passbookPass)
    {
        return (passbookPass != null) ? passbookPass.getFpanSuffix() : null;
    }

    private static String getCardDescriptionFromDpanId(String dpanId)
    {
        return (dpanId != null) ? getCardDescriptionFromPassbookPass(getPassbookPassByDpanId(dpanId)) : null;
    }

    public static SecureElement getSecureElement(ManageCardEvent manageCardEvent)
    {
        SecureElement secureElement = null;

        if (manageCardEvent != null)
        {
            List<CardEvent> cardEvents = manageCardEvent.getCardEvents();

            if (cardEvents != null)
            {
                for (CardEvent cardEvent : cardEvents)
                {
                    secureElement = getSecureElementByDpanId(cardEvent.getDpanId());

                    if (secureElement != null)
                    {
                        break;
                    }
                }
            }
        }

        return secureElement;
    }

    public static String getCardHolderName(ProvisionCardEvent provisionCardEvent, ManageCardEvent manageCardEvent)
    {
        String value = null;

        if (provisionCardEvent != null)
        {
            value = provisionCardEvent.getCardHolderName();
        }

        if ((value == null) && (manageCardEvent != null))
        {
            value = manageCardEvent.getCardHolderName();
        }

        return value;
    }

    public static String getCardHolderEmail(ProvisionCardEvent provisionCardEvent, ManageCardEvent manageCardEvent)
    {
        String value = null;

        if (provisionCardEvent != null)
        {
            value = provisionCardEvent.getCardHolderEmail();
        }

        if ((value == null) && (manageCardEvent != null))
        {
            value = manageCardEvent.getCardHolderEmail();
        }

        return value;
    }

    public static String getDeviceName(ProvisionCardEvent provisionCardEvent, ManageCardEvent manageCardEvent)
    {
        String value = null;

        if (provisionCardEvent != null)
        {
            value = provisionCardEvent.getDeviceName();
        }

        if ((value == null) && (manageCardEvent != null))
        {
            value = manageCardEvent.getDeviceName();
        }

        return value;
    }

    public static String getDeviceType(ProvisionCardEvent provisionCardEvent, SecureElement secureElement)
    {
        String value = null;

        if (provisionCardEvent != null)
        {
            value = provisionCardEvent.getDeviceType();
        }

        if ((value == null) && (secureElement != null))
        {
            value = SMPEventDataService.getDeviceType(secureElement);
        }

        return value;
    }

    public static String getDsid(ProvisionCardEvent provisionCardEvent, ManageCardEvent manageCardEvent)
    {
        String value = null;

        if (provisionCardEvent != null)
        {
            value = provisionCardEvent.getDsid();
        }

        if ((value == null) && (manageCardEvent != null))
        {
            value = manageCardEvent.getDsid();
        }

        return value;
    }

    public static String getLocale(ProvisionCardEvent provisionCardEvent, ManageCardEvent manageCardEvent)
    {
        String value = null;

        if (provisionCardEvent != null)
        {
            value = provisionCardEvent.getLocale();
        }

        if ((value == null) && (manageCardEvent != null))
        {
            value = manageCardEvent.getLocale();
        }

        return value;
    }

    private static String getCardDisplayNumber(ProvisionCardEvent provisionCardEvent, CardEvent cardEvent)
    {
        String value = null;

        if (provisionCardEvent != null)
        {
            value = provisionCardEvent.getCardDisplayNumber();
        }

        if ((value == null) && (cardEvent != null))
        {
            value = cardEvent.getCardDisplayNumber();
        }

        return value;
    }

    private static String getCardDescription(CardEvent cardEvent)
    {
        String value = null;

        if (cardEvent != null)
        {
            value = cardEvent.getCardDescription();

            if (value == null)
            {
                value = getCardDescriptionFromDpanId(cardEvent.getDpanId());
            }
        }

        return value;
    }

    public static String getCardDescriptionFromPassbookPass(PassbookPass passbookPass)
    {
        String value = null;

        if (passbookPass != null)
        {
            value = SMPEventDataService.getValueFromPassbookPass(passbookPass, AbstractPass.PAYMENT_PASS_LONG_DESC_KEY);

            if (value == null)
            {
                value = SMPEventDataService.getValueFromPassbookPass(passbookPass, AbstractPass.PAYMENT_PASS_SHORT_DESC_KEY);
            }
        }

        return value;
    }

    private static Card getCard(ProvisionCardEvent provisionCardEvent, CardEvent cardEvent)
    {
        String cardDisplayNumber = getCardDisplayNumber(provisionCardEvent, cardEvent);
        String cardDescription = getCardDescription(cardEvent);

        return Card.getInstance(cardDisplayNumber, cardDescription, cardEvent);
    }

    public static List<Card> getCards(ProvisionCardEvent provisionCardEvent, ManageCardEvent manageCardEvent)
    {
        List<Card> cards = new ArrayList<Card>();

        if (manageCardEvent != null)
        {
            List<CardEvent> cardEvents = manageCardEvent.getCardEvents();

            if (cardEvents != null)
            {
                for (CardEvent cardEvent : cardEvents)
                {
                    cards.add(getCard(provisionCardEvent, cardEvent));
                }
            }
        }

        return cards;
    }
}