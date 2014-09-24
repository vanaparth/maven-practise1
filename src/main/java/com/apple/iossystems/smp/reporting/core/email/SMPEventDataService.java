package com.apple.iossystems.smp.reporting.core.email;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.smp.domain.icloud.FetchDeviceResponse;
import com.apple.iossystems.smp.icloud.util.iCloudService;
import com.apple.iossystems.smp.persistence.entity.*;
import com.apple.iossystems.smp.service.PassManagementService;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class SMPEventDataService
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventDataService.class);

    private static final iCloudService ICLOUD_SERVICE = (iCloudService) AppContext.getApplicationContext().getBean("iCloudServiceImpl");
    private static final PassManagementService PASS_MANAGEMENT_SERVICE = AppContext.getApplicationContext().getBean(PassManagementService.class);

    private SMPEventDataService()
    {
    }

    static String getDeviceName(PassbookPass passbookPass, SecureElement secureElement)
    {
        String deviceName = null;

        try
        {
            FetchDeviceResponse fetchDeviceResponse = ICLOUD_SERVICE.fetchDeviceResponse(passbookPass.getUserPrincipal(), secureElement.getDeviceSerialNumber());

            if (fetchDeviceResponse != null)
            {
                deviceName = fetchDeviceResponse.getDeviceName();
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return deviceName;
    }

    static String getDeviceType(SecureElement secureElement)
    {
        String deviceTypeName = null;

        DeviceType deviceType = secureElement.getDeviceType();

        if (deviceType != null)
        {
            deviceTypeName = deviceType.getDeviceTypeName();
        }

        return deviceTypeName;
    }

    static PassbookPass getPassByDpanId(String dpanId)
    {
        return PASS_MANAGEMENT_SERVICE.getPassByDpanId(dpanId);
    }

    static SecureElement getSecureElement(String dpanId)
    {
        SecureElement secureElement = null;

        PassPan passPan = PASS_MANAGEMENT_SERVICE.getPassPanByDpanId(dpanId);

        if (passPan != null)
        {
            secureElement = passPan.getSecureElementId();
        }

        return secureElement;
    }

    static String getValueFromPassbookPass(PassbookPass passbookPass, String key)
    {
        String value = null;

        for (PanMetadata panMetadata : passbookPass.getPanMetadataCollection())
        {
            if (panMetadata.getKey().equals(key))
            {
                value = panMetadata.getValue();

                break;
            }
        }

        return value;
    }
}