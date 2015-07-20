package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.domain.device.AbstractPass;
import com.apple.iossystems.smp.domain.product.ProductId;
import com.apple.iossystems.smp.persistence.entity.PassPan;
import com.apple.iossystems.smp.persistence.entity.PassPaymentType;
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
        return ((passSerial != null) && (seid != null)) ? smpEventDataService.getPassPanByPassSerialAndSeid(passSerial, seid) : null;
    }

    public String getDpanIdByPassSerialAndSeid(String passSerial, String seid)
    {
        PassPan passPan = ((passSerial != null) && (seid != null)) ? getPassPanByPassSerialAndSeid(passSerial, seid) : null;

        return (passPan != null) ? passPan.getDpanId() : null;
    }

    public String getCompanionDeviceSerialNumber(String serialNumber)
    {
        return (serialNumber != null) ? smpEventDataService.getCompanionDeviceSerialNumber(serialNumber) : null;
    }

    public PassPaymentType getPassPaymentType(ProductId productId)
    {
        return (productId != null) ? smpEventDataService.getPassPaymentType(productId) : null;
    }
}