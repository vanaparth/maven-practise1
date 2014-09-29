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

    public static PassbookPass getPassbookPass(String dpanId)
    {
        return (dpanId != null) ? SMPEventDataService.getPassByDpanId(dpanId) : null;
    }

    public static SecureElement getSecureElement(String dpanId)
    {
        return (dpanId != null) ? SMPEventDataService.getSecureElement(dpanId) : null;
    }

    public static String getCardHolderName(AthenaCardEvent athenaCardEvent, ManageCardEvent manageCardEvent, PassbookPass passbookPass)
    {
        String value = null;

        if (athenaCardEvent != null)
        {
            value = athenaCardEvent.getCardHolderName();
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

    public static String getCardHolderEmail(AthenaCardEvent athenaCardEvent, ManageCardEvent manageCardEvent)
    {
        String value = null;

        if (athenaCardEvent != null)
        {
            value = athenaCardEvent.getCardHolderEmail();
        }

        if ((value == null) && (manageCardEvent != null))
        {
            value = manageCardEvent.getCardHolderEmail();
        }

        return value;
    }

    public static String getDeviceName(AthenaCardEvent athenaCardEvent, ManageCardEvent manageCardEvent, PassbookPass passbookPass, SecureElement secureElement)
    {
        String value = null;

        if (athenaCardEvent != null)
        {
            value = athenaCardEvent.getDeviceName();
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

    public static String getDeviceType(AthenaCardEvent athenaCardEvent, SecureElement secureElement)
    {
        String value = null;

        if (athenaCardEvent != null)
        {
            value = athenaCardEvent.getDeviceType();
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

    public static String getDsid(AthenaCardEvent athenaCardEvent, PassbookPass passbookPass)
    {
        String value = null;

        if (athenaCardEvent != null)
        {
            value = athenaCardEvent.getDsid();
        }

        if ((value == null) && (passbookPass != null))
        {
            value = passbookPass.getUserPrincipal();
        }

        return value;
    }

    public static String getLocale(AthenaCardEvent athenaCardEvent, ManageCardEvent manageCardEvent)
    {
        String value = null;

        if (athenaCardEvent != null)
        {
            value = athenaCardEvent.getDeviceLanguage();
        }

        if ((value == null) && (manageCardEvent != null))
        {
            value = manageCardEvent.getLocale();
        }

        return value;
    }

    public static boolean isFirstProvision(SecureElement secureElement)
    {
        return ((secureElement != null) && (secureElement.getProvisioningCount() == 1));
    }

    public static String getDpanId(List<CardEvent> cardEvents)
    {
        String value = null;

        if (cardEvents != null)
        {
            for (CardEvent cardEvent : cardEvents)
            {
                value = cardEvent.getDpanId();

                if (value != null)
                {
                    break;
                }
            }
        }

        return value;
    }

    public static List<Card> getCards(List<CardEvent> cardEvents)
    {
        List<Card> cards = new ArrayList<Card>();

        if (cardEvents != null)
        {
            for (CardEvent cardEvent : cardEvents)
            {
                cards.add(getCard(cardEvent));
            }
        }

        return cards;
    }

    private static Card getCard(CardEvent cardEvent)
    {
        String cardDisplayNumber = null;
        String cardDescription = null;

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

        return Card.getInstance(cardDescription, cardDisplayNumber, cardEvent);
    }
}