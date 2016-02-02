package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.impl.PubSubUtil;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.ireporter.publish.PublishTaskHandler;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class SMPReportingService
{
    private static final Logger LOGGER = Logger.getLogger(SMPReportingService.class);

    private PublishTaskHandler publishTaskHandler = PublishTaskHandler.getInstance();

    private SMPReportingService()
    {
        init();
    }

    public static SMPReportingService getInstance()
    {
        return new SMPReportingService();
    }

    private void init()
    {
        createSMPEventExchange();

        startSubscribers();
    }

    private void createSMPEventExchange()
    {
        String exchangeName = ApplicationConfiguration.getSMPEventsExchangeName();

        createExchange(exchangeName);

        createPubSubQueue(exchangeName, EventType.REPORTS);
        createPubSubQueue(exchangeName, EventType.PAYMENT);
        createPubSubQueue(exchangeName, EventType.EMAIL);
        createPubSubQueue(exchangeName, EventType.LOYALTY);
    }

    private void createExchange(String exchangeName)
    {
        try
        {
            PubSubUtil.createExchange(exchangeName, "topic", true);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void createPubSubQueue(String exchangeName, EventType eventType)
    {
        try
        {
            String queueName = eventType.getQueueName();

            PubSubUtil.createSharedQueue(queueName, exchangeName, getRoutingKey(queueName), true);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private String getRoutingKey(String queueName)
    {
        return "*.*.*.*.*." + queueName;
    }

    private void startSubscribers()
    {
        if (ApplicationConfiguration.isRabbitConsumersEnabled())
        {
            SMPReportingSubscriberService.getInstance(EventType.REPORTS.getQueueName(), this).begin();

            SMPReportingSubscriberService.getInstance(EventType.PAYMENT.getQueueName(), this).begin();

            SMPReportingSubscriberService.getInstance(EventType.EMAIL.getQueueName(), this).begin();

            SMPReportingSubscriberService.getInstance(EventType.LOYALTY.getQueueName(), this).begin();
        }
    }

    public boolean postSMPEvent(EventRecord eventRecord)
    {
        return publishTaskHandler.add(eventRecord);
    }

    public void start()
    {
    }
}