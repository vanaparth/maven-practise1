package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.logging.local.BDBStorage;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.hubble.HubblePublisher;
import com.apple.iossystems.smp.reporting.core.timer.Timer;
import com.apple.iossystems.smp.service.StoreManagementService;
import org.apache.log4j.Logger;

import java.util.Collections;
import java.util.Map;

/**
 * @author Toch
 */
class BacklogBdbConsumer extends BdbConsumer
{
    private static final Logger LOGGER = Logger.getLogger(BdbConsumer.class);

    private final HubblePublisher hubblePublisher = HubblePublisher.getInstance();
    private final StoreManagementService storeManagementService = AppContext.getApplicationContext().getBean(StoreManagementService.class);
    private final int consumeBdbEventsInterval = ApplicationConfiguration.getConsumeBacklogBdbInterval();

    private BacklogBdbConsumer(BDBStorage bdbStorage)
    {
        super(bdbStorage);
    }

    static BacklogBdbConsumer getInstance(BDBStorage bdbStorage)
    {
        return new BacklogBdbConsumer(bdbStorage);
    }

    @Override
    boolean consumerEnabled()
    {
        return consumerEnabled(getBdbConsumerConfiguration());
    }

    @Override
    void handleAutoConsumeBdbEvents()
    {
        if (getBooleanValue(getBdbConsumerConfiguration(), "autoConsume", true))
        {
            doHandleAutoConsumeBdbEvents();
        }
        else
        {
            notifyBdbRecordCount();
        }
    }

    @Override
    void notifySuccessEvent(int count)
    {
        hubblePublisher.incrementCountForEvent(Metric.CONSUME_BACKLOG_QUEUE, count);
    }

    @Override
    void notifyFailedEvent(int count)
    {
        hubblePublisher.incrementCountForEvent(Metric.CONSUME_BACKLOG_QUEUE_FAILED, count);
    }

    private boolean consumerEnabled(Map<String, String> map)
    {
        return getBooleanValue(map, "enable", false);
    }

    private boolean getBooleanValue(Map<String, String> map, String key, boolean defaultValue)
    {
        String value = (map != null) ? map.get(key) : null;

        return (value != null) ? Boolean.parseBoolean(value) : defaultValue;
    }

    private Map<String, String> getBdbConsumerConfiguration()
    {
        Map<String, String> map = null;

        try
        {
            map = storeManagementService.getGlobalStoreValue("SMP_REPORTING_BACKLOG_BDB_CONSUMERS");
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return (map != null) ? map : Collections.<String, String>emptyMap();
    }

    private void doHandleAutoConsumeBdbEvents()
    {
        if (Timer.delayExpired(getLastConsumeBdbEventsTime(), consumeBdbEventsInterval))
        {
            handleConsumeBdbEvents();
        }
    }

    private void notifyBdbRecordCount()
    {
        long count = getBdbRecordCount();

        if (count > 0)
        {
            LOGGER.warn("Bdb backlog records " + count);
        }
    }
}