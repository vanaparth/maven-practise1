package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.domain.ProvisioningCardSource;
import com.apple.iossystems.smp.domain.device.CardEligibilityStatus;
import com.apple.iossystems.smp.utils.SMPErrorEnum;

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

    public static String getFpanTypeCode(String fpanType)
    {
        String code = EMPTY_CODE;

        if (fpanType != null)
        {
            if (fpanType.equalsIgnoreCase("Credit"))
            {
                code = "1";
            }
            else if (fpanType.equalsIgnoreCase("Debit"))
            {
                code = "2";
            }
            else if (fpanType.equalsIgnoreCase("PrePaid"))
            {
                code = "3";
            }
            else if (fpanType.equalsIgnoreCase("PrivateLabel"))
            {
                code = "4";
            }
        }

        return code;
    }

    public static String getPNONameCode(String name)
    {
        String code = EMPTY_CODE;

        if (name != null)
        {
            if (name.equalsIgnoreCase("helium"))
            {
                code = "201";
            }
            else if (name.equalsIgnoreCase("neon"))
            {
                code = "202";
            }
            else if (name.equalsIgnoreCase("argon"))
            {
                code = "203";
            }
            else if (name.equalsIgnoreCase("krypton"))
            {
                code = "204";
            }
            else if (name.equalsIgnoreCase("xenon"))
            {
                code = "205";
            }
        }

        return code;
    }

    public static String getUseCaseTypeCode(String useCaseType)
    {
        String code = EMPTY_CODE;

        if (useCaseType != null)
        {
            if (useCaseType.equalsIgnoreCase("Passbook"))
            {
                code = "1";
            }
        }

        return code;
    }

    public static String getProvisioningColorCode(String color)
    {
        String code = EMPTY_CODE;

        if (color != null)
        {
            if (color.equalsIgnoreCase("green"))
            {
                code = "1";
            }
            else if (color.equalsIgnoreCase("yellow"))
            {
                code = "2";
            }
            else if (color.equalsIgnoreCase("red"))
            {
                code = "3";
            }
        }

        return code;
    }

    public static String getCardEligibilityStatusCode(CardEligibilityStatus cardStatus)
    {
        String code = EMPTY_CODE;

        if (cardStatus != null)
        {
            switch (cardStatus)
            {
                case INELIGIBLE:
                    code = "7";
                    break;

                case ELIGIBLE:
                    code = "8";
                    break;

                case NETWORK_UNAVAILABLE:
                    code = "9";
                    break;

                case PROVISIONED:
                    code = "10";
                    break;

                default:
                    code = EMPTY_CODE;
                    break;
            }
        }

        return code;
    }

    public static String getProvisioningCardSourceCode(ProvisioningCardSource provisioningCardSource)
    {
        String code = EMPTY_CODE;

        if (provisioningCardSource != null)
        {
            switch (provisioningCardSource)
            {
                case MANUAL:
                    code = "1";
                    break;

                case ON_FILE:
                    code = "2";
                    break;

                case BANKING_APP:
                    code = "3";
                    break;

                default:
                    code = EMPTY_CODE;
                    break;
            }
        }

        return code;
    }

    public static String getResponseStatus(String statusCode)
    {
        String code = EMPTY_CODE;

        if (statusCode != null)
        {
            SMPErrorEnum errorEnum = SMPErrorEnum.fromErrorCode(statusCode);

            if (errorEnum != null)
            {
                switch (errorEnum)
                {
                    case NO_ERROR:
                        code = EMPTY_CODE;
                        break;

                    case PAN_INELIGIBLE:
                    case INELIGIBLE_PAN:
                    case INVALID_PAN:
                        code = "1";
                        break;

                    case CVV_VERIFICATION_FAILED:
                    case CVV_VERIFICATION_FAILED_LEGACY:
                        code = "2";
                        break;

                    case FPAN_EXPIRED:
                        code = "3";
                        break;

                    case NAME_VERIFICATION_FAILED:
                        code = "4";
                        break;

                    default:
                        code = "5";
                        break;
                }
            }
        }

        return code;
    }
}