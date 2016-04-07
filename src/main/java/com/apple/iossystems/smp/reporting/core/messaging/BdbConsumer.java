package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.logging.local.BDBStorage;
import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTask;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTaskHandler;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.utils.JSONUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * @author Toch
 */
abstract class BdbConsumer implements ScheduledTaskHandler
{
    private static final Logger LOGGER = Logger.getLogger(BdbConsumer.class);

    private final NotificationService notificationService = SMPEventNotificationService.getInstance().getPublisher();
    private final int bdbBatchSize = ApplicationConfiguration.getLogServiceBdbBatchSize();
    private long lastConsumeBdbEventsTime = System.currentTimeMillis();

    private final BDBStorage bdbStorage;

    BdbConsumer(BDBStorage bdbStorage)
    {
        this.bdbStorage = bdbStorage;

        init();
    }

    private void init()
    {
        startScheduledTasks();
    }

    private void startScheduledTasks()
    {
        ScheduledTask.getInstance(this, 5 * 60 * 1000);
    }

    @Override
    public void handleEvent()
    {
        handleConsumeEvents();
    }

    private void handleConsumeEvents()
    {
        if (consumerEnabled())
        {
            handleConsumeBdbEvents();
        }
        else
        {
            handleAutoConsumeBdbEvents();
        }
    }

    final void handleConsumeBdbEvents()
    {
        doHandleConsumeBdbEvents();
        lastConsumeBdbEventsTime = System.currentTimeMillis();
    }

    private void doHandleConsumeBdbEvents()
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

            notifySuccessEvent(records.size());
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);

            records = EventRecords.getInstance();

            notifyFailedEvent(1);
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

    final long getLastConsumeBdbEventsTime()
    {
        return lastConsumeBdbEventsTime;
    }

    final long getBdbRecordCount()
    {
        long count = 0;

        try
        {
            count = bdbStorage.size();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return count;
    }

    final void start()
    {
    }

    abstract boolean consumerEnabled();

    abstract void handleAutoConsumeBdbEvents();

    abstract void notifySuccessEvent(int count);

    abstract void notifyFailedEvent(int count);
}