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
    private PassManagementService passManagementService = AppContext.getApplicationContext().getBean(PassManagementService.class);
    private SecureElementService secureElementService = AppContext.getApplicationContext().getBean(SecureElementService.class);

    private SMPEventDataService()
    {
    }

    public static SMPEventDataService getInstance()
    {
        return new SMPEventDataService();
    }

    public String getDeviceType(SecureElement secureElement)
    {
        DeviceType deviceType = secureElement.getDeviceType();

        return (deviceType != null) ? deviceType.getDeviceTypeName() : null;
    }

    public PassbookPass getPassByDpanId(String dpanId)
    {
        return passManagementService.getPassByDpanId(dpanId);
    }

    public SecureElement getSecureElementByDpanId(String dpanId)
    {
        PassPan passPan = passManagementService.getPassPanByDpanId(dpanId);

        return (passPan != null) ? passPan.getSecureElementId() : null;
    }

    public String getValueFromPassbookPass(PassbookPass passbookPass, String key)
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

    public PassPan getPassPanByPassSerialAndSeid(String passSerial, String seid)
    {
        return passManagementService.getPrimaryPassPanByPassSerialAndSeid(seid, passSerial);
    }

    public String getCompanionDeviceSerialNumber(String serialNumber)
    {
        SecureElement secureElement = secureElementService.findSecureElementBySerialNumber(serialNumber);

        return (secureElement != null) ? secureElement.getCompanionDeviceSerialNumber() : null;
    }
}