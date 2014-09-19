package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.domain.CardSource;
import com.apple.iossystems.smp.domain.FpanType;
import com.apple.iossystems.smp.domain.clm.Card;
import com.apple.iossystems.smp.domain.device.CardEligibilityStatus;

/**
 * @author Toch
 */
public class SMPEventCode
{
    private static final String UNKNOWN_CODE = "000";

    private SMPEventCode()
    {
    }

    public static void writeCode(EventRecord record, EventAttribute attribute, String code)
    {
        if (isValid(code))
        {
            record.setAttributeValue(attribute.key(), code);
        }
    }

    private static boolean isValid(String code)
    {
        return (!code.equals(UNKNOWN_CODE));
    }

    public static String getCardEligibilityStatusCode(CardEligibilityStatus cardStatus)
    {
        switch (cardStatus)
        {
            case INELIGIBLE:
                return "7";

            case ELIGIBLE:
                return "8";

            case NETWORK_UNAVAILABLE:
                return "9";

            case PROVISIONED:
                return "10";

            default:
                return UNKNOWN_CODE;
        }
    }

    public static String getCardSourceCode(CardSource cardSource)
    {
        switch (cardSource)
        {
            case USER_INPUT:
                return "1";

            case ON_FILE:
                return "2";

            case CLIENT_SDK:
                return "3";

            case ON_PASS:
                return "4";

            default:
                return UNKNOWN_CODE;
        }
    }

    public static String getFpanTypeCode(FpanType fpanType)
    {
        switch (fpanType)
        {
            case Credit:
                return "1";

            case Debit:
                return "2";

            default:
                return UNKNOWN_CODE;
        }
    }

    public static String getPNONameCode(String name)
    {
        if (name.equalsIgnoreCase("helium"))
        {
            return "201";
        }
        else if (name.equalsIgnoreCase("neon"))
        {
            return "202";
        }
        else if (name.equalsIgnoreCase("argon"))
        {
            return "203";
        }
        else if (name.equalsIgnoreCase("krypton"))
        {
            return "204";
        }
        else
        {
            return UNKNOWN_CODE;
        }
    }

    public static String getUseCaseTypeCode(String useCaseType)
    {
        if (useCaseType.equalsIgnoreCase("Passbook"))
        {
            return "1";
        }
        else
        {
            return UNKNOWN_CODE;
        }
    }

    public static String getProvisioningColorCode(String color)
    {
        if (color.equalsIgnoreCase("green"))
        {
            return "1";
        }
        else if (color.equalsIgnoreCase("yellow"))
        {
            return "2";
        }
        else if (color.equalsIgnoreCase("red"))
        {
            return "3";
        }
        else
        {
            return UNKNOWN_CODE;
        }
    }

    private enum CardStatus
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

        private CardStatus(Card.CardStatus cardStatus, String code)
        {
            this.cardStatus = cardStatus;
            this.code = code;
        }

        private static String getCode(Card.CardStatus cardStatus)
        {
            for (CardStatus e : CardStatus.values())
            {
                if (e.cardStatus == cardStatus)
                {
                    return e.code;
                }
            }

            return UNKNOWN_CODE;
        }

        private static Card.CardStatus getCardStatus(String code)
        {
            for (CardStatus e : CardStatus.values())
            {
                if (e.code == code)
                {
                    return e.cardStatus;
                }
            }

            return Card.CardStatus.UNKNOWN;
        }
    }

    public static String getCardStatusCode(Card.CardStatus cardStatus)
    {
        return CardStatus.getCode(cardStatus);
    }

    public static Card.CardStatus getCardStatus(String code)
    {
        return CardStatus.getCardStatus(code);
    }
}