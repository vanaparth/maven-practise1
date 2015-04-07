package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.clm.Card;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.SMPDeviceEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class CardEventStatus
{
    private static final Card.CardStatus[] VALID_SUSPEND_STATUS = {Card.CardStatus.SUSPENDED, Card.CardStatus.SUSPENDED_ISSUER, Card.CardStatus.SUSPENDED_OTP, Card.CardStatus.SUSPENDED_WALLET};

    private static final Card.CardStatus[] VALID_UNLINKED_STATUS = {Card.CardStatus.UNLINKED};

    private static final Card.CardStatus[] VALID_RESUME_STATUS = {Card.CardStatus.ACTIVE};

    private static final Map<String, Card.CardStatus> CARD_STATUS_MAP = getCardStatusMap();

    private CardEventStatus()
    {
    }

    private static Map<String, Card.CardStatus> getCardStatusMap()
    {
        Map<String, Card.CardStatus> map = new HashMap<>();

        map.put("1", Card.CardStatus.ACTIVE);
        map.put("2", Card.CardStatus.SUSPENDED);
        map.put("3", Card.CardStatus.UNLINKED);
        map.put("4", Card.CardStatus.SUSPENDED_OTP);
        map.put("5", Card.CardStatus.SUSPENDED_ISSUER);
        map.put("6", Card.CardStatus.SUSPENDED_WALLET);

        return map;
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

    private static Card.CardStatus getCardStatus(String code)
    {
        Card.CardStatus cardStatus = CARD_STATUS_MAP.get(code);

        return ((cardStatus != null) ? cardStatus : Card.CardStatus.UNKNOWN);
    }

    public static boolean hasValidCardStatus(EventRecord record)
    {
        SMPDeviceEvent smpEvent = SMPDeviceEvent.getSMPEvent(record);

        Card.CardStatus[] validCardStatusList = null;

        if (smpEvent == SMPDeviceEvent.SUSPEND_CARD)
        {
            validCardStatusList = VALID_SUSPEND_STATUS;
        }
        else if (smpEvent == SMPDeviceEvent.UNLINK_CARD)
        {
            validCardStatusList = VALID_UNLINKED_STATUS;
        }
        else if (smpEvent == SMPDeviceEvent.RESUME_CARD)
        {
            validCardStatusList = VALID_RESUME_STATUS;
        }

        Card.CardStatus cardStatus = getCardStatus(record.getAttributeValue(EventAttribute.CARD_STATUS.key()));

        return ((validCardStatusList == null) || isValidStatus(cardStatus, validCardStatusList));
    }

    public static Card.CardStatus getDefaultValidCardStatus(EventRecord record)
    {
        SMPDeviceEvent smpEvent = SMPDeviceEvent.getSMPEvent(record);

        Card.CardStatus defaultValidCardStatus;

        if (smpEvent == SMPDeviceEvent.SUSPEND_CARD)
        {
            defaultValidCardStatus = Card.CardStatus.SUSPENDED;
        }
        else if (smpEvent == SMPDeviceEvent.UNLINK_CARD)
        {
            defaultValidCardStatus = Card.CardStatus.UNLINKED;
        }
        else if (smpEvent == SMPDeviceEvent.RESUME_CARD)
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