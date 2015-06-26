package com.apple.iossystems.smp.reporting.core.email;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.smp.domain.DSIDInfo;
import com.apple.iossystems.smp.domain.ProvisionCount;
import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.reporting.core.persistence.SMPEventCache;
import com.apple.iossystems.smp.service.StoreManagementService;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class ProvisionEmailPublishService
{
    private static final Logger LOGGER = Logger.getLogger(ProvisionEmailPublishService.class);

    private EmailService emailService = EmailService.getInstance();

    private static final ProvisionEmailPublishService INSTANCE = new ProvisionEmailPublishService();

    private ProvisionEmailPublishService()
    {
    }

    public static ProvisionEmailPublishService getInstance()
    {
        return INSTANCE;
    }

    public void processProvisionEvent(String dpanId, String dsid)
    {
        StoreManagementService storeManagementService = AppContext.getApplicationContext().getBean(StoreManagementService.class);

        DSIDInfo dsidInfo = null;

        try
        {
            dsidInfo = storeManagementService.getDSIDInfo(dsid);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        publishEvent(dpanId, ((dsidInfo != null) && (ProvisionCount.ZERO.equals(dsidInfo.getProvisionCount()))));

        storeManagementService.updateProvisionCount(dsid);
    }

    private void publishEvent(String dpanId, boolean firstProvision)
    {
        String json = SMPEventCache.remove(SMPEventCache.Attribute.PROVISION_EVENT, dpanId);

        LOGGER.info("Email Test - Searching L&P with key: " + dpanId + " " + firstProvision);

        if (firstProvision && (json != null))
        {
            LOGGER.info("Email Test - Retrieving L&P with key: " + dpanId);

            emailService.publishProvisionEvent(GsonBuilderFactory.getInstance().fromJson(json, ProvisionCardEvent.class));
        }
    }
}