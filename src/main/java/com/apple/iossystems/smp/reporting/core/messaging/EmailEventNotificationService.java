package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.keystone.spring.AppContext;
import com.apple.iossystems.smp.reporting.core.concurrent.TaskExecutorService;
import com.apple.iossystems.smp.reporting.core.email.EmailEventService;
import com.apple.iossystems.smp.reporting.core.email.EmailServiceFactory;
import com.apple.iossystems.smp.reporting.core.email.SMPEmailEvent;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import org.apache.log4j.Logger;

import java.util.concurrent.Callable;

/**
 * @author Toch
 */
class EmailEventNotificationService implements NotificationService
{
    private static final Logger LOGGER = Logger.getLogger(EventNotificationService.class);

    private final EmailEventService emailService = EmailServiceFactory.getInstance().getEmailService();
    private final TaskExecutorService taskExecutorService = AppContext.getApplicationContext().getBean(TaskExecutorService.class);

    private EmailEventNotificationService()
    {
    }

    static EmailEventNotificationService getInstance()
    {
        return new EmailEventNotificationService();
    }

    private void publishEmailRecords(EventRecords records)
    {
        try
        {
            emailService.send(SMPEmailEvent.getEventRecords(records));
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private void publishEventTask(EventRecords records)
    {
        try
        {
            publishEmailRecords(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public void publishEvents(EventRecords records)
    {
        try
        {
            taskExecutorService.submit(new Task(records));
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean isOnline()
    {
        return true;
    }

    private class Task implements Callable<Boolean>
    {
        private final EventRecords records;

        private Task(EventRecords records)
        {
            this.records = records;
        }

        @Override
        public Boolean call() throws Exception
        {
            publishEventTask(records);

            return true;
        }
    }
}