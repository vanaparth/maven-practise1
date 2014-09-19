package com.apple.iossystems.smp.reporting.core.email;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.smp.broker.domain.registry.AccountDataValue;
import com.apple.iossystems.smp.domain.AccountDataDescriptor;
import com.apple.iossystems.smp.domain.device.AbstractPass;
import com.apple.iossystems.smp.domain.icloud.FetchDeviceResponse;
import com.apple.iossystems.smp.icloud.util.iCloudService;
import com.apple.iossystems.smp.persistence.entity.DeviceType;
import com.apple.iossystems.smp.persistence.entity.PanMetadata;
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
class EmailContentService
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
                String value = accountDataDescriptor.getEmailAddress();

                if (value != null)
                {
                    cardHolderEmail = value;
                }
            }
        }

        return cardHolderEmail;
    }

    static String getDeviceName(PassbookPass passbookPass, SecureElement secureElement)
    {
        String deviceName = "";

        try
        {
            FetchDeviceResponse fetchDeviceResponse = ICLOUD_SERVICE.fetchDeviceResponse(passbookPass.getUserPrincipal(), secureElement.getDeviceSerialNumber());

            if (fetchDeviceResponse != null)
            {
                String value = fetchDeviceResponse.getDeviceName();

                if (value != null)
                {
                    deviceName = value;
                }
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
        String deviceTypeName = "";

        DeviceType deviceType = secureElement.getDeviceType();

        if (deviceType != null)
        {
            String value = deviceType.getDeviceTypeName();

            if (value != null)
            {
                deviceTypeName = value;
            }
        }

        return deviceTypeName;
    }

    static String getLocale()
    {
        return "";
    }

    static PassbookPass getPassByDpanId(String dpanId)
    {
        return PASS_MANAGEMENT_SERVICE.getPassByDpanId(dpanId);
    }

    static SecureElement getSecureElement(String dpanId)
    {
        return PASS_MANAGEMENT_SERVICE.getPassPanByDpanId(dpanId).getSecureElementId();
    }

    static boolean isFirstProvision(PassbookPass passbookPass, SecureElement secureElement)
    {
        return (SECURE_ELEMENT_SERVICE.findBySeIdAndUserPrincipal(secureElement.getSeid(), passbookPass.getUserPrincipal()).getProvisioningCount() == 1);
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
        String panMetaDataKey = panMetaData.getKey();

        for (EmailAttribute emailAttribute : emailAttributes)
        {
            if (panMetaDataKey.equals(emailAttribute.panMetaDataKey))
            {
                String recordKey = emailAttribute.eventAttribute.key();
                String recordValue = eventRecord.getAttributeValue(recordKey);

                if (recordValue == null)
                {
                    eventRecord.setAttributeValue(recordKey, panMetaData.getValue());
                }

                break;
            }
        }
    }

    private static class EmailAttribute
    {
        private static final EmailAttribute[] EMAIL_ATTRIBUTES =
                {
                        new EmailAttribute(EventAttribute.CARD_DISPLAY_NUMBER, AbstractPass.PAYMENT_PASS_FPAN_SUFFIX_KEY),
                        new EmailAttribute(EventAttribute.CARD_DESCRIPTION, AbstractPass.PAYMENT_PASS_LONG_DESC_KEY),
                        new EmailAttribute(EventAttribute.CARD_DESCRIPTION, AbstractPass.PAYMENT_PASS_SHORT_DESC_KEY)
                };

        private final EventAttribute eventAttribute;
        private final String panMetaDataKey;

        private EmailAttribute(EventAttribute eventAttribute, String panMetaDataKey)
        {
            this.eventAttribute = eventAttribute;
            this.panMetaDataKey = panMetaDataKey;
        }
    }
}