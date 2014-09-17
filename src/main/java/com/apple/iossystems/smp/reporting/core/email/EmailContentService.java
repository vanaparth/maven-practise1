package com.apple.iossystems.smp.reporting.core.email;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.smp.broker.domain.registry.AccountDataValue;
import com.apple.iossystems.smp.domain.AccountDataDescriptor;
import com.apple.iossystems.smp.domain.device.AbstractPass;
import com.apple.iossystems.smp.domain.icloud.FetchDeviceResponse;
import com.apple.iossystems.smp.icloud.util.iCloudService;
import com.apple.iossystems.smp.persistence.entity.PanMetadata;
import com.apple.iossystems.smp.persistence.entity.PassPan;
import com.apple.iossystems.smp.persistence.entity.PassbookPass;
import com.apple.iossystems.smp.persistence.entity.SecureElement;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.service.PassManagementService;
import com.apple.iossystems.smp.service.SecureElementService;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class EmailContentService
{
    private static final Logger LOGGER = Logger.getLogger(EmailContentService.class);

    private static final iCloudService ICLOUD_SERVICE = (iCloudService) AppContext.getApplicationContext().getBean("iCloudServiceImpl");
    private static final PassManagementService PASS_MANAGEMENT_SERVICE = AppContext.getApplicationContext().getBean(PassManagementService.class);
    private static final SecureElementService SECURE_ELEMENT_SERVICE = AppContext.getApplicationContext().getBean(SecureElementService.class);

    private EmailContentService()
    {
    }

    private static AccountDataValue getAccountDataValue()
    {
        return null;
    }

    static String getCardHolderEmail()
    {
        String cardHolderEmail = "";

        AccountDataValue accountDataValue = getAccountDataValue();

        if (accountDataValue != null)
        {
            AccountDataDescriptor accountDataDescriptor = accountDataValue.getAccountDataDescriptor();

            if (accountDataDescriptor != null)
            {
                cardHolderEmail = accountDataDescriptor.getEmailAddress();
            }
        }

        return cardHolderEmail;
    }

    static String getDeviceName(String dsid, String serialNumber)
    {
        String deviceName = "";

        try
        {
            FetchDeviceResponse fetchDeviceResponse = ICLOUD_SERVICE.fetchDeviceResponse(dsid, serialNumber);
            deviceName = fetchDeviceResponse.getDeviceName();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return deviceName;
    }

    static String getDeviceSerialNumber(String dpanId)
    {
        String deviceSerialNumber = "";

        PassPan passPan = PASS_MANAGEMENT_SERVICE.getPassPanByDpanId(dpanId);

        if (passPan != null)
        {
            SecureElement secureElement = PASS_MANAGEMENT_SERVICE.getPassPanByDpanId(dpanId).getSecureElementId();

            if (secureElement != null)
            {
                deviceSerialNumber = secureElement.getDeviceSerialNumber();
            }
        }

        return deviceSerialNumber;
    }

    static PassbookPass getPassByDpanId(String dpanId)
    {
        return PASS_MANAGEMENT_SERVICE.getPassByDpanId(dpanId);
    }

    static String getSeId(SecureElement secureElement)
    {
        return secureElement.getSeid();
    }

    static boolean isFirstProvision(String seId, String userPrincipal)
    {
        return (SECURE_ELEMENT_SERVICE.findBySeIdAndUserPrincipal(seId, userPrincipal).getProvisioningCount() == 1);
    }

    static void setValuesFromPassbookPass(EventRecord eventRecord, PassbookPass passbookPass)
    {
        for (PanMetadata panMetadata : passbookPass.getPanMetadataCollection())
        {
            setValueFromPanMetaData(eventRecord, panMetadata, EmailAttribute.EMAIL_ATTRIBUTES);
        }
    }

    static void setValueFromPanMetaData(EventRecord eventRecord, PanMetadata panMetaData, EmailAttribute[] emailAttributes)
    {
        String metaDataKey = panMetaData.getKey();

        for (EmailAttribute emailAttribute : emailAttributes)
        {
            if (metaDataKey.equals(emailAttribute.passKey))
            {
                eventRecord.setAttributeValue(emailAttribute.eventAttribute.key(), panMetaData.getValue());

                break;
            }
        }
    }

    private static class EmailAttribute
    {
        private static final EmailAttribute[] EMAIL_ATTRIBUTES =
                {
                        new EmailAttribute(EventAttribute.CARD_DISPLAY_NUMBER, AbstractPass.PAYMENT_PASS_FPAN_SUFFIX_KEY),
                        new EmailAttribute(EventAttribute.CARD_DESCRIPTION, AbstractPass.PAYMENT_PASS_SHORT_DESC_KEY)
                };

        private final EventAttribute eventAttribute;
        private final String passKey;

        private EmailAttribute(EventAttribute eventAttribute, String passKey)
        {
            this.eventAttribute = eventAttribute;
            this.passKey = passKey;
        }
    }
}