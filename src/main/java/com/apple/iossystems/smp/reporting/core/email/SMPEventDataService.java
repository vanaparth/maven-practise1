package com.apple.iossystems.smp.reporting.core.email;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.smp.persistence.entity.*;
import com.apple.iossystems.smp.service.PassManagementService;
import com.apple.iossystems.smp.service.SecureElementService;

import java.util.Collection;

/**
 * @author Toch
 */
class SMPEventDataService
{
    private static final PassManagementService PASS_MANAGEMENT_SERVICE = AppContext.getApplicationContext().getBean(PassManagementService.class);
    private static final SecureElementService SECURE_ELEMENT_SERVICE = AppContext.getApplicationContext().getBean(SecureElementService.class);

    private SMPEventDataService()
    {
    }

    public static String getDeviceType(SecureElement secureElement)
    {
        DeviceType deviceType = secureElement.getDeviceType();

        return (deviceType != null) ? deviceType.getDeviceTypeName() : null;
    }

    public static PassbookPass getPassByDpanId(String dpanId)
    {
        return PASS_MANAGEMENT_SERVICE.getPassByDpanId(dpanId);
    }

    public static SecureElement getSecureElementByDpanId(String dpanId)
    {
        PassPan passPan = PASS_MANAGEMENT_SERVICE.getPassPanByDpanId(dpanId);

        return (passPan != null) ? passPan.getSecureElementId() : null;
    }

    public static String getValueFromPassbookPass(PassbookPass passbookPass, String key)
    {
        String value = null;

        Collection<PanMetadata> panMetadataCollection = passbookPass.getPanMetadataCollection();

        if (panMetadataCollection != null)
        {
            for (PanMetadata panMetadata : panMetadataCollection)
            {
                if ((panMetadata != null) && (key.equals(panMetadata.getKey())))
                {
                    value = panMetadata.getValue();

                    break;
                }
            }
        }

        return value;
    }

    public static PassPan getPassPanByPassSerialAndSeid(String passSerial, String seid)
    {
        return PASS_MANAGEMENT_SERVICE.getPrimaryPassPanByPassSerialAndSeid(seid, passSerial);
    }

    public static String getCompanionDeviceSerialNumber(String serialNumber)
    {
        SecureElement secureElement = SECURE_ELEMENT_SERVICE.findSecureElementBySerialNumber(serialNumber);

        return (secureElement != null) ? secureElement.getCompanionDeviceSerialNumber() : null;
    }
}