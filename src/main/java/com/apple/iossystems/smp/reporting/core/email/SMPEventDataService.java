package com.apple.iossystems.smp.reporting.core.email;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.smp.domain.DSIDInfo;
import com.apple.iossystems.smp.domain.ProvisionCount;
import com.apple.iossystems.smp.persistence.entity.*;
import com.apple.iossystems.smp.service.PassManagementService;
import com.apple.iossystems.smp.service.StoreManagementService;
import org.apache.log4j.Logger;

import java.util.Collection;

/**
 * @author Toch
 */
class SMPEventDataService
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventDataService.class);
    private static final PassManagementService PASS_MANAGEMENT_SERVICE = AppContext.getApplicationContext().getBean(PassManagementService.class);
    private static final StoreManagementService STORE_MANAGEMENT_SERVICE = AppContext.getApplicationContext().getBean(StoreManagementService.class);

    private SMPEventDataService()
    {
    }

    public static String getDeviceType(SecureElement secureElement)
    {
        String deviceTypeName = null;

        DeviceType deviceType = secureElement.getDeviceType();

        if (deviceType != null)
        {
            deviceTypeName = deviceType.getDeviceTypeName();
        }

        return deviceTypeName;
    }

    public static PassbookPass getPassByDpanId(String dpanId)
    {
        return PASS_MANAGEMENT_SERVICE.getPassByDpanId(dpanId);
    }

    public static SecureElement getSecureElementByDpanId(String dpanId)
    {
        SecureElement secureElement = null;

        PassPan passPan = PASS_MANAGEMENT_SERVICE.getPassPanByDpanId(dpanId);

        if (passPan != null)
        {
            secureElement = passPan.getSecureElementId();
        }

        return secureElement;
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

    public static ProvisionCount getProvisionCount(String dsid)
    {
        DSIDInfo dsidInfo = null;

        try
        {
            dsidInfo = STORE_MANAGEMENT_SERVICE.getDSIDInfo(dsid);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return ((dsidInfo != null) ? dsidInfo.getProvisionCount() : null);
    }
}