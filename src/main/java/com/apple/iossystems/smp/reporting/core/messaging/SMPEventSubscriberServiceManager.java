package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.impl.EventSubscriberService;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.ireporter.publish.EventTaskHandler;
import com.apple.iossystems.smp.reporting.ireporter.publish.PublishTaskHandlerFactory;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
public class SMPEventSubscriberServiceManager
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventSubscriberServiceManager.class);

    private final List<EventSubscriberService> eventSubscriberServices = new ArrayList<>();

    private SMPEventSubscriberServiceManager()
    {
        init();
    }

    public static SMPEventSubscriberServiceManager getInstance()
    {
        return new SMPEventSubscriberServiceManager();
    }

    private void init()
    {
        createEventExchange();

        startConsumers();
    }

    private void createEventExchange()
    {
        if (ApplicationConfiguration.subscribeEventsEnabled())
        {
            doCreateEventExchange();
        }
    }

    private void doCreateEventExchange()
    {
        String exchange = ApplicationConfiguration.getSMPEventsExchangeName();

        PubSubService pubSubService = PubSubService.getInstance();

        createExchange(pubSubService, exchange);

        createQueue(pubSubService, exchange, EventType.REPORTS);
        createQueue(pubSubService, exchange, EventType.PAYMENT);
        createQueue(pubSubService, exchange, EventType.LOYALTY);
    }

    private void createExchange(PubSubService pubSubService, String exchangeName)
    {
        try
        {
            pubSubService.createExchange(exchangeName, "topic", true);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void createQueue(PubSubService pubSubService, String exchange, EventType eventType)
    {
        try
        {
            pubSubService.createQueue(exchange, eventType.getQueueName());
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void startConsumers()
    {
        if (ApplicationConfiguration.rabbitConsumersEnabled())
        {
            PublishTaskHandlerFactory publishTaskHandlerFactory = PublishTaskHandlerFactory.getInstance();

            eventSubscriberServices.add(startConsumer(EventType.REPORTS, publishTaskHandlerFactory.getReportsPublishTaskHandler()));
            eventSubscriberServices.add(startConsumer(EventType.PAYMENT, publishTaskHandlerFactory.getPaymentPublishTaskHandler()));
            eventSubscriberServices.add(startConsumer(EventType.LOYALTY, publishTaskHandlerFactory.getLoyaltyPublishTaskHandler()));
        }
    }

    private EventSubscriberService startConsumer(EventType eventType, EventTaskHandler eventTaskHandler)
    {
        EventSubscriberService eventSubscriberService = SMPEventSubscriberService.getInstance(eventType.getQueueName(), eventTaskHandler);

        eventSubscriberService.startConsumerService();

        return eventSubscriberService;
    }

    public void start()
    {
    }

    public void shutdown()
    {
        for (EventSubscriberService eventSubscriberService : eventSubscriberServices)
        {
            eventSubscriberService.shutdown();
        }
    }
}