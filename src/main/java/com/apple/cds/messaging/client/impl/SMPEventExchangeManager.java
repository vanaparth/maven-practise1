package com.apple.cds.messaging.client.impl;

import com.apple.iossystems.smp.reporting.config.SMPReportingApplicationConfiguration;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Toch
 */
public class SMPEventExchangeManager
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventExchangeManager.class);

    private List<EventSubscriberService> eventSubscribers = new ArrayList<EventSubscriberService>();

    private SMPEventExchangeManager()
    {
        init();
    }

    private void init()
    {
        try
        {
            setupSMPEventExchanges();
            startSMPEventSubscribers();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    // Use start to create and start the SMPExchangeManager
    private static SMPEventExchangeManager getInstance()
    {
        return new SMPEventExchangeManager();
    }

    public static void start()
    {
        getInstance();
    }

    private void setupSMPEventExchanges() throws Exception
    {
        List<SMPEventExchange> exchanges = new ArrayList<SMPEventExchange>();

        // For now, create one exchange with one queue
        SMPEventExchange exchange = SMPEventExchange.getInstance(SMPReportingApplicationConfiguration.getSMPEventsExchangeName());
        exchange.register(SMPEventExchangeQueue.getInstance(SMPReportingApplicationConfiguration.getLogLevelEventName()));

        // Register the exchange
        exchanges.add(exchange);

        // Create the PubSub exchanges
        createPubSubExchanges(exchanges);
    }

    private void createPubSubExchanges(List<SMPEventExchange> exchanges) throws Exception
    {
        for (SMPEventExchange exchange : exchanges)
        {
            PubSubUtil.createExchange(exchange.getName(), "topic", true);
            createPubSubSharedQueues(exchange);
        }
    }

    private void createPubSubSharedQueues(SMPEventExchange exchange) throws IOException
    {
        String exchangeName = exchange.getName();

        Iterator<SMPEventExchangeQueue> iterator = exchange.iterator();

        while (iterator.hasNext())
        {
            SMPEventExchangeQueue exchangeQueue = iterator.next();
            String queueName = exchangeQueue.getName();

            PubSubUtil.createSharedQueue(queueName, exchangeName, calculateRoutingKey(exchangeName, queueName), true);

            registerSMPEventSubscriber(exchangeQueue);
        }
    }

    private String calculateRoutingKey(String exchange, String queueName)
    {
        return "*.*.*.*.*." + queueName;
    }

    // TODO: a way of determining which subscriber to add to which exchange queue
    // Here, we can add more types of subscribers like an email report subscriber
    private void registerSMPEventSubscriber(SMPEventExchangeQueue exchangeQueue)
    {
        eventSubscribers.add(EventSubscriberServiceFactory.newIReporterSubscriber(exchangeQueue.getName()));
    }

    private void startSMPEventSubscribers() throws Exception
    {
        for (EventSubscriberService eventSubscriber : eventSubscribers)
        {
            startSMPEventSubscriber(eventSubscriber);
        }
    }

    private void startSMPEventSubscriber(EventSubscriberService eventSubscriber) throws Exception
    {
        try
        {
            eventSubscriber.begin();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }
}
