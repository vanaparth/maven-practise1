package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.domain.ProvisioningCardSource;
import com.apple.iossystems.smp.domain.clm.Card;
import com.apple.iossystems.smp.domain.device.CardEligibilityStatus;
import com.apple.iossystems.smp.utils.SMPErrorEnum;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class SMPEventCode
{
    private static final String EMPTY_CODE = "";

    private static final Map<String, String> PNO_MAP = new HashMap<>();
    private static final Map<String, String> COLOR_MAP = new HashMap<>();
    private static final Map<String, String> USE_CASE_TYPE_MAP = new HashMap<>();
    private static final Map<String, String> FPAN_TYPE_MAP = new HashMap<>();
    private static final Map<String, String> CARD_STATUS_MAP = new HashMap<>();
    private static final Map<String, String> CARD_ELIGIBILITY_STATUS_MAP = new HashMap<>();
    private static final Map<String, String> PROVISIONING_CARD_SOURCE_MAP = new HashMap<>();

    static
    {
        addToMap(PNO_MAP, "helium", "201");
        addToMap(PNO_MAP, "neon", "202");
        addToMap(PNO_MAP, "argon", "203");
        addToMap(PNO_MAP, "krypton", "204");
        addToMap(PNO_MAP, "xenon", "205");
        //
        addToMap(COLOR_MAP, "green", "1");
        addToMap(COLOR_MAP, "yellow", "2");
        addToMap(COLOR_MAP, "red", "3");
        //
        addToMap(USE_CASE_TYPE_MAP, "Passbook", "1");
        //
        addToMap(FPAN_TYPE_MAP, "Credit", "1");
        addToMap(FPAN_TYPE_MAP, "Debit", "2");
        addToMap(FPAN_TYPE_MAP, "PrePaid", "3");
        addToMap(FPAN_TYPE_MAP, "PrivateLabel", "4");
        //
        addToMap(CARD_STATUS_MAP, Card.CardStatus.ACTIVE.toString(), "1");
        addToMap(CARD_STATUS_MAP, Card.CardStatus.SUSPENDED.toString(), "2");
        addToMap(CARD_STATUS_MAP, Card.CardStatus.UNLINKED.toString(), "3");
        addToMap(CARD_STATUS_MAP, Card.CardStatus.SUSPENDED_OTP.toString(), "4");
        addToMap(CARD_STATUS_MAP, Card.CardStatus.SUSPENDED_ISSUER.toString(), "5");
        addToMap(CARD_STATUS_MAP, Card.CardStatus.SUSPENDED_WALLET.toString(), "6");
        //
        addToMap(CARD_ELIGIBILITY_STATUS_MAP, String.valueOf(CardEligibilityStatus.INELIGIBLE.getId()), "7");
        addToMap(CARD_ELIGIBILITY_STATUS_MAP, String.valueOf(CardEligibilityStatus.ELIGIBLE.getId()), "8");
        addToMap(CARD_ELIGIBILITY_STATUS_MAP, String.valueOf(CardEligibilityStatus.NETWORK_UNAVAILABLE.getId()), "9");
        addToMap(CARD_ELIGIBILITY_STATUS_MAP, String.valueOf(CardEligibilityStatus.PROVISIONED.getId()), "10");
        addToMap(CARD_ELIGIBILITY_STATUS_MAP, String.valueOf(CardEligibilityStatus.NEWER_OS_REQUIRED.getId()), "11");
        //
        addToMap(PROVISIONING_CARD_SOURCE_MAP, String.valueOf(ProvisioningCardSource.MANUAL.getId()), "1");
        addToMap(PROVISIONING_CARD_SOURCE_MAP, String.valueOf(ProvisioningCardSource.ON_FILE.getId()), "2");
        addToMap(PROVISIONING_CARD_SOURCE_MAP, String.valueOf(ProvisioningCardSource.BANKING_APP.getId()), "3");
        addToMap(PROVISIONING_CARD_SOURCE_MAP, String.valueOf(ProvisioningCardSource.PASS.getId()), "4");
    }

    private SMPEventCode()
    {
    }

    private static void addToMap(Map<String, String> map, String key, String value)
    {
        map.put(key.toLowerCase(), value);
    }

    private static boolean isValid(String code)
    {
        return (!code.equals(EMPTY_CODE));
    }

    private static void writeCode(EventRecord record, EventAttribute attribute, Map<String, String> map, String key)
    {
        writeCode(record, attribute, getCode(map, key));
    }

    private static void writeCode(EventRecord record, EventAttribute attribute, String code)
    {
        if (isValid(code))
        {
            record.setAttributeValue(attribute.key(), code);
        }
    }

    private static String getCode(Map<String, String> map, String key)
    {
        if (key != null)
        {
            key = key.toLowerCase();
        }

        String value = map.get(key);

        return ((value != null) ? value : EMPTY_CODE);
    }

    public static void writePNOName(EventRecord record, EventAttribute attribute, String pnoName)
    {
        writeCode(record, attribute, PNO_MAP, pnoName);
    }

    public static void writeProvisioningColor(EventRecord record, EventAttribute attribute, String color)
    {
        writeCode(record, attribute, COLOR_MAP, color);
    }

    public static void writeUseCaseType(EventRecord record, EventAttribute attribute, String useCaseType)
    {
        writeCode(record, attribute, USE_CASE_TYPE_MAP, useCaseType);
    }

    public static void writeFpanType(EventRecord record, EventAttribute attribute, String fpanType)
    {
        writeCode(record, attribute, FPAN_TYPE_MAP, fpanType);
    }

    public static void writeCardStatus(EventRecord record, EventAttribute attribute, Card.CardStatus cardStatus)
    {
        writeCode(record, attribute, CARD_STATUS_MAP, (cardStatus != null) ? cardStatus.toString() : "");
    }

    public static void writeCardEligibilityStatus(EventRecord record, EventAttribute attribute, CardEligibilityStatus cardEligibilityStatus)
    {
        writeCode(record, attribute, CARD_ELIGIBILITY_STATUS_MAP, (cardEligibilityStatus != null) ? String.valueOf(cardEligibilityStatus.getId()) : "");
    }

    public static void writeProvisioningCardSource(EventRecord record, EventAttribute attribute, ProvisioningCardSource provisioningCardSource)
    {
        writeCode(record, attribute, PROVISIONING_CARD_SOURCE_MAP, (provisioningCardSource != null) ? String.valueOf(provisioningCardSource.getId()) : "");
    }

    public static void writeResponseStatus(EventRecord record, EventAttribute attribute, String errorCode)
    {
        String responseStatusCode = null;

        if (StringUtils.isNotBlank(errorCode))
        {
            SMPErrorEnum errorEnum = SMPErrorEnum.fromErrorCode(errorCode);

            if (errorEnum != null)
            {
                switch (errorEnum)
                {
                    case NO_ERROR:
                        responseStatusCode = EMPTY_CODE;
                        break;

                    case PAN_INELIGIBLE:
                    case INELIGIBLE_PAN:
                    case INVALID_PAN:
                        responseStatusCode = "1";
                        break;

                    case CVV_VERIFICATION_FAILED:
                    case CVV_VERIFICATION_FAILED_LEGACY:
                        responseStatusCode = "2";
                        break;

                    case FPAN_EXPIRED:
                        responseStatusCode = "3";
                        break;

                    case NAME_VERIFICATION_FAILED:
                        responseStatusCode = "4";
                        break;

                    default:
                        responseStatusCode = "5";
                        break;
                }
            }
        }

        writeCode(record, attribute, (responseStatusCode != null) ? responseStatusCode : EMPTY_CODE);
    }
}