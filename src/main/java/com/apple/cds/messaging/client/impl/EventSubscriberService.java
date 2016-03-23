package com.apple.cds.messaging.client.impl;

import com.apple.cds.analysis.OperationalAnalytics;
import com.apple.cds.messaging.client.ConsumerServiceProperties;
import com.apple.cds.messaging.client.events.AbstractConsumerServiceEventListener;
import com.apple.cds.messaging.client.exception.ServiceException;
import com.apple.iossystems.logging.pubsub.LogEvent;
import com.apple.iossystems.logging.pubsub.LoggingSubscriberServiceBase;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.messaging.SMPEventDeliveryHandler;
import com.apple.iossystems.smp.reporting.core.messaging.SMPLogEventSerializer;
import org.apache.log4j.Logger;

import java.net.InetAddress;

/**
 * @author Toch
 */
public abstract class EventSubscriberService extends LoggingSubscriberServiceBase<LogEvent>
{
    private final EventConsumerService consumerService;

    protected EventSubscriberService(String queueName)
    {
        consumerService = getEventConsumerService(queueName);
    }

    private EventConsumerService getEventConsumerService(String queueName)
    {
        SMPEventDeliveryHandler smpEventDeliveryHandler = new SMPEventDeliveryHandler();
        smpEventDeliveryHandler.setEventHandler(this);

        return new EventConsumerService(getProperties("EventSubscriberService", queueName), smpEventDeliveryHandler, SMPLogEventSerializer.getInstance());
    }

    private ConsumerServiceProperties getProperties(String serviceName, String queueName)
    {
        ConsumerServiceProperties properties = new ConsumerServicePropertiesImpl();

        properties.setRabbitmqHost(ApplicationConfiguration.getKeystoneRabbitHost());
        properties.setRabbitmqPort(Integer.parseInt(ApplicationConfiguration.getKeystoneRabbitPort()));
        properties.setRabbitmqUser(ApplicationConfiguration.getKeystoneRabbitUser());
        properties.setRabbitmqPassword(ApplicationConfiguration.getKeystoneRabbitPassword());
        properties.setRabbitmqVirtualhost(ApplicationConfiguration.getKeystoneRabbitVirtualHost());
        properties.setRabbitConnectionCount(ApplicationConfiguration.getRabbitConsumerThreadsCount());
        properties.setServiceConsumerPrefetchCount(ApplicationConfiguration.getRabbitConsumerThreadsPrefetchCount());
        properties.setServiceConsumerQueue(queueName);
        properties.setServiceName(serviceName);
        properties.setServiceConsumerTransactional(true);

        try
        {
            properties.setClientProvidedConsumerTagPrefix(InetAddress.getLocalHost().getHostName());
        }
        catch (Exception e)
        {
            Logger.getLogger(EventSubscriberService.class).error(e.getMessage(), e);
        }

        return properties;
    }

    @Override
    public final <V extends AbstractConsumerServiceEventListener<LogEvent>> void setEventListener(V eventListener)
    {
        consumerService.setEventListener(eventListener);
    }

    @Override
    public final OperationalAnalytics getMonitor()
    {
        return null;
    }

    @Override
    public final void setMonitor(OperationalAnalytics analytics)
    {
    }

    @Override
    public final void start() throws ServiceException
    {
        consumerService.start();
    }

    @Override
    public final void stop() throws ServiceException
    {
        consumerService.stop();
    }

    @Override
    public final void pause() throws ServiceException
    {
        consumerService.pause();
    }

    @Override
    public final void resume() throws ServiceException
    {
        consumerService.resume();
    }

    @Override
    public final boolean isStarted()
    {
        return consumerService.isStarted();
    }

    @Override
    public final boolean isStopped()
    {
        return consumerService.isStopped();
    }

    @Override
    public final boolean isPaused()
    {
        return consumerService.isPaused();
    }

    @Override
    public final boolean isQuiescent()
    {
        return consumerService.isQuiescent();
    }

    public final void startConsumerService()
    {
        try
        {
            start();
        }
        catch (Exception e)
        {
            Logger.getLogger(EventSubscriberService.class).error(e.getMessage(), e);
        }
    }

    public abstract void handleEvent(LogEvent logEvent);

    public abstract void shutdown();
}