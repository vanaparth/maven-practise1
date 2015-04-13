package com.apple.cds.messaging.client.impl;

import com.apple.cds.analysis.OperationalAnalytics;
import com.apple.cds.messaging.client.ConsumerServiceProperties;
import com.apple.cds.messaging.client.events.AbstractConsumerServiceEventListener;
import com.apple.cds.messaging.client.exception.ServiceException;
import com.apple.iossystems.logging.pubsub.LogEventSerializer;
import com.apple.iossystems.logging.pubsub.LoggingSubscriberServiceBase;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public abstract class SMPEventSubscriberService<LogEvent> extends LoggingSubscriberServiceBase<LogEvent>
{
    private BasicConsumerService<LogEvent> consumerService;

    protected SMPEventSubscriberService(String queueName)
    {
        init(queueName);
    }

    private void init(String queueName)
    {
        SMPEventDeliveryHandler<LogEvent> smpEventDeliveryHandler = new SMPEventDeliveryHandler<>();

        consumerService = new SMPEventConsumerService<>(getProperties("LoggingSubscriberService", queueName), smpEventDeliveryHandler, new LogEventSerializer<LogEvent>());

        smpEventDeliveryHandler.setEventHandler(this);
    }

    private ConsumerServiceProperties getProperties(String serviceName, String queueName)
    {
        ConsumerServiceProperties properties = new ConsumerServicePropertiesImpl();

        properties.setRabbitmqHost(ApplicationConfigurationManager.getKeystoneRabbitHost());
        properties.setRabbitmqPort(Integer.valueOf(ApplicationConfigurationManager.getKeystoneRabbitPort()));
        properties.setRabbitmqUser(ApplicationConfigurationManager.getKeystoneRabbitUser());
        properties.setRabbitmqPassword(ApplicationConfigurationManager.getKeystoneRabbitPassword());
        properties.setRabbitmqVirtualhost(ApplicationConfigurationManager.getKeystoneRabbitVirtualHost());
        properties.setRabbitConnectionCount(ApplicationConfigurationManager.getRabbitConsumerThreadsCount());
        properties.setServiceConsumerPrefetchCount(ApplicationConfigurationManager.getRabbitConsumerThreadsPrefetchCount());

        properties.setServiceConsumerQueue(queueName);
        properties.setServiceName(serviceName);
        properties.setServiceConsumerTransactional(false);

        return properties;
    }

    @Override
    public void setEventListener(AbstractConsumerServiceEventListener eventListener)
    {
        consumerService.setEventListener(eventListener);
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
        consumerService.start();
    }

    @Override
    public void stop() throws ServiceException
    {
        consumerService.stop();
    }

    @Override
    public void pause() throws ServiceException
    {
        consumerService.pause();
    }

    @Override
    public void resume() throws ServiceException
    {
        consumerService.resume();
    }

    @Override
    public boolean isStarted()
    {
        return consumerService.isStarted();
    }

    @Override
    public boolean isStopped()
    {
        return consumerService.isStopped();
    }

    @Override
    public boolean isPaused()
    {
        return consumerService.isPaused();
    }

    @Override
    public boolean isQuiescent()
    {
        return consumerService.isQuiescent();
    }

    public final void begin()
    {
        try
        {
            start();
        }
        catch (Exception e)
        {
            Logger.getLogger(SMPEventSubscriberService.class).error(e);
        }
    }

    public abstract void handleEvent(com.apple.iossystems.logging.pubsub.LogEvent logEvent);
}