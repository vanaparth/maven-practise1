package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.core.hubble.HubblePublisher;
import com.apple.iossystems.smp.reporting.ireporter.publish.ResultMetric;

import java.util.Collections;
import java.util.Map;

/**
 * @author Toch
 */
public class EventHubblePublisher
{
    private final HubblePublisher hubblePublisher = HubblePublisher.getInstance();

    private final Map<EventType, ResultMetric> metricMap;

    private EventHubblePublisher(Map<EventType, ResultMetric> metricMap)
    {
        this.metricMap = (metricMap != null) ? metricMap : Collections.<EventType, ResultMetric>emptyMap();
    }

    public static EventHubblePublisher getInstance(Map<EventType, ResultMetric> metricMap)
    {
        return new EventHubblePublisher(metricMap);
    }

    private void incrementCountForEvent(EventType eventType, boolean success, int count)
    {
        ResultMetric resultMetric = metricMap.get(eventType);

        if (resultMetric != null)
        {
            Metric metric = success ? resultMetric.getSuccessMetric() : resultMetric.getFailedMetric();

            if (metric != null)
            {
                hubblePublisher.incrementCountForEvent(metric, count);
            }
        }
    }

    private void incrementCountForEvent(EventType eventType, boolean isSuccessEvent)
    {
        incrementCountForEvent(eventType, isSuccessEvent, 1);
    }

    public void incrementCountForSuccessEvent(EventType eventType)
    {
        incrementCountForEvent(eventType, true);
    }

    public void incrementCountForSuccessEvent(EventType eventType, int count)
    {
        incrementCountForEvent(eventType, true, count);
    }

    public void incrementCountForFailedEvent(EventType eventType)
    {
        incrementCountForEvent(eventType, false);
    }
}