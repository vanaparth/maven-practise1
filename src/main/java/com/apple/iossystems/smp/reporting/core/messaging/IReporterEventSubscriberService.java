package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.impl.SMPEventSubscriberService;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledNotification;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTaskHandler;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterService;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class IReporterEventSubscriberService<LogEvent> extends SMPEventSubscriberService<LogEvent>
{
    private static final Logger LOGGER = Logger.getLogger(IReporterEventSubscriberService.class);

    private IReporterService iReporterService = IReporterService.getInstance();

    private TaskHandler taskHandler;

    private EventRecord pendingEventRecord;

    private IReporterEventSubscriberService(String queueName) throws Exception
    {
        super(queueName);
    }

    static IReporterEventSubscriberService getInstance(String queueName) throws Exception
    {
        return new IReporterEventSubscriberService(queueName);
    }

    @Override
    public void handleEvent(com.apple.iossystems.logging.pubsub.LogEvent logEvent)
    {
        EventRecord record = EventRecord.getInstance();

        record.putAll(logEvent.getMetadata());

        if (!iReporterService.postSMPEvent(record))
        {
            handleFailedRequest(record);
        }
    }

    private void handleFailedRequest(EventRecord record)
    {
        try
        {
            pendingEventRecord = record;

            pause();

            startTaskHandler();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void startTaskHandler()
    {
        if (!taskHandlerIsActive())
        {
            taskHandler = getTaskHandler();
        }
    }

    private void stopTaskHandler()
    {
        if (taskHandlerIsActive())
        {
            taskHandler.shutdown();
        }
    }

    private boolean taskHandlerIsActive()
    {
        return ((taskHandler != null) && (!taskHandler.isShutdown()));
    }

    private TaskHandler getTaskHandler()
    {
        TaskHandler handler = new TaskHandler();

        handler.init();

        return handler;
    }

    private void checkIReporterService()
    {
        try
        {
            if ((pendingEventRecord != null) && (iReporterService.postSMPEvent(pendingEventRecord)))
            {
                pendingEventRecord = null;

                stopTaskHandler();

                resume();
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private class TaskHandler implements ScheduledTaskHandler
    {
        private ScheduledNotification scheduledNotification;

        private TaskHandler()
        {
        }

        private void init()
        {
            startScheduledTasks();
        }

        private void startScheduledTasks()
        {
            scheduledNotification = ScheduledNotification.getInstance(this, 60 * 1000);
        }

        @Override
        public void handleEvent()
        {
            checkIReporterService();
        }

        private void shutdown()
        {
            scheduledNotification.shutdown();
        }

        private boolean isShutdown()
        {
            return scheduledNotification.isShutdown();
        }
    }
}