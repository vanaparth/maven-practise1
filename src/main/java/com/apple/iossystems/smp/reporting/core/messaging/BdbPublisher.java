package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.logging.local.BDBStorage;
import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.reporting.core.analytics.ResultMetric;
import com.apple.iossystems.smp.reporting.core.concurrent.TaskExecutorService;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.utils.JSONUtils;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Toch
 */
class BdbPublisher
{
    private static final Logger LOGGER = Logger.getLogger(BdbPublisher.class);

    private final TaskExecutorService taskExecutorService = AppContext.getApplicationContext().getBean(TaskExecutorService.class);
    private final EventHubblePublisher eventHubblePublisher;
    private final BDBStorage bdbStorage;

    private BdbPublisher(BDBStorage bdbStorage, Map<EventType, ResultMetric> metricMap)
    {
        this.bdbStorage = bdbStorage;
        eventHubblePublisher = EventHubblePublisher.getInstance(metricMap);
    }

    static BdbPublisher getInstance(BDBStorage bdbStorage, Map<EventType, ResultMetric> metricMap)
    {
        return new BdbPublisher(bdbStorage, metricMap);
    }

    private void publishEventRecord(EventRecord record)
    {
        try
        {
            bdbStorage.insert(GsonBuilderFactory.getInstance().toJson(record.getData(), JSONUtils.MAPTYPE).getBytes());

            eventHubblePublisher.incrementCountForSuccessEvent(EventType.getEventType(record));
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);

            eventHubblePublisher.incrementCountForFailedEvent(EventType.getEventType(record));
        }
    }

    private void publishEventRecords(EventRecords records)
    {
        for (EventRecord record : records.getList())
        {
            publishEventRecord(record);
        }
    }

    private void publishEventTask(EventRecords records)
    {
        try
        {
            publishEventRecords(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    void publishEvents(EventRecords records)
    {
        try
        {
            taskExecutorService.submit(new Task(records));
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private class Task implements Callable<Boolean>
    {
        private final EventRecords records;

        private Task(EventRecords records)
        {
            this.records = records;
        }

        @Override
        public Boolean call() throws Exception
        {
            publishEventTask(records);

            return true;
        }
    }
}