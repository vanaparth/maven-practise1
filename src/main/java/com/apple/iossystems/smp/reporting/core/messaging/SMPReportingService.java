package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.impl.PubSubUtil;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.ireporter.publish.PublishTaskHandlerFactory;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class SMPReportingService
{
    private static final Logger LOGGER = Logger.getLogger(SMPReportingService.class);

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
        if (ApplicationConfiguration.rabbitConsumersEnabled())
        {
            PublishTaskHandlerFactory publishTaskHandlerFactory = PublishTaskHandlerFactory.getInstance();

            SMPReportingSubscriberService.getInstance(EventType.REPORTS.getQueueName(), publishTaskHandlerFactory.getReportsPublishTaskHandler()).begin();

            SMPReportingSubscriberService.getInstance(EventType.PAYMENT.getQueueName(), publishTaskHandlerFactory.getPaymentPublishTaskHandler()).begin();

            SMPReportingSubscriberService.getInstance(EventType.LOYALTY.getQueueName(), publishTaskHandlerFactory.getLoyaltyPublishTaskHandler()).begin();
        }
    }

    public void start()
    {
    }
}