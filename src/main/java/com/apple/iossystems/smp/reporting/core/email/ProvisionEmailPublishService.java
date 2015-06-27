package com.apple.iossystems.smp.reporting.core.email;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.smp.LogKeyVal;
import com.apple.iossystems.smp.domain.DSIDInfo;
import com.apple.iossystems.smp.domain.ProvisionCount;
import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.exception.SMPBusinessException;
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

        try {
            DSIDInfo dsidInfo = storeManagementService.getDSIDInfo(dsid);
            // Checking for a provisioning count of ONE here since we are called AFTER updating the provisioning count from 0 => 1.
            // This will be true if and only if this is the user's first successful provisioning.
            boolean isFirstProvision = ProvisionCount.ONE.equals(dsidInfo.getProvisionCount());

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("Processing provision event. " + LogKeyVal.dsId(dsid) + " " + LogKeyVal.dpanId(dpanId) + " isFirstProvision=" + isFirstProvision + " provisionCount=" + dsidInfo.getProvisionCount() );
            }

            publishEvent(dpanId, isFirstProvision);
        }
        catch (SMPBusinessException smpbex)
        {
            LOGGER.error("Failed to procure DSID Info for provision event! " + LogKeyVal.dsId(dsid) + " " + LogKeyVal.dpanId(dpanId) + " " + smpbex.getMessage(), smpbex);
        }
    }

    private void publishEvent(String dpanId, boolean firstProvision)
    {
        String json = SMPEventCache.remove(SMPEventCache.Attribute.PROVISION_EVENT, dpanId);

        if (firstProvision)
        {
            if (null != json)
            {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Sending email for first time provisioning for " + LogKeyVal.dpanId(dpanId));
                }
                emailService.publishProvisionEvent(GsonBuilderFactory.getInstance().fromJson(json, ProvisionCardEvent.class));
            }
            else
            {
                LOGGER.error("Could not locate cached provisioning details. " + LogKeyVal.dpanId(dpanId));
            }
        }
    }
}