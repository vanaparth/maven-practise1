package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.smp.domain.DSIDInfo;
import com.apple.iossystems.smp.domain.ProvisionCount;
import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.reporting.core.concurrent.ThreadPoolExecutorService;
import com.apple.iossystems.smp.reporting.core.email.EmailService;
import com.apple.iossystems.smp.reporting.core.email.ProvisionCardEvent;
import com.apple.iossystems.smp.reporting.core.persistence.SMPEventCache;
import com.apple.iossystems.smp.service.StoreManagementService;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

/**
 * @author Toch
 */
public class ProvisionEventNotificationService
{
    private static final Logger LOGGER = Logger.getLogger(ProvisionEventNotificationService.class);

    private static final ProvisionEventNotificationService INSTANCE = new ProvisionEventNotificationService();

    private ThreadPoolExecutorService threadPoolExecutorService = ThreadPoolExecutorService.getInstance();

    private EmailService emailService = EmailService.getInstance();

    private static final SMPEventCache SMP_EVENT_CACHE = SMPEventCache.getInstance();

    private ProvisionEventNotificationService()
    {
    }

    public static ProvisionEventNotificationService getInstance()
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
            dsidInfo = storeManagementService.findDSIDInfo(dsid);
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
            processEmailEvent(GsonBuilderFactory.getInstance().fromJson(json, ProvisionCardEvent.class));
        }
    }

    private void processEmailEvent(ProvisionCardEvent provisionCardEvent)
    {
        try
        {
            threadPoolExecutorService.submit(new Task(provisionCardEvent));
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private class Task implements Callable
    {
        private final ProvisionCardEvent provisionCardEvent;

        private Task(ProvisionCardEvent provisionCardEvent)
        {
            this.provisionCardEvent = provisionCardEvent;
        }

        @Override
        public Object call()
        {
            emailService.publishProvisionEvent(provisionCardEvent);

            return null;
        }
    }
}