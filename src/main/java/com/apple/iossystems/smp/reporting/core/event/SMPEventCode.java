package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.domain.CardSource;
import com.apple.iossystems.smp.domain.FpanType;
import com.apple.iossystems.smp.domain.device.CardEligibilityStatus;
import com.apple.iossystems.smp.utils.SMPErrorEnum;
import org.apache.commons.lang.StringUtils;

/**
 * @author Toch
 */
public class SMPEventCode
{
    private static final String EMPTY_CODE = "";

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
        return (!code.equals(EMPTY_CODE));
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
                return EMPTY_CODE;
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
                return EMPTY_CODE;
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
                return EMPTY_CODE;
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
            return EMPTY_CODE;
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
            return EMPTY_CODE;
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
            return EMPTY_CODE;
        }
    }

    public static String getResponseStatus(String statusCode)
    {
        if (StringUtils.isBlank(statusCode))
        {
            return EMPTY_CODE;
        }

        SMPErrorEnum errorEnum = SMPErrorEnum.fromErrorCode(statusCode);

        switch (errorEnum)
        {
            case NO_ERROR:
                return EMPTY_CODE;

            case PAN_INELIGIBLE:
            case INELIGIBLE_PAN:
            case INVALID_PAN:
                return "1";

            case CVV_VERIFICATION_FAILED:
            case CVV_VERIFICATION_FAILED_LEGACY:
                return "2";

            case FPAN_EXPIRED:
                return "3";

            case NAME_VERIFICATION_FAILED:
                return "4";

            default:
                return "5";
        }
    }
}