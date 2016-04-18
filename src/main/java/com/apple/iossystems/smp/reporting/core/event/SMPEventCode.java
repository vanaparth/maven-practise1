package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.domain.Actor;
import com.apple.iossystems.smp.domain.CardProvisioningSource;
import com.apple.iossystems.smp.domain.SMPCardProvisioningSource;
import com.apple.iossystems.smp.domain.WalletCardProvisioningSource;
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
    private final Map<String, String> cardStatusUpdateSourceMap = new HashMap<>();
    private final Map<String, String> cardProvisioningSourceMap = new HashMap<>();

    private final String emptyCode = "";

    private SMPEventCode()
    {
        init();
    }

    public static SMPEventCode getInstance()
    {
        return INSTANCE;
    }

    private void init()
    {
        addToMap(pnoMap, PNORepository.HELIUM_NAME, "201");
        addToMap(pnoMap, PNORepository.NEON_NAME, "202");
        addToMap(pnoMap, PNORepository.ARGON_NAME, "203");
        addToMap(pnoMap, PNORepository.KRYPTON_NAME, "204");
        addToMap(pnoMap, PNORepository.XENON_NAME, "205");
        addToMap(pnoMap, PNORepository.SODIUM_NAME, "206");
        addToMap(pnoMap, PNORepository.SLALOM_NAME, "207");
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
        addToMap(fpanTypeMap, "Transit", "5");
        //
        addToMap(cardStatusMap, Card.CardStatus.UNKNOWN.toString(), "0");
        addToMap(cardStatusMap, Card.CardStatus.ACTIVE.toString(), "1");
        addToMap(cardStatusMap, Card.CardStatus.SUSPENDED.toString(), "2");
        addToMap(cardStatusMap, Card.CardStatus.UNLINKED.toString(), "3");
        addToMap(cardStatusMap, Card.CardStatus.SUSPENDED_OTP.toString(), "4");
        addToMap(cardStatusMap, Card.CardStatus.SUSPENDED_ISSUER.toString(), "5");
        addToMap(cardStatusMap, Card.CardStatus.SUSPENDED_WALLET.toString(), "6");
        //
        addToMap(cardStatusMap, String.valueOf(CardEligibilityStatus.INELIGIBLE.getId()), "7");
        addToMap(cardStatusMap, String.valueOf(CardEligibilityStatus.ELIGIBLE.getId()), "8");
        addToMap(cardStatusMap, String.valueOf(CardEligibilityStatus.NETWORK_UNAVAILABLE.getId()), "9");
        addToMap(cardStatusMap, String.valueOf(CardEligibilityStatus.PROVISIONED.getId()), "10");
        addToMap(cardStatusMap, String.valueOf(CardEligibilityStatus.UPGRADE_REQUIRED.getId()), "11");
        //
        addToMap(cardStatusMap, String.valueOf(Card.CardStatus.IN_PROCESS.toString()), "12");
        //
        addToMap(cardStatusUpdateSourceMap, Actor.DEVICE.toString(), "1");
        addToMap(cardStatusUpdateSourceMap, Actor.INTERNAL.toString(), "1");
        addToMap(cardStatusUpdateSourceMap, Actor.PNO.toString(), "2");
        addToMap(cardStatusUpdateSourceMap, Actor.FMIP.toString(), "3");
        //
        addToMap(cardProvisioningSourceMap, WalletCardProvisioningSource.MANUAL.getSource(), "1");
        addToMap(cardProvisioningSourceMap, SMPCardProvisioningSource.ON_FILE.getSource(), "2");
        addToMap(cardProvisioningSourceMap, WalletCardProvisioningSource.BANKING_APP.getSource(), "3");
        addToMap(cardProvisioningSourceMap, SMPCardProvisioningSource.PASS.getSource(), "4");
        addToMap(cardProvisioningSourceMap, WalletCardProvisioningSource.READER.getSource(), "5");
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

    public void writeCardStatusUpdateSource(EventRecord record, EventAttribute attribute, Actor actor)
    {
        writeCode(record, attribute, cardStatusUpdateSourceMap, (actor != null) ? actor.toString() : "");
    }

    public void writeCardEligibilityStatus(EventRecord record, EventAttribute attribute, CardEligibilityStatus cardEligibilityStatus)
    {
        writeCode(record, attribute, cardStatusMap, (cardEligibilityStatus != null) ? String.valueOf(cardEligibilityStatus.getId()) : "");
    }

    public void writeCardProvisioningSource(EventRecord record, EventAttribute attribute, CardProvisioningSource cardProvisioningSource)
    {
        writeCode(record, attribute, cardProvisioningSourceMap, (cardProvisioningSource != null) ? cardProvisioningSource.getSource() : "");
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