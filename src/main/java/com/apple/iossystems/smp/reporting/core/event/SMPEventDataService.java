package com.apple.iossystems.smp.reporting.core.event;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.persistence.entity.dao.PaymentProductRepository;
import com.apple.iossystems.smp.domain.product.ProductId;
import com.apple.iossystems.smp.persistence.entity.*;
import com.apple.iossystems.smp.service.PassManagementService;
import com.apple.iossystems.smp.service.SecureElementService;

import java.util.Collection;

/**
 * @author Toch
 */
class SMPEventDataService
{
    private final PassManagementService passManagementService = AppContext.getApplicationContext().getBean(PassManagementService.class);
    private final SecureElementService secureElementService = AppContext.getApplicationContext().getBean(SecureElementService.class);
    private final PaymentProductRepository paymentProductRepository = AppContext.getApplicationContext().getBean(PaymentProductRepository.class);

    private SMPEventDataService()
    {
    }

    static SMPEventDataService getInstance()
    {
        return new SMPEventDataService();
    }

    String getDeviceType(SecureElement secureElement)
    {
        DeviceType deviceType = (secureElement != null) ? secureElement.getDeviceType() : null;

        return (deviceType != null) ? deviceType.getDeviceTypeName() : null;
    }

    PassbookPass getPassByDpanId(String dpanId)
    {
        return passManagementService.getPassByDpanId(dpanId);
    }

    SecureElement getSecureElementByDpanId(String dpanId)
    {
        PassPan passPan = passManagementService.getPassPanByDpanId(dpanId);

        return (passPan != null) ? passPan.getSecureElementId() : null;
    }

    String getValueFromPassbookPass(PassbookPass passbookPass, String key)
    {
        String value = null;

        Collection<PanMetadata> panMetadataCollection = (passbookPass != null) ? passbookPass.getPanMetadataCollection() : null;

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

    PassPan getPassPanByPassSerialAndSeid(String passSerial, String seid)
    {
        return passManagementService.getPrimaryPassPanByPassSerialAndSeid(seid, passSerial);
    }

    String getCompanionDeviceSerialNumber(String serialNumber)
    {
        SecureElement secureElement = secureElementService.findSecureElementBySerialNumber(serialNumber);

        return (secureElement != null) ? secureElement.getCompanionDeviceSerialNumber() : null;
    }

    PassPaymentType getPassPaymentType(ProductId productId)
    {
        PaymentProduct paymentProduct = (productId != null) ? paymentProductRepository.findByPnoAndName(productId.getPnoName(), productId.getName()) : null;

        PaymentOption paymentOption = (paymentProduct != null) ? paymentProduct.getPrimaryPaymentOption() : null;

        return (paymentOption != null) ? paymentOption.getPaymentType() : null;
    }
}