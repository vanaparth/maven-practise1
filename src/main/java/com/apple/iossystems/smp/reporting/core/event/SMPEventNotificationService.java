package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.logging.LogService;
import com.apple.iossystems.logging.LogServiceFactory;
import com.apple.iossystems.logging.LoggerType;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
import com.apple.iossystems.smp.reporting.core.email.SMPEmailEvent;
import com.apple.iossystems.smp.reporting.core.util.MapToPair;
import org.apache.log4j.Logger;

import java.util.Properties;

/**
 * @author Toch
 */
public class SMPEventNotificationService
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventNotificationService.class);

    private static final SMPEventNotificationService INSTANCE = new SMPEventNotificationService();

    private LogService logService;

    private SMPEventNotificationService()
    {
        setSystemProperties();
        initLogService();
    }

    public static SMPEventNotificationService getInstance()
    {
        return INSTANCE;
    }

    private void setSystemProperties()
    {
        System.setProperty(ApplicationConfigurationManager.getRabbitHostKey(), ApplicationConfigurationManager.getKeystoneRabbitHost());
        System.setProperty(ApplicationConfigurationManager.getRabbitUserKey(), ApplicationConfigurationManager.getKeystoneRabbitUser());
        System.setProperty(ApplicationConfigurationManager.getRabbitPassKey(), ApplicationConfigurationManager.getKeystoneRabbitPass());
        System.setProperty(ApplicationConfigurationManager.getRabbitVHostKey(), ApplicationConfigurationManager.getKeystoneRabbitVHost());
    }

    private void initLogService()
    {
        Properties properties = new Properties();

        properties.setProperty(LogService.ORGANIZATION, "iossystems");
        properties.setProperty(LogService.ORGANIZATION_UNIT, "stockholm");
        properties.setProperty(LogService.DISTINGUISHED_NAME, "events");
        properties.setProperty(LogService.TYPE, LoggerType.HYBRIDPUBSUB.toString());
        properties.setProperty(LogService.EXCHANGE_NAME, ApplicationConfigurationManager.getSMPEventsExchangeName());
        properties.setProperty(LogService.STORE_NAME, "reporting");

        try
        {
            logService = LogServiceFactory.createNewLogService(properties);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }


    private void publishEventRecord(EventRecord record)
    {
        logService.logEvent("event", EventType.getLogLevel(record), MapToPair.toPairs(record.getData()));
    }

    private void publishEventRecords(EventRecords records)
    {
        for (EventRecord record : records.getList())
        {
            publishEventRecord(record);
        }
    }

    public void publishEvents(EventRecords records)
    {
        // Prevent any side effects
        try
        {
            publishEventRecords(records);

            publishEventRecords(SMPEmailEvent.getEventRecords(records));
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }
}