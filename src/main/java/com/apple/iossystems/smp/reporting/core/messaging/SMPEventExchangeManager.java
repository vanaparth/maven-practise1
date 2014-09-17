package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.impl.PubSubUtil;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterService;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class SMPEventExchangeManager
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventExchangeManager.class);

    private SMPEventExchangeManager()
    {
        init();
    }

    public static SMPEventExchangeManager getInstance()
    {
        return new SMPEventExchangeManager();
    }

    private void init()
    {
        createSMPEventExchange();

        startSMPEventSubscribers();
    }

    private void createSMPEventExchange()
    {
        String exchangeName = ApplicationConfigurationManager.getSMPEventsExchangeName();

        createExchange(exchangeName);

        createPubSubQueue(exchangeName, EventType.REPORTS);
        createPubSubQueue(exchangeName, EventType.PAYMENT);
        createPubSubQueue(exchangeName, EventType.EMAIL);
    }

    private void createExchange(String exchangeName)
    {
        try
        {
            PubSubUtil.createExchange(exchangeName, "topic", true);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void createPubSubQueue(String exchangeName, EventType eventType)
    {
        try
        {
            String queueName = eventType.getQueueName();

            PubSubUtil.createSharedQueue(queueName, exchangeName, calculateRoutingKey(queueName), true);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private String calculateRoutingKey(String queueName)
    {
        return "*.*.*.*.*." + queueName;
    }

    private void startSMPEventSubscribers()
    {
        try
        {
            IReporterService service = IReporterService.getInstance();

            IReporterEventSubscriberService.getInstance(EventType.REPORTS.getQueueName(), service).begin();

            IReporterEventSubscriberService.getInstance(EventType.PAYMENT.getQueueName(), service).begin();

            IReporterEventSubscriberService.getInstance(EventType.EMAIL.getQueueName(), service).begin();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    public void start()
    {
    }
}