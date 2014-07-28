package com.apple.cds.messaging.client.impl;

import com.apple.cds.analysis.OperationalAnalytics;
import com.apple.cds.messaging.client.ConsumerServiceProperties;
import com.apple.cds.messaging.client.Delivery;
import com.apple.cds.messaging.client.DeliveryHandler;
import com.apple.cds.messaging.client.Serializer;
import com.apple.cds.messaging.client.events.AbstractConsumerServiceEventListener;
import com.apple.cds.messaging.client.events.Event;
import com.apple.cds.messaging.client.events.EventId;
import com.apple.cds.messaging.client.exception.ServiceException;
import com.apple.iossystems.logging.pubsub.LogEventSerializer;
import org.apache.log4j.Logger;

public abstract class SMPEventSubscriberService<LogEvent> extends EventSubscriberService<LogEvent>
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventSubscriberService.class);

    private LoggingSubscriberMQServiceImpl<LogEvent> subscriber;

    public SMPEventSubscriberService(String queueName)
    {
        subscriber = new LoggingSubscriberMQServiceImpl<LogEvent>(getProperties("LoggingSubscriberService", queueName),
                new LoggingSubscriberHandler<LogEvent>(), new LogEventSerializer<LogEvent>());
    }

    private ConsumerServiceProperties getProperties(String serviceName, String queueName)
    {
        ConsumerServiceProperties properties = new ConsumerServicePropertiesImpl();
        properties.setServiceConsumerPrefetchCount(PubSubUtil.RABBIT_CONSUMERS_PREFETCH_COUNT);
        properties.setServiceConsumerThreads(PubSubUtil.RABBIT_CONSUMERS_THREADS);
        properties.setServiceConsumerQueue(queueName);
        properties.setServiceName(serviceName);

        if (null != PubSubUtil.RABBIT_USER)
        {
            LOGGER.info("Setting rabbit user to: " + PubSubUtil.RABBIT_USER);
            properties.setRabbitmqUser(PubSubUtil.RABBIT_USER);
        }

        if (null != PubSubUtil.RABBIT_PASS)
        {
            LOGGER.info("Setting rabbit pass to: XXX");
            properties.setRabbitmqPassword(PubSubUtil.RABBIT_PASS);
        }

        if (null != PubSubUtil.RABBIT_VIRTUAL_HOST)
        {
            LOGGER.info("Setting rabbit virtual to: " + PubSubUtil.RABBIT_VIRTUAL_HOST);
            properties.setRabbitmqVirtualhost(PubSubUtil.RABBIT_VIRTUAL_HOST);
        }

        properties.setRabbitmqHost(PubSubUtil.RABBIT_HOST);
        properties.setServiceConsumerTransactional(false);

        return properties;
    }

    private AbstractConsumerServiceEventListener createConsumerServiceEventListener()
    {
        AbstractConsumerServiceEventListener listener = new AbstractConsumerServiceEventListener<com.apple.iossystems.logging.pubsub.LogEvent>()
        {
            @Override
            public void onEvent(EventId.ConsumerEventId eventId, Event<com.apple.iossystems.logging.pubsub.LogEvent> event)
            {
            }

            @Override
            public void onEvent(EventId.ServiceEventId eventId, Event<com.apple.iossystems.logging.pubsub.LogEvent> event)
            {
            }
        };

        return listener;
    }

    @Override
    public <V extends AbstractConsumerServiceEventListener<LogEvent>> void setEventListener(V listener)
    {
        subscriber.setEventListener(listener);
    }

    @Override
    public OperationalAnalytics getMonitor()
    {
        return null;
    }

    @Override
    public void setMonitor(OperationalAnalytics analytics)
    {
    }

    @Override
    public void start() throws ServiceException
    {
        subscriber.start();
    }

    @Override
    public void stop() throws ServiceException
    {
        subscriber.stop();
    }

    @Override
    public void pause() throws ServiceException
    {
        subscriber.pause();
    }

    @Override
    public void resume() throws ServiceException
    {
        subscriber.resume();
    }

    @Override
    public boolean isStarted()
    {
        return subscriber.isStarted();
    }

    @Override
    public boolean isStopped()
    {
        return subscriber.isStopped();
    }

    @Override
    public boolean isPaused()
    {
        return subscriber.isPaused();
    }

    @Override
    public boolean isQuiescent()
    {
        return subscriber.isQuiescent();
    }

    private class LoggingSubscriberMQServiceImpl<LogEvent> extends BasicConsumerService<LogEvent>
    {
        private LogEventSerializer<LogEvent> serializer;

        LoggingSubscriberMQServiceImpl(ConsumerServiceProperties props, DeliveryHandler<LogEvent> deliveryHandler,
                                       LogEventSerializer<LogEvent> serializer)
        {
            super(props, deliveryHandler, serializer);
            this.serializer = serializer;
        }

        protected Serializer<LogEvent> getSerializer()
        {
            return serializer;
        }
    }

    private class LoggingSubscriberHandler<LogEvent> implements DeliveryHandler<LogEvent>
    {
        @Override
        public void handleDelivery(Delivery<LogEvent> delivery)
        {
            com.apple.iossystems.logging.pubsub.LogEvent logEvent = (com.apple.iossystems.logging.pubsub.LogEvent) delivery.getMessage();
            handle(logEvent);
        }
    }

    private void initEventListener()
    {
        setEventListener(createConsumerServiceEventListener());
    }

    private void handle(com.apple.iossystems.logging.pubsub.LogEvent logEvent)
    {
        handleEvent(logEvent);
    }

    @Override
    final void begin()
    {
        try
        {
            initEventListener();
            start();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }
}

