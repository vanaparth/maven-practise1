package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.logging.LogServiceFactoryStrategy;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;

/**
 * @author Toch
 */
public class SMPEventLogServiceFactoryStrategy implements LogServiceFactoryStrategy
{
    @Override
    public String getClassname()
    {
        return ApplicationConfigurationManager.getLogServiceClass();
    }
}