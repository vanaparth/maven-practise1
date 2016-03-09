package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.logging.local.BDBStorage;
import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.analytics.ResultMetric;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.utils.JSONUtils;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * @author Toch
 */
class BdbPublisher
{
    private static final Logger LOGGER = Logger.getLogger(BdbPublisher.class);

    private final EventNotificationServiceThreadPool threadPool = EventNotificationServiceThreadPool.getInstance();
    private final EventHubblePublisher eventHubblePublisher = EventHubblePublisher.getInstance(getMetricMap());
    private final BDBStorage bdbStorage;

    private final boolean publishEventsEnabled = ApplicationConfiguration.publishEventsEnabled();

    private BdbPublisher(BDBStorage bdbStorage)
    {
        this.bdbStorage = bdbStorage;
    }

    public static BdbPublisher getInstance(BDBStorage bdbStorage)
    {
        return new BdbPublisher(bdbStorage);
    }

    private Map<EventType, ResultMetric> getMetricMap()
    {
        Map<EventType, ResultMetric> map = new HashMap<>();

        map.put(EventType.REPORTS, new ResultMetric(Metric.PUBLISH_REPORTS_BACKLOG_EVENT_QUEUE, Metric.PUBLISH_REPORTS_BACKLOG_EVENT_QUEUE_FAILED));
        map.put(EventType.PAYMENT, new ResultMetric(Metric.PUBLISH_PAYMENT_BACKLOG_EVENT_QUEUE, Metric.PUBLISH_PAYMENT_BACKLOG_EVENT_QUEUE_FAILED));
        map.put(EventType.LOYALTY, new ResultMetric(Metric.PUBLISH_LOYALTY_BACKLOG_EVENT_QUEUE, Metric.PUBLISH_LOYALTY_BACKLOG_EVENT_QUEUE_FAILED));

        return map;
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

    private void doPublishEventRecords(EventRecords records)
    {
        try
        {
            for (EventRecord record : records.getList())
            {
                publishEventRecord(record);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void publishEventRecords(EventRecords records)
    {
        if (publishEventsEnabled)
        {
            doPublishEventRecords(records);
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

    public void publishEvents(EventRecords records)
    {
        try
        {
            threadPool.submit(new Task(records));
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