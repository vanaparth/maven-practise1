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

    private ProvisionEmailPublishService()
    {
    }

    public static void processProvisionEvent(String conversationId, String dsid)
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

        publishEvent(conversationId, ((dsidInfo != null) && (ProvisionCount.ZERO.equals(dsidInfo.getProvisionCount()))));

        storeManagementService.updateProvisionCount(dsid);
    }

    private static void publishEvent(String conversationId, boolean firstProvision)
    {
        String json = SMPEventCache.remove(SMPEventCache.Attribute.PROVISION_EVENT, conversationId);

        if (firstProvision && (json != null))
        {
            EmailPublishService.publishProvisionEvent(GsonBuilderFactory.getInstance().fromJson(json, ProvisionCardEvent.class));
        }
    }
}