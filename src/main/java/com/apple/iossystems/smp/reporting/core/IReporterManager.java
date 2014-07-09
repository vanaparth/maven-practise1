package com.apple.iossystems.smp.reporting.core;

import com.apple.cds.messaging.client.impl.*;
import com.apple.iossystems.logging.pubsub.LogEvent;
import com.apple.iossystems.smp.reporting.data.SMPDataReport;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Toch
 */
public class IReporterManager implements SMPReporterManager
{
    private static final Logger LOGGER = Logger.getLogger(IReporterManager.class);

    private List<EventSubscriberServiceSMP> eventSubscribers = new ArrayList<EventSubscriberServiceSMP>();

    private IReporterManager() throws Exception
    {
        init();
    }

    public static SMPReporterManager newInstance() throws Exception
    {
        return new IReporterManager();
    }

    private void init() throws Exception
    {
        setupSMPEventExchanges();
    }

    private void setupSMPEventExchanges() throws Exception
    {
        List<SMPEventExchange> exchanges = new ArrayList<SMPEventExchange>();

        // Create one exchange with one queue
        SMPEventExchange exchange = SMPEventExchange.newInstance(SMPEventConfiguration.EXCHANGE_NAME);
        exchange.register(SMPEventExchangeQueue.newInstance(SMPEventConfiguration.LOG_LEVEL_EVENT));

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

            // Here, we can add more types of subscribers like an email report subscriber
            registerSMPEventSubscriber(exchangeQueue);
        }
    }

    private String calculateRoutingKey(String exchange, String queueName)
    {
        return "*.*.*.*.*." + queueName;
    }

    // TODO: a way of determining which subscriber to add to which exchange queue
    private void registerSMPEventSubscriber(SMPEventExchangeQueue exchangeQueue)
    {
        try
        {
            eventSubscribers.add(EventSubscriberServiceIReporter.newInstance(exchangeQueue.getName()));
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void startSMPEventSubscribers() throws Exception
    {
        for (EventSubscriberServiceSMP eventSubscriber : eventSubscribers)
        {
            startSMPEventSubscriber(eventSubscriber);
        }
    }

    private void startSMPEventSubscriber(EventSubscriberServiceSMP eventSubscriber) throws Exception
    {
        try
        {
            eventSubscriber.setupAndStart();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    // Called by BrokerService in SMPBroker
    public static void startNewIReporter()
    {
        try
        {
            IReporterManager.newInstance().start();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    @Override
    public void start() throws Exception
    {
        startSMPEventSubscribers();
    }
}
