package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.logging.local.BDBStorage;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class BacklogEventNotificationService implements NotificationService
{
    private static final Logger LOGGER = Logger.getLogger(BacklogEventNotificationService.class);

    private static final BacklogEventNotificationService INSTANCE = new BacklogEventNotificationService();

    private BdbPublisher bdbPublisher;

    private BacklogEventNotificationService()
    {
        init();
    }

    public static BacklogEventNotificationService getInstance()
    {
        return INSTANCE;
    }

    private void init()
    {
        BDBStorage bdbStorage = getBdbStorage();

        if (bdbStorage != null)
        {
            bdbPublisher = BdbPublisher.getInstance(bdbStorage);

            BdbConsumer.getInstance(bdbStorage).start();
        }
        else
        {
            LOGGER.warn("Unable to start BDB publisher and consumer");
        }
    }

    private BDBStorage getBdbStorage()
    {
        BDBStorage bdbStorage = null;

        try
        {
            bdbStorage = new BDBStorage(ApplicationConfiguration.getBacklogBdbStore());
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return bdbStorage;
    }

    @Override
    public void publishEvents(EventRecords records)
    {
        try
        {
            bdbPublisher.publishEvents(records);
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean isOnline()
    {
        return (bdbPublisher != null);
    }
}