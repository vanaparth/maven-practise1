package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;

/**
 * @author Toch
 */
public class EmailServiceFactory
{
    private EmailServiceFactory()
    {
    }

    public static EmailServiceFactory getInstance()
    {
        return new EmailServiceFactory();
    }

    public EmailEventService getEmailService()
    {
        return ApplicationConfiguration.emailEventsEnabled() ? EmailService.getInstance() : OfflineEmailService.getInstance();
    }
}