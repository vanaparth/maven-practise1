package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.impl.EventSubscriberService;
import com.apple.iossystems.logging.pubsub.LogEvent;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.core.eventhandler.EventListener;
import com.apple.iossystems.smp.reporting.core.eventhandler.EventListenerFactory;
import com.apple.iossystems.smp.reporting.core.hubble.HubblePublisher;
import com.apple.iossystems.smp.reporting.ireporter.publish.EventTaskHandler;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
class SMPEventSubscriberService extends EventSubscriberService
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventSubscriberService.class);

    private final EventListener eventListener = EventListenerFactory.getInstance().getSMPConsumeEventListener();
    private final HubblePublisher hubblePublisher = HubblePublisher.getInstance();
    private final Map<EventType, Metric> metricMap = getMetricMap();

    private final EventTaskHandler eventTaskHandler;

    private SMPEventSubscriberService(String queueName, EventTaskHandler eventTaskHandler)
    {
        super(queueName);

        this.eventTaskHandler = eventTaskHandler;

        startConsumer();
    }

    static SMPEventSubscriberService getInstance(String queueName, EventTaskHandler eventTaskHandler)
    {
        return new SMPEventSubscriberService(queueName, eventTaskHandler);
    }

    private Map<EventType, Metric> getMetricMap()
    {
        Map<EventType, Metric> map = new HashMap<>();

        map.put(EventType.REPORTS, Metric.CONSUME_REPORTS_QUEUE);
        map.put(EventType.PAYMENT, Metric.CONSUME_PAYMENT_QUEUE);
        map.put(EventType.LOYALTY, Metric.CONSUME_LOYALTY_QUEUE);

        return map;
    }

    @Override
    public void handleEvent(LogEvent logEvent)
    {
        EventRecord record = EventRecord.getInstance();

        record.putAll(logEvent.getMetadata());

        sendEventRecord(record);

        notifyListeners(record);
    }

    @Override
    public void shutdown()
    {
        LOGGER.info("Shutting down SMPEventSubscriberService");

        stopConsumer();

        eventTaskHandler.shutdown();
    }

    private void sendEventRecord(EventRecord record)
    {
        eventTaskHandler.processEventRecord(record);
    }

    private void notifyListeners(EventRecord record)
    {
        notifyEventListener(record);

        notifyHubble(record);
    }

    private void notifyEventListener(EventRecord record)
    {
        try
        {
            EventRecords records = EventRecords.getInstance();

            records.add(record);

            eventListener.handleEvent(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void notifyHubble(EventRecord record)
    {
        try
        {
            Metric metric = metricMap.get(EventType.getEventType(record));

            if (metric != null)
            {
                hubblePublisher.incrementCountForEvent(metric);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void startConsumer()
    {
        try
        {
            start();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void stopConsumer()
    {
        try
        {
            stop();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }
}