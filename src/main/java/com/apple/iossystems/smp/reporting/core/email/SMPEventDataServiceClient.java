package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.device.AbstractPass;
import com.apple.iossystems.smp.persistence.entity.PassPan;
import com.apple.iossystems.smp.persistence.entity.PassbookPass;
import com.apple.iossystems.smp.persistence.entity.SecureElement;

/**
 * @author Toch
 */
public class SMPEventDataServiceClient
{
    private SMPEventDataService smpEventDataService = SMPEventDataService.getInstance();

    private SMPEventDataServiceClient()
    {
    }

    public static SMPEventDataServiceClient getInstance()
    {
        return new SMPEventDataServiceClient();
    }

    public PassbookPass getPassbookPassByDpanId(String dpanId)
    {
        return (dpanId != null) ? smpEventDataService.getPassByDpanId(dpanId) : null;
    }

    public SecureElement getSecureElementByDpanId(String dpanId)
    {
        return (dpanId != null) ? smpEventDataService.getSecureElementByDpanId(dpanId) : null;
    }

    public String getDeviceType(SecureElement secureElement)
    {
        return (secureElement != null) ? smpEventDataService.getDeviceType(secureElement) : null;
    }

    public String getCardDisplayNumberFromPassbookPass(PassbookPass passbookPass)
    {
        return (passbookPass != null) ? passbookPass.getFpanSuffix() : null;
    }

    public String getCardDescriptionFromPassbookPass(PassbookPass passbookPass)
    {
        String value = null;

        if (passbookPass != null)
        {
            value = smpEventDataService.getValueFromPassbookPass(passbookPass, AbstractPass.PAYMENT_PASS_LONG_DESC_KEY);

            if (value == null)
            {
                value = smpEventDataService.getValueFromPassbookPass(passbookPass, AbstractPass.PAYMENT_PASS_SHORT_DESC_KEY);
            }
        }

        return value;
    }

    public PassPan getPassPanByPassSerialAndSeid(String passSerial, String seid)
    {
        return smpEventDataService.getPassPanByPassSerialAndSeid(passSerial, seid);
    }

    public String getDpanIdByPassSerialAndSeid(String passSerial, String seid)
    {
        PassPan passPan = getPassPanByPassSerialAndSeid(passSerial, seid);

        return (passPan != null) ? passPan.getDpanId() : null;
    }

    public String getCompanionDeviceSerialNumber(String serialNumber)
    {
        return (serialNumber != null) ? smpEventDataService.getCompanionDeviceSerialNumber(serialNumber) : null;
    }
}