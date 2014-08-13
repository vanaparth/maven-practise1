package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.logging.LogLevel;
import com.apple.iossystems.logging.LogService;
import com.apple.iossystems.logging.LogServiceFactory;
import com.apple.iossystems.logging.LoggerType;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;
import com.apple.iossystems.smp.reporting.core.util.MapToPair;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Properties;

/**
 * @author Toch
 */
public class SMPEventNotificationService
{
    private static final Logger LOGGER = Logger.getLogger(SMPEventNotificationService.class);

    private static final SMPEventNotificationService INSTANCE = new SMPEventNotificationService();

    private boolean enabled = ApplicationConfigurationManager.getSMPEventsPublishEnable();

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

        try
        {
            logService = LogServiceFactory.createNewLogService(properties);
        }
        catch (Exception e)
        {
            enabled = false;
            LOGGER.error("Failed to initialize events logging service", e);
        }
    }

    private void publishEventRecord(EventRecord record)
    {
        logService.logEvent("event", LogLevel.EVENT, MapToPair.toPairs(record.getData()));
    }

    private void publishEventRecords(EventRecords records)
    {
        List<EventRecord> list = records.getList();

        for (EventRecord record : list)
        {
            SMPEventRecord.completeBuild(record);

            publishEventRecord(record);
        }
    }

    public void publishEvents(EventRecords records)
    {
        // Prevent any side effects
        try
        {
            if (enabled)
            {
                publishEventRecords(records);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }
}