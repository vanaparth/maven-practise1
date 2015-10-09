package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.domain.ProvisioningCardSource;
import com.apple.iossystems.smp.domain.clm.Card;
import com.apple.iossystems.smp.domain.device.CardEligibilityStatus;
import com.apple.iossystems.smp.pno.PNORepository;
import com.apple.iossystems.smp.utils.SMPErrorEnum;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class SMPEventCode
{
    private static final SMPEventCode INSTANCE = new SMPEventCode();

    private final Map<String, String> pnoMap = new HashMap<>();
    private final Map<String, String> colorMap = new HashMap<>();
    private final Map<String, String> useCaseTypeMap = new HashMap<>();
    private final Map<String, String> fpanTypeMap = new HashMap<>();
    private final Map<String, String> cardStatusMap = new HashMap<>();
    private final Map<String, String> cardEligibilityStatusMap = new HashMap<>();
    private final Map<String, String> provisioningCardSourceMap = new HashMap<>();

    private final Map<String, Map<String, String>> maps = new HashMap<>();

    private final String emptyCode = "";

    public static final String UNLINKED_ISSUER = "unlinkedIssuer";
    public static final String UNLINKED_WALLET = "unlinkedWallet";
    public static final String RESUMED_ISSUER = "resumedIssuer";
    public static final String RESUMED_WALLET = "resumedWallet";

    private SMPEventCode()
    {
        init();
    }

    public static SMPEventCode getInstance()
    {
        return INSTANCE;
    }

    private void init()/**/
    {
        addToMap(pnoMap, PNORepository.HELIUM_NAME, "201");
        addToMap(pnoMap, PNORepository.NEON_NAME, "202");
        addToMap(pnoMap, PNORepository.ARGON_NAME, "203");
        addToMap(pnoMap, PNORepository.KRYPTON_NAME, "204");
        addToMap(pnoMap, PNORepository.XENON_NAME, "205");
        addToMap(pnoMap, PNORepository.SODIUM_NAME, "206");
        //
        addToMap(colorMap, "green", "1");
        addToMap(colorMap, "yellow", "2");
        addToMap(colorMap, "red", "3");
        //
        addToMap(useCaseTypeMap, "Passbook", "1");
        //
        addToMap(fpanTypeMap, "Credit", "1");
        addToMap(fpanTypeMap, "Debit", "2");
        addToMap(fpanTypeMap, "PrePaid", "3");
        addToMap(fpanTypeMap, "PrivateLabel", "4");
        //
        addToMap(cardStatusMap, Card.CardStatus.UNKNOWN.toString(), "0");
        addToMap(cardStatusMap, Card.CardStatus.ACTIVE.toString(), "1");
        addToMap(cardStatusMap, Card.CardStatus.SUSPENDED.toString(), "2");
        addToMap(cardStatusMap, Card.CardStatus.UNLINKED.toString(), "3");
        addToMap(cardStatusMap, Card.CardStatus.SUSPENDED_OTP.toString(), "4");
        addToMap(cardStatusMap, Card.CardStatus.SUSPENDED_ISSUER.toString(), "5");
        addToMap(cardStatusMap, Card.CardStatus.SUSPENDED_WALLET.toString(), "6");
        //
        addToMap(cardEligibilityStatusMap, String.valueOf(CardEligibilityStatus.INELIGIBLE.getId()), "7");
        addToMap(cardEligibilityStatusMap, String.valueOf(CardEligibilityStatus.ELIGIBLE.getId()), "8");
        addToMap(cardEligibilityStatusMap, String.valueOf(CardEligibilityStatus.NETWORK_UNAVAILABLE.getId()), "9");
        addToMap(cardEligibilityStatusMap, String.valueOf(CardEligibilityStatus.PROVISIONED.getId()), "10");
        addToMap(cardEligibilityStatusMap, String.valueOf(CardEligibilityStatus.UPGRADE_REQUIRED.getId()), "11");
        //
        addToMap(cardStatusMap, UNLINKED_ISSUER, "12");
        addToMap(cardStatusMap, UNLINKED_WALLET, "13");
        addToMap(cardStatusMap, RESUMED_ISSUER, "14");
        addToMap(cardStatusMap, RESUMED_WALLET, "15");
        //
        addToMap(provisioningCardSourceMap, String.valueOf(ProvisioningCardSource.MANUAL.getId()), "1");
        addToMap(provisioningCardSourceMap, String.valueOf(ProvisioningCardSource.ON_FILE.getId()), "2");
        addToMap(provisioningCardSourceMap, String.valueOf(ProvisioningCardSource.BANKING_APP.getId()), "3");
        addToMap(provisioningCardSourceMap, String.valueOf(ProvisioningCardSource.PASS.getId()), "4");
        //
        maps.put(UNLINKED_ISSUER, cardStatusMap);
        maps.put(UNLINKED_WALLET, cardStatusMap);
        maps.put(RESUMED_ISSUER, cardStatusMap);
        maps.put(RESUMED_WALLET, cardStatusMap);
    }

    private void addToMap(Map<String, String> map, String key, String value)
    {
        map.put(key.toLowerCase(), value);
    }

    private boolean isValid(String code)
    {
        return (!code.equals(emptyCode));
    }

    private void writeCode(EventRecord record, EventAttribute attribute, Map<String, String> map, String key)
    {
        writeCode(record, attribute, getCode(map, key));
    }

    private void writeCode(EventRecord record, EventAttribute attribute, String code)
    {
        if (isValid(code))
        {
            record.setAttributeValue(attribute.key(), code);
        }
    }

    private String getCode(Map<String, String> map, String key)
    {
        if (key != null)
        {
            key = key.toLowerCase();
        }

        String value = (map != null) ? map.get(key) : null;

        return (value != null) ? value : emptyCode;
    }

    public void writePNOName(EventRecord record, EventAttribute attribute, String pnoName)
    {
        writeCode(record, attribute, pnoMap, pnoName);
    }

    public void writeProvisioningColor(EventRecord record, EventAttribute attribute, String color)
    {
        writeCode(record, attribute, colorMap, color);
    }

    public void writeUseCaseType(EventRecord record, EventAttribute attribute, String useCaseType)
    {
        writeCode(record, attribute, useCaseTypeMap, useCaseType);
    }

    public void writeFpanType(EventRecord record, EventAttribute attribute, String fpanType)
    {
        writeCode(record, attribute, fpanTypeMap, fpanType);
    }

    public void writeCardStatus(EventRecord record, EventAttribute attribute, Card.CardStatus cardStatus)
    {
        writeCode(record, attribute, cardStatusMap, (cardStatus != null) ? cardStatus.toString() : "");
    }

    public void writeCardEligibilityStatus(EventRecord record, EventAttribute attribute, CardEligibilityStatus cardEligibilityStatus)
    {
        writeCode(record, attribute, cardEligibilityStatusMap, (cardEligibilityStatus != null) ? String.valueOf(cardEligibilityStatus.getId()) : "");
    }

    public void writeProvisioningCardSource(EventRecord record, EventAttribute attribute, ProvisioningCardSource provisioningCardSource)
    {
        writeCode(record, attribute, provisioningCardSourceMap, (provisioningCardSource != null) ? String.valueOf(provisioningCardSource.getId()) : "");
    }

    public void writeValue(EventRecord record, EventAttribute attribute, String key)
    {
        Map<String, String> map = maps.get(key);

        if (map != null)
        {
            writeCode(record, attribute, map, key);
        }
    }

    public void writeResponseStatus(EventRecord record, EventAttribute attribute, String errorCode)
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
                        responseStatusCode = emptyCode;
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

        writeCode(record, attribute, (responseStatusCode != null) ? responseStatusCode : emptyCode);
    }
}