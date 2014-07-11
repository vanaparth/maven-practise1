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

        SMPEventExchange exchange = SMPEventExchange.getInstance(SMPReportingApplicationConfiguration.getSMPEventsExchangeName());
        exchange.register(SMPEventExchangeQueue.getInstance(SMPReportingApplicationConfiguration.getLogLevelEventName()));

        exchanges.add(exchange);

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
