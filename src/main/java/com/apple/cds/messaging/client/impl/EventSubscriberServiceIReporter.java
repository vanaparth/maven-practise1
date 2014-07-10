package com.apple.cds.messaging.client.impl;

/**
 * @author Toch
 */

import com.apple.cds.analysis.OperationalAnalytics;
import com.apple.cds.keystone.core.OperationalAnalyticsManager;
import com.apple.cds.messaging.client.ConsumerServiceProperties;
import com.apple.cds.messaging.client.Delivery;
import com.apple.cds.messaging.client.DeliveryHandler;
import com.apple.cds.messaging.client.Serializer;
import com.apple.cds.messaging.client.events.AbstractConsumerServiceEventListener;
import com.apple.cds.messaging.client.events.Event;
import com.apple.cds.messaging.client.events.EventId;
import com.apple.cds.messaging.client.exception.ServiceException;
import com.apple.iossystems.logging.pubsub.LogEventSerializer;
import com.apple.iossystems.smp.domain.event.SMPEventConstants;
import com.apple.iossystems.smp.reporting.data.IReporterDataReport;
import com.apple.iossystems.smp.reporting.data.SMPDataReport;
import com.apple.iossystems.smp.reporting.service.ReporterService;
import com.apple.iossystems.smp.reporting.service.ReporterServiceFactory;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * Most of this code was taken from LoggingSubscriberService
 */
public class EventSubscriberServiceIReporter<LogEvent> extends EventSubscriberServiceSMP<LogEvent>
{
    private static final Logger LOGGER = Logger.getLogger(EventSubscriberServiceIReporter.class);
    private String queueName;
    private LoggingSubscriberMQServiceImpl<LogEvent> subscriber;

    /**
     * New stuff
     */
    private ReporterService reporterService;
    private IReporterDataReport.Builder reportBuilder = IReporterDataReport.Builder.newInstance();

    private EventSubscriberServiceIReporter(String queueName) throws Exception
    {
        this.queueName = queueName;
        this.reporterService = ReporterServiceFactory.newIReporter();

        // Toch: Should getProperties still take LoggingSubscriberService?
        subscriber = new LoggingSubscriberMQServiceImpl<LogEvent>(getProperties("LoggingSubscriberService", queueName),
                new LoggingSubscriberHandler<LogEvent>(), new LogEventSerializer<LogEvent>());
    }

    /**
     * Toch: Are these properties still valid?
     */
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
            // refactor cassandra write and elastic write to be called here
            com.apple.iossystems.logging.pubsub.LogEvent logEvent = (com.apple.iossystems.logging.pubsub.LogEvent) delivery.getMessage();
            handle(logEvent);
        }
    }

    private void handle(com.apple.iossystems.logging.pubsub.LogEvent logEvent)
    {
        postReport(logEvent);
    }

    private void postReport(com.apple.iossystems.logging.pubsub.LogEvent logEvent)
    {
        Map<String, String> metaData = logEvent.getMetadata();

        SMPDataReport report = reportBuilder.event(metaData.get(SMPEventConstants.EVENT_TYPE)).
                timestamp(SMPEventConstants.TIMESTAMP).user(SMPEventConstants.DSID).
                fpan(SMPEventConstants.FPAN_FIRST_SIX).location(SMPEventConstants.SEID).build();

        reporterService.postReport(report);
    }

    public static EventSubscriberServiceIReporter newInstance(String queueName) throws Exception
    {
        return new EventSubscriberServiceIReporter(queueName);
    }

    public void setupAndStart() throws Exception
    {
        setMonitor(OperationalAnalyticsManager.getInstance().getOperationalAnalytics());

        AbstractConsumerServiceEventListener listener = new AbstractConsumerServiceEventListener<com.apple.iossystems.logging.pubsub.LogEvent>() {

            @Override
            public void onEvent(EventId.ConsumerEventId eventId, Event<com.apple.iossystems.logging.pubsub.LogEvent> event) {
                LOGGER.info("Caught Consumer Event " + eventId.getName());
                LOGGER.info(event);
            }

            @Override
            public void onEvent(EventId.ServiceEventId eventId, Event<com.apple.iossystems.logging.pubsub.LogEvent> event) {
                LOGGER.info("Caught Service Event " + eventId.getName());
                LOGGER.info(event);
            }

        };

        subscriber.setEventListener(listener);

        start();
    }

    /**
     * TESTING
     */
    public void testPostReport(Map<String, String> metaData)
    {
        SMPDataReport report = reportBuilder.event(metaData.get(SMPEventConstants.EVENT_TYPE)).
                timestamp(SMPEventConstants.TIMESTAMP).user(SMPEventConstants.DSID).
                fpan(SMPEventConstants.FPAN_FIRST_SIX).location(SMPEventConstants.SEID).build();

        reporterService.postReport(report);
    }
}

