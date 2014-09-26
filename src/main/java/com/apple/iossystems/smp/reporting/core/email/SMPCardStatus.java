package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.clm.Card;

/**
 * @author Toch
 */
public enum SMPCardStatus
{
    ACTIVE(Card.CardStatus.ACTIVE, "1"),
    SUSPENDED(Card.CardStatus.SUSPENDED, "2"),
    UNLINKED(Card.CardStatus.UNLINKED, "3"),
    SUSPENDED_OTP(Card.CardStatus.SUSPENDED_OTP, "4"),
    SUSPENDED_ISSUER(Card.CardStatus.SUSPENDED_ISSUER, "5"),
    SUSPENDED_WALLET(Card.CardStatus.SUSPENDED_WALLET, "6"),
    UNKNOWN(Card.CardStatus.UNKNOWN, "0");

    private final Card.CardStatus cardStatus;
    private final String code;

    private SMPCardStatus(Card.CardStatus cardStatus, String code)
    {
        this.cardStatus = cardStatus;
        this.code = code;
    }

    private static SMPCardStatus getUnknownStatus()
    {
        return UNKNOWN;
    }

    public static String getCode(Card.CardStatus cardStatus)
    {
        for (SMPCardStatus e : SMPCardStatus.values())
        {
            if (e.cardStatus == cardStatus)
            {
                return e.code;
            }
        }

        return getUnknownStatus().code;
    }

    public static Card.CardStatus getCardStatus(String code)
    {
        for (SMPCardStatus e : SMPCardStatus.values())
        {
            if (e.code.equals(code))
            {
                return e.cardStatus;
            }
        }

        return getUnknownStatus().cardStatus;
    }
}