package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.cds.messaging.client.impl.EventSubscriberService;
import com.apple.iossystems.smp.reporting.core.concurrent.TaskExecutorService;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.ireporter.publish.EventTaskHandler;
import com.apple.iossystems.smp.reporting.ireporter.publish.PublishTaskHandlerFactory;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Toch
 */
public class SMPEventSubscriberServiceManager
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventSubscriberServiceManager.class);

    private final BlockingQueue<EventSubscriberService> eventSubscriberServices = new LinkedBlockingQueue<>();

    private SMPEventSubscriberServiceManager()
    {
        startServiceManager();
    }

    public static SMPEventSubscriberServiceManager getInstance()
    {
        return new SMPEventSubscriberServiceManager();
    }

    private void startServiceManager()
    {
        TaskExecutorService taskExecutorService = AppContext.getApplicationContext().getBean(TaskExecutorService.class);

        taskExecutorService.submit(new StartServiceManagerTask());
    }

    private void doStartServiceManager()
    {
        createEventExchange();

        startEventSubscribers();
    }

    private void shutdownServiceManager()
    {
        TaskExecutorService taskExecutorService = AppContext.getApplicationContext().getBean(TaskExecutorService.class);

        for (EventSubscriberService eventSubscriberService : eventSubscriberServices)
        {
            taskExecutorService.submit(new ShutdownEventSubscriberTask(eventSubscriberService));
        }
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

    private void createExchange(PubSubService pubSubService, String exchange)
    {
        try
        {
            pubSubService.createExchange(exchange, "topic", true);
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

    private void startEventSubscribers()
    {
        if (ApplicationConfiguration.rabbitConsumersEnabled())
        {
            TaskExecutorService taskExecutorService = AppContext.getApplicationContext().getBean(TaskExecutorService.class);

            PublishTaskHandlerFactory publishTaskHandlerFactory = PublishTaskHandlerFactory.getInstance();

            taskExecutorService.submit(new StartEventSubscriberTask(publishTaskHandlerFactory, EventType.REPORTS));
            taskExecutorService.submit(new StartEventSubscriberTask(publishTaskHandlerFactory, EventType.PAYMENT));
            taskExecutorService.submit(new StartEventSubscriberTask(publishTaskHandlerFactory, EventType.LOYALTY));
        }
    }

    public void shutdown()
    {
        shutdownServiceManager();
    }

    private class StartServiceManagerTask implements Callable<Boolean>
    {
        private StartServiceManagerTask()
        {
        }

        @Override
        public Boolean call() throws Exception
        {
            doStartServiceManager();

            return true;
        }
    }

    private class StartEventSubscriberTask implements Callable<Boolean>
    {
        private final PublishTaskHandlerFactory publishTaskHandlerFactory;
        private final EventType eventType;

        private StartEventSubscriberTask(PublishTaskHandlerFactory publishTaskHandlerFactory, EventType eventType)
        {
            this.publishTaskHandlerFactory = publishTaskHandlerFactory;
            this.eventType = eventType;
        }

        @Override
        public Boolean call() throws Exception
        {
            EventTaskHandler eventTaskHandler = null;

            if (eventType == EventType.REPORTS)
            {
                eventTaskHandler = publishTaskHandlerFactory.getReportsPublishTaskHandler();
            }
            else if (eventType == EventType.PAYMENT)
            {
                eventTaskHandler = publishTaskHandlerFactory.getPaymentPublishTaskHandler();
            }
            else if (eventType == EventType.LOYALTY)
            {
                eventTaskHandler = publishTaskHandlerFactory.getLoyaltyPublishTaskHandler();
            }

            if (eventTaskHandler != null)
            {
                eventSubscriberServices.offer(SMPEventSubscriberService.getInstance(eventType.getQueueName(), eventTaskHandler));
            }

            return true;
        }
    }

    private class ShutdownEventSubscriberTask implements Callable<Boolean>
    {
        private final EventSubscriberService eventSubscriberService;

        private ShutdownEventSubscriberTask(EventSubscriberService eventSubscriberService)
        {
            this.eventSubscriberService = eventSubscriberService;
        }

        @Override
        public Boolean call() throws Exception
        {
            eventSubscriberService.shutdown();

            return true;
        }
    }
}