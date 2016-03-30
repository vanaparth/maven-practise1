package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.impl.PubSubUtil;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.ireporter.publish.PublishTaskHandlerFactory;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

/**
 * @author Toch
 */
public class SMPReportingService
{
    private static final Logger LOGGER = Logger.getLogger(SMPReportingService.class);

    private SMPReportingService()
    {
    }

    public static SMPReportingService getInstance()
    {
        return new SMPReportingService();
    }

    private void startService()
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
        EventNotificationServiceThreadPool.getInstance().submit(new Task(Action.START));
    }

    private enum Action
    {
        START
    }

    private class Task implements Callable<Boolean>
    {
        private final Action action;

        private Task(Action action)
        {
            this.action = action;
        }

        @Override
        public Boolean call() throws Exception
        {
            if (action == Action.START)
            {
                startService();
            }

            return true;
        }
    }
}