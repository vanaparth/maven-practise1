package com.apple.iossystems.smp.reporting.core.logging;

import com.apple.cds.keystone.config.PropertyManager;
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

    private boolean loggingEnabled = PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.email.log", false);

    public EmailEventLogger()
    {
    }

    private void log(String message)
    {
        if (loggingEnabled)
        {
            LOGGER.info(message);
        }
    }

    public void log(ProvisionCardEvent provisionCardEvent)
    {
        LogMessage logMessage = new LogMessage();

        logMessage.add(GsonBuilderFactory.getInstance().toJson(provisionCardEvent, ProvisionCardEvent.class));

        log(logMessage.toString());
    }

    public void log(ManageDeviceEvent manageDeviceEvent)
    {
        LogMessage logMessage = new LogMessage();

        logMessage.add(GsonBuilderFactory.getInstance().toJson(manageDeviceEvent, ManageDeviceEvent.class));

        log(logMessage.toString());
    }
}