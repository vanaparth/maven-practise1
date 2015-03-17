package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.device.AbstractPass;
import com.apple.iossystems.smp.persistence.entity.PassPan;
import com.apple.iossystems.smp.persistence.entity.PassbookPass;
import com.apple.iossystems.smp.persistence.entity.SecureElement;

/**
 * @author Toch
 */
public class SMPEventDataServiceProxy
{
    private SMPEventDataServiceProxy()
    {
    }

    public static PassbookPass getPassbookPassByDpanId(String dpanId)
    {
        return (dpanId != null) ? SMPEventDataService.getPassByDpanId(dpanId) : null;
    }

    public static SecureElement getSecureElementByDpanId(String dpanId)
    {
        return (dpanId != null) ? SMPEventDataService.getSecureElementByDpanId(dpanId) : null;
    }

    public static String getDeviceType(SecureElement secureElement)
    {
        return (secureElement != null) ? SMPEventDataService.getDeviceType(secureElement) : null;
    }

    public static String getCardDisplayNumberFromPassbookPass(PassbookPass passbookPass)
    {
        return (passbookPass != null) ? passbookPass.getFpanSuffix() : null;
    }

    public static String getCardDescriptionFromPassbookPass(PassbookPass passbookPass)
    {
        String value = null;

        if (passbookPass != null)
        {
            value = SMPEventDataService.getValueFromPassbookPass(passbookPass, AbstractPass.PAYMENT_PASS_LONG_DESC_KEY);

            if (value == null)
            {
                value = SMPEventDataService.getValueFromPassbookPass(passbookPass, AbstractPass.PAYMENT_PASS_SHORT_DESC_KEY);
            }
        }

        return value;
    }

    public static PassPan getPassPanByPassSerialAndSeid(String passSerial, String seid)
    {
        return SMPEventDataService.getPassPanByPassSerialAndSeid(passSerial, seid);
    }
}