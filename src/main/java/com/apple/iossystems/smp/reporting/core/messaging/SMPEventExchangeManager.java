package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.impl.EventSubscriberService;
import com.apple.cds.messaging.client.impl.PubSubUtil;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
import org.apache.log4j.Logger;

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
        setupSMPEventExchanges();
    }

    public static SMPEventExchangeManager getInstance()
    {
        return new SMPEventExchangeManager();
    }

    private void setupSMPEventExchanges()
    {
        List<SMPEventExchange> exchanges = new ArrayList<SMPEventExchange>();

        SMPEventExchange exchange = SMPEventExchange.getInstance(ApplicationConfigurationManager.getSMPEventsExchangeName());
        exchange.addQueue(SMPEventExchangeQueue.getInstance(ApplicationConfigurationManager.getLogLevelEventName()));

        exchanges.add(exchange);

        createPubSubExchanges(exchanges);
    }

    private void createPubSubExchanges(List<SMPEventExchange> exchanges)
    {
        for (SMPEventExchange exchange : exchanges)
        {
            createExchange(exchange);

            if (exchange.isActive())
            {
                createPubSubSharedQueues(exchange);
            }
        }
    }

    private void createExchange(SMPEventExchange exchange)
    {
        try
        {
            PubSubUtil.createExchange(exchange.getName(), "topic", true);
            exchange.setActive(true);
        }
        catch (Exception e)
        {
            exchange.setActive(false);
            LOGGER.error(e);
        }
    }

    private void createPubSubSharedQueues(SMPEventExchange exchange)
    {
        Iterator<SMPEventExchangeQueue> iterator = exchange.iterator();

        while (iterator.hasNext())
        {
            SMPEventExchangeQueue queue = iterator.next();

            createSharedQueue(exchange, queue);

            if (queue.isActive())
            {
                addSMPEventSubscriber(queue);
            }
        }
    }

    private void createSharedQueue(SMPEventExchange exchange, SMPEventExchangeQueue queue)
    {
        try
        {
            String queueName = queue.getName();

            PubSubUtil.createSharedQueue(queueName, exchange.getName(), calculateRoutingKey(queueName), true);

            queue.setActive(true);
        }
        catch (Exception e)
        {
            queue.setActive(false);
            LOGGER.error(e);
        }
    }

    private String calculateRoutingKey(String queueName)
    {
        return "*.*.*.*.*." + queueName;
    }

    private void addSMPEventSubscriber(SMPEventExchangeQueue queue)
    {
        try
        {
            eventSubscribers.add(EventSubscriberServiceFactory.getIReporterEventSubscriberService(queue.getName()));
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void startSMPEventSubscribers()
    {
        for (EventSubscriberService eventSubscriber : eventSubscribers)
        {
            startSMPEventSubscriber(eventSubscriber);
        }
    }

    private void startSMPEventSubscriber(EventSubscriberService eventSubscriber)
    {
        eventSubscriber.begin();
    }

    public void start()
    {
        startSMPEventSubscribers();
    }
}
