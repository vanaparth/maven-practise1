package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.device.AbstractPass;
import com.apple.iossystems.smp.persistence.entity.PassbookPass;
import com.apple.iossystems.smp.persistence.entity.SecureElement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
class SMPEventDataServiceProxy
{
    private SMPEventDataServiceProxy()
    {
    }

    private static PassbookPass getPassbookPassByDpanId(String dpanId)
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

    public static PassbookPass getPassbookPass(ManageCardEvent manageCardEvent)
    {
        PassbookPass passbookPass = null;

        if (manageCardEvent != null)
        {
            List<CardEvent> cardEvents = manageCardEvent.getCardEvents();

            if (cardEvents != null)
            {
                for (CardEvent cardEvent : cardEvents)
                {
                    passbookPass = getPassbookPassByDpanId(cardEvent.getDpanId());

                    if (passbookPass != null)
                    {
                        break;
                    }
                }
            }
        }

        return passbookPass;
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

    public static String getCardHolderName(ProvisionCardEvent provisionCardEvent, ManageCardEvent manageCardEvent, PassbookPass passbookPass)
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

        if ((value == null) && (passbookPass != null))
        {
            value = passbookPass.getCardHolderName();
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

    public static String getDeviceName(ProvisionCardEvent provisionCardEvent, ManageCardEvent manageCardEvent, PassbookPass passbookPass, SecureElement secureElement)
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

        if ((value == null) && (passbookPass != null) && (secureElement != null))
        {
            value = SMPEventDataService.getDeviceName(passbookPass, secureElement);
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

    public static String getDeviceImageUrl(ManageCardEvent manageCardEvent)
    {
        return (manageCardEvent != null) ? manageCardEvent.getDeviceImageUrl() : null;
    }

    public static String getDsid(ProvisionCardEvent provisionCardEvent, ManageCardEvent manageCardEvent, PassbookPass passbookPass)
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

        if ((value == null) && (passbookPass != null))
        {
            value = passbookPass.getUserPrincipal();
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

    public static int getProvisionCount(SecureElement secureElement)
    {
        return ((secureElement != null) ? secureElement.getProvisioningCount() : 0);
    }

    public static boolean isFirstProvision(ProvisionCardEvent provisionCardEvent)
    {
        return ((provisionCardEvent != null) && provisionCardEvent.isFirstProvision());
    }

    public static List<Card> getCards(ProvisionCardEvent provisionCardEvent, ManageCardEvent manageCardEvent)
    {
        List<Card> cards;

        if (manageCardEvent != null)
        {
            cards = getCards(provisionCardEvent, manageCardEvent.getCardEvents());
        }
        else
        {
            cards = new ArrayList<Card>();
        }

        return cards;
    }

    private static List<Card> getCards(ProvisionCardEvent provisionCardEvent, List<CardEvent> cardEvents)
    {
        List<Card> cards = new ArrayList<Card>();

        if (cardEvents != null)
        {
            for (CardEvent cardEvent : cardEvents)
            {
                cards.add(getCard(provisionCardEvent, cardEvent));
            }
        }

        return cards;
    }

    private static Card getCard(ProvisionCardEvent provisionCardEvent, CardEvent cardEvent)
    {
        String cardDisplayNumber = null;
        String cardDescription = null;

        if (cardEvent != null)
        {
            String dpanId = cardEvent.getDpanId();

            if (dpanId != null)
            {
                PassbookPass passbookPass = SMPEventDataService.getPassByDpanId(dpanId);

                if (passbookPass != null)
                {
                    cardDisplayNumber = SMPEventDataService.getValueFromPassbookPass(passbookPass, AbstractPass.PAYMENT_PASS_FPAN_SUFFIX_KEY);
                    cardDescription = SMPEventDataService.getValueFromPassbookPass(passbookPass, AbstractPass.PAYMENT_PASS_LONG_DESC_KEY);

                    if (cardDescription == null)
                    {
                        cardDescription = SMPEventDataService.getValueFromPassbookPass(passbookPass, AbstractPass.PAYMENT_PASS_SHORT_DESC_KEY);
                    }
                }
            }

            if (cardDescription == null)
            {
                cardDescription = cardEvent.getCardDescription();
            }
        }

        if ((cardDisplayNumber == null) && (provisionCardEvent != null))
        {
            cardDisplayNumber = provisionCardEvent.getCardDisplayNumber();
        }

        if ((cardDisplayNumber == null) && (cardEvent != null))
        {
            cardDisplayNumber = cardEvent.getCardDisplayNumber();
        }

        return Card.getInstance(cardDescription, cardDisplayNumber, cardEvent);
    }
}