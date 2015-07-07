package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.domain.clm.Card;

/**
 * @author Toch
 */
public class CardEventStatus
{
    private static final Card.CardStatus[] VALID_SUSPEND_STATUS = {Card.CardStatus.SUSPENDED, Card.CardStatus.SUSPENDED_ISSUER, Card.CardStatus.SUSPENDED_OTP, Card.CardStatus.SUSPENDED_WALLET};

    private static final Card.CardStatus[] VALID_UNLINKED_STATUS = {Card.CardStatus.UNLINKED};

    private static final Card.CardStatus[] VALID_RESUME_STATUS = {Card.CardStatus.ACTIVE};

    private CardEventStatus()
    {
    }

    private static boolean isValidStatus(Card.CardStatus cardStatus, Card.CardStatus[] validCardStatusList)
    {
        for (Card.CardStatus validCardStatus : validCardStatusList)
        {
            if (cardStatus == validCardStatus)
            {
                return true;
            }
        }

        return false;
    }

    public static boolean hasValidCardStatus(EventRecord record, Card card)
    {
        SMPDeviceEvent smpDeviceEvent = SMPDeviceEvent.getEvent(record);

        Card.CardStatus[] validCardStatusList = null;

        if (smpDeviceEvent == SMPDeviceEvent.SUSPEND_CARD)
        {
            validCardStatusList = VALID_SUSPEND_STATUS;
        }
        else if (smpDeviceEvent == SMPDeviceEvent.UNLINK_CARD)
        {
            validCardStatusList = VALID_UNLINKED_STATUS;
        }
        else if (smpDeviceEvent == SMPDeviceEvent.RESUME_CARD)
        {
            validCardStatusList = VALID_RESUME_STATUS;
        }

        return ((validCardStatusList == null) || isValidStatus(card.getCurrentStatus(), validCardStatusList));
    }

    public static Card.CardStatus getDefaultValidCardStatus(EventRecord record)
    {
        SMPDeviceEvent smpDeviceEvent = SMPDeviceEvent.getEvent(record);

        Card.CardStatus defaultValidCardStatus;

        if (smpDeviceEvent == SMPDeviceEvent.SUSPEND_CARD)
        {
            defaultValidCardStatus = Card.CardStatus.SUSPENDED;
        }
        else if (smpDeviceEvent == SMPDeviceEvent.UNLINK_CARD)
        {
            defaultValidCardStatus = Card.CardStatus.UNLINKED;
        }
        else if (smpDeviceEvent == SMPDeviceEvent.RESUME_CARD)
        {
            defaultValidCardStatus = Card.CardStatus.ACTIVE;
        }
        else
        {
            defaultValidCardStatus = Card.CardStatus.ACTIVE;
        }

        return defaultValidCardStatus;
    }
}