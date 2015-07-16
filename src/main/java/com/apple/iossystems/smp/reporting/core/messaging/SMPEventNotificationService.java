package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.logging.LogService;
import com.apple.iossystems.logging.LogServiceFactory2;
import com.apple.iossystems.smp.reporting.core.email.EmailService;
import com.apple.iossystems.smp.reporting.core.email.SMPEmailEvent;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.core.eventhandler.EventListener;
import com.apple.iossystems.smp.reporting.core.eventhandler.EventListenerFactory;
import com.apple.iossystems.smp.reporting.core.util.MapToPair;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class SMPEventNotificationService
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventNotificationService.class);

    private static final SMPEventNotificationService INSTANCE = new SMPEventNotificationService();

    private EmailService emailService = EmailService.getInstance();

    private EventListener eventListener = EventListenerFactory.getInstance().getSMPPublishEventListener();

    private EventListener kistaEventListener = EventListenerFactory.getInstance().getSMPKistaEventListener();

    private LogService logService;

    private SMPEventNotificationService()
    {
        initLogService();
    }

    public static SMPEventNotificationService getInstance()
    {
        return INSTANCE;
    }

    private void initLogService()
    {
        try
        {
            logService = LogServiceFactory2.getInstance().createLogService(new SMPEventLogServiceConfigurator(), new SMPEventLogServiceFactoryStrategy());
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void publishEventRecord(EventRecord record)
    {
        try
        {
            logService.logEvent("event", EventType.getLogLevel(record), MapToPair.toPairs(record.getData()));
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void notifyEventListener(EventRecords records)
    {
        try
        {
            eventListener.handleEvent(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void notifyKista(EventRecords records)
    {
        try
        {
            kistaEventListener.handleEvent(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void publishEmailRecords(EventRecords records)
    {
        try
        {
            emailService.send(SMPEmailEvent.getEventRecords(records));
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void publishEventRecords(EventRecords records)
    {
        try
        {
            for (EventRecord record : records.getList())
            {
                publishEventRecord(record);
            }

            notifyEventListener(records);

            notifyKista(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    public void publishEvents(EventRecords records)
    {
        // Prevent any side effects
        try
        {
            publishEventRecords(records);

            publishEmailRecords(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    public boolean isOnline()
    {
        return (logService != null);
    }
}