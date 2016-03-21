package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.logging.LogServiceFactoryStrategy;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;

/**
 * @author Toch
 */
class SMPEventLogServiceFactoryStrategy implements LogServiceFactoryStrategy
{
    private SMPEventLogServiceFactoryStrategy()
    {
    }

    static SMPEventLogServiceFactoryStrategy getInstance()
    {
        return new SMPEventLogServiceFactoryStrategy();
    }

    @Override
    public String getClassname()
    {
        return ApplicationConfiguration.getLogServiceClass();
    }
}