package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.messaging.client.impl.SMPEventSubscriberService;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledNotification;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledTaskHandler;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class SMPReportingSubscriberService<LogEvent> extends SMPEventSubscriberService<LogEvent>
{
    private static final Logger LOGGER = Logger.getLogger(SMPReportingSubscriberService.class);

    private SMPReportingService smpReportingService;

    private TaskHandler taskHandler;

    private EventRecord pendingEventRecord;

    private SMPReportingSubscriberService(String queueName, SMPReportingService smpReportingService)
    {
        super(queueName);

        this.smpReportingService = smpReportingService;
    }

    static SMPReportingSubscriberService getInstance(String queueName, SMPReportingService smpReportingService)
    {
        return new SMPReportingSubscriberService(queueName, smpReportingService);
    }

    @Override
    public void handleEvent(com.apple.iossystems.logging.pubsub.LogEvent logEvent)
    {
        EventRecord record = EventRecord.getInstance();

        record.putAll(logEvent.getMetadata());

        if (!smpReportingService.postSMPEvent(record))
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

    private void checkSMPReportingService()
    {
        try
        {
            if ((pendingEventRecord != null) && (smpReportingService.postSMPEvent(pendingEventRecord)))
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
            checkSMPReportingService();
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