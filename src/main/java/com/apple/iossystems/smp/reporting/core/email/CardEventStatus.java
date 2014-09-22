package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.clm.Card;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.SMPCardEvent;
import com.apple.iossystems.smp.reporting.core.event.SMPEventCode;

/**
 * @author Toch
 */
class CardEventStatus
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

    public static boolean hasValidCardStatus(EventRecord record)
    {
        SMPCardEvent cardEvent = SMPCardEvent.getSMPCardEvent(record.getAttributeValue(EventAttribute.CARD_EVENT.key()));

        Card.CardStatus[] validCardStatusList = null;

        if (cardEvent == SMPCardEvent.SUSPEND_CARD)
        {
            validCardStatusList = VALID_SUSPEND_STATUS;
        }
        else if (cardEvent == SMPCardEvent.UNLINK_CARD)
        {
            validCardStatusList = VALID_UNLINKED_STATUS;
        }
        else if (cardEvent == SMPCardEvent.RESUME_CARD)
        {
            validCardStatusList = VALID_RESUME_STATUS;
        }

        Card.CardStatus cardStatus = SMPEventCode.getCardStatus(record.getAttributeValue(EventAttribute.CARD_STATUS.key()));

        return (validCardStatusList == null) ? true : isValidStatus(cardStatus, validCardStatusList);
    }
}