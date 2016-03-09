package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.logging.local.BDBStorage;
import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledNotification;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTaskHandler;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.hubble.HubblePublisher;
import com.apple.iossystems.smp.service.StoreManagementService;
import com.apple.iossystems.smp.utils.JSONUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * @author Toch
 */
class BdbConsumer implements ScheduledTaskHandler
{
    private static final Logger LOGGER = Logger.getLogger(BdbConsumer.class);

    private final NotificationService notificationService = SMPEventNotificationService.getInstance().getPublisher();
    private final HubblePublisher hubblePublisher = HubblePublisher.getInstance();
    private final BDBStorage bdbStorage;

    private final int bdbBatchSize = ApplicationConfiguration.getLogServiceBdbBatchSize();

    private StoreManagementService storeManagementService = AppContext.getApplicationContext().getBean(StoreManagementService.class);

    private BdbConsumer(BDBStorage bdbStorage)
    {
        this.bdbStorage = bdbStorage;

        init();
    }

    public static BdbConsumer getInstance(BDBStorage bdbStorage)
    {
        return new BdbConsumer(bdbStorage);
    }

    private void init()
    {
        startScheduledTasks();
    }

    private void startScheduledTasks()
    {
        ScheduledNotification.getInstance(this, 5 * 60 * 1000);
    }

    @Override
    public final void handleEvent()
    {
        handleConsumeEvents();
    }

    private void handleConsumeEvents()
    {
        if (consumerEnabled())
        {
            doHandleConsumeEvents();
        }
    }

    private void doHandleConsumeEvents()
    {
        int totalCount = 0;
        int maxBacklogRecords = 100000;

        while (totalCount < maxBacklogRecords)
        {
            EventRecords records = consumeBdbEvents();

            int recordCount = records.size();

            if (recordCount > 0)
            {
                totalCount += recordCount;

                notificationService.publishEvents(records);
            }
            else
            {
                break;
            }
        }
    }

    private EventRecords consumeBdbEvents()
    {
        EventRecords records;

        try
        {
            records = doConsumeBdbEvents();

            hubblePublisher.incrementCountForEvent(Metric.CONSUME_BACKLOG_QUEUE, records.size());
        }
        catch (Exception e)
        {
            records = EventRecords.getInstance();

            LOGGER.error(e.getMessage(), e);

            hubblePublisher.incrementCountForEvent(Metric.CONSUME_BACKLOG_QUEUE_FAILED);
        }

        return records;
    }

    private EventRecords doConsumeBdbEvents()
    {
        EventRecords records = EventRecords.getInstance();

        List<byte[]> bdbRecords = bdbStorage.get(bdbBatchSize);

        if (bdbRecords != null)
        {
            for (byte[] bdbRecord : bdbRecords)
            {
                if (bdbRecord != null)
                {
                    EventRecord record = EventRecord.getInstance();

                    Map<String, String> data = GsonBuilderFactory.getInstance().fromJson(new String(bdbRecord), JSONUtils.MAPTYPE);

                    record.putAll(data);

                    records.add(record);
                }
            }
        }

        return records;
    }

    private boolean consumerEnabled()
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

        return (map != null);
    }

    public void start()
    {
    }
}