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

    private static final SMPEventCache SMP_EVENT_CACHE = SMPEventCache.getInstance();

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
        // Prevent any side effects
        try
        {
            doProcessProvisionEvent(dpanId, dsid);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void doProcessProvisionEvent(String dpanId, String dsid)
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

        publishEvent(dpanId, ((dsidInfo != null) && (dsidInfo.getProvisionCount() == ProvisionCount.ZERO)));

        storeManagementService.updateProvisionCount(dsid);
    }

    private void publishEvent(String dpanId, boolean firstProvision)
    {
        String json = SMP_EVENT_CACHE.remove(SMPEventCache.Attribute.PROVISION_EVENT, dpanId);

        if (firstProvision && (json != null))
        {
            emailService.publishProvisionEvent(GsonBuilderFactory.getInstance().fromJson(json, ProvisionCardEvent.class));
        }
    }
}