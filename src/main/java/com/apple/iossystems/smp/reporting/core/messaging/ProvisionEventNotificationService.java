package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.smp.domain.DSIDInfo;
import com.apple.iossystems.smp.domain.ProvisionCount;
import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.reporting.core.concurrent.TaskExecutorService;
import com.apple.iossystems.smp.reporting.core.email.EmailEventService;
import com.apple.iossystems.smp.reporting.core.email.EmailServiceFactory;
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

    private static final SMPEventCache SMP_EVENT_CACHE = SMPEventCache.getInstance();

    private final EmailEventService emailService = EmailServiceFactory.getInstance().getEmailService();
    private final StoreManagementService storeManagementService = AppContext.getApplicationContext().getBean(StoreManagementService.class);
    private final TaskExecutorService taskExecutorService = AppContext.getApplicationContext().getBean(TaskExecutorService.class);

    private ProvisionEventNotificationService()
    {
    }

    public static ProvisionEventNotificationService getInstance()
    {
        return INSTANCE;
    }

    public void processProvisionEvent(String dpanId, String dsid, String seid)
    {
        // Prevent any side effects
        try
        {
            doProcessProvisionEvent(dpanId, dsid, seid);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void doProcessProvisionEvent(String dpanId, String dsid, String seid)
    {
        DSIDInfo dsidInfo = null;

        try
        {
            dsidInfo = storeManagementService.findDSIDInfo(dsid);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        publishEvent(dpanId, ((dsidInfo != null) && (dsidInfo.getProvisionCount() == ProvisionCount.ZERO)));

        storeManagementService.updateProvisionCount(dsid, seid);
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
            taskExecutorService.submit(new Task(provisionCardEvent));
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private class Task implements Callable<Boolean>
    {
        private final ProvisionCardEvent provisionCardEvent;

        private Task(ProvisionCardEvent provisionCardEvent)
        {
            this.provisionCardEvent = provisionCardEvent;
        }

        @Override
        public Boolean call() throws Exception
        {
            emailService.processProvisionEvent(provisionCardEvent);

            return true;
        }
    }
}