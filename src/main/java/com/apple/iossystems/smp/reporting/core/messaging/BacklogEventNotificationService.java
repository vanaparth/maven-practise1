package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.logging.local.BDBStorage;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.analytics.ResultMetric;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
class BacklogEventNotificationService implements NotificationService
{
    private static final Logger LOGGER = Logger.getLogger(BacklogEventNotificationService.class);

    private static final BacklogEventNotificationService INSTANCE = new BacklogEventNotificationService();

    private BdbPublisher bdbPublisher;

    private BacklogEventNotificationService()
    {
        init();
    }

    static BacklogEventNotificationService getInstance()
    {
        return INSTANCE;
    }

    private void init()
    {
        BDBStorage bdbStorage = getBdbStorage();

        if (bdbStorage != null)
        {
            bdbPublisher = BdbPublisher.getInstance(bdbStorage, getBdbPublisherMetricMap());

            BacklogBdbConsumer.getInstance(bdbStorage).start();
        }
        else
        {
            LOGGER.warn("Unable to start BDB publisher and consumer");
        }
    }

    private BDBStorage getBdbStorage()
    {
        BDBStorage bdbStorage = null;

        try
        {
            bdbStorage = new BDBStorage(ApplicationConfiguration.getBacklogBdbStore());
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return bdbStorage;
    }

    private Map<EventType, ResultMetric> getBdbPublisherMetricMap()
    {
        Map<EventType, ResultMetric> map = new HashMap<>();

        map.put(EventType.REPORTS, new ResultMetric(Metric.PUBLISH_REPORTS_BACKLOG_QUEUE, Metric.PUBLISH_REPORTS_BACKLOG_QUEUE_FAILED));
        map.put(EventType.PAYMENT, new ResultMetric(Metric.PUBLISH_PAYMENT_BACKLOG_QUEUE, Metric.PUBLISH_PAYMENT_BACKLOG_QUEUE_FAILED));
        map.put(EventType.LOYALTY, new ResultMetric(Metric.PUBLISH_LOYALTY_BACKLOG_QUEUE, Metric.PUBLISH_LOYALTY_BACKLOG_QUEUE_FAILED));

        return map;
    }

    @Override
    public void publishEvents(EventRecords records)
    {
        try
        {
            bdbPublisher.publishEvents(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean isOnline()
    {
        return (bdbPublisher != null);
    }
}