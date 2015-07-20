package com.apple.iossystems.smp.reporting.core.logging;

import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;
import com.apple.iossystems.smp.reporting.core.email.ManageDeviceEvent;
import com.apple.iossystems.smp.reporting.core.email.ProvisionCardEvent;
import com.apple.iossystems.smp.reporting.core.util.LogMessage;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class EmailEventLogger
{
    private static final Logger LOGGER = Logger.getLogger(EmailEventLogger.class);

    private final boolean loggingEnabled;

    public EmailEventLogger(boolean loggingEnabled)
    {
        this.loggingEnabled = loggingEnabled;
    }

    private void log(LogMessage logMessage)
    {
        if (loggingEnabled)
        {
            LOGGER.info(logMessage.toString());
        }
    }

    public void log(ProvisionCardEvent provisionCardEvent)
    {
        log(new LogMessage().add(GsonBuilderFactory.getInstance().toJson(provisionCardEvent, ProvisionCardEvent.class)));
    }

    public void log(ManageDeviceEvent manageDeviceEvent)
    {
        log(new LogMessage().add(GsonBuilderFactory.getInstance().toJson(manageDeviceEvent, ManageDeviceEvent.class)));
    }
}