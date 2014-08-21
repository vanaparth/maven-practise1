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
    private SMPEventCode()
    {
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
                return "0";
        }
    }

    public static String getCardStatusCode(Card.CardStatus cardStatus)
    {
        switch (cardStatus)
        {
            case ACTIVE:
                return "1";

            case SUSPENDED:
                return "2";

            case UNLINKED:
                return "3";

            case SUSPENDED_OTP:
                return "4";

            case SUSPENDED_ISSUER:
                return "5";

            case SUSPENDED_WALLET:
                return "6";

            default:
                return "0";
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
                return "0";
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
                return "0";
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
            return "0";
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
            return "0";
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
            return "0";
        }
    }
}