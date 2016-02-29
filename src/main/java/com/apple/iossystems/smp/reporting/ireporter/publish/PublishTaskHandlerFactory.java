package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;

/**
 * @author Toch
 */
public class PublishTaskHandlerFactory
{
    private PublishTaskHandlerFactory()
    {
    }

    public static PublishTaskHandlerFactory getInstance()
    {
        return new PublishTaskHandlerFactory();
    }

    public EventTaskHandler getReportsPublishTaskHandler()
    {
        return ApplicationConfiguration.publishEventsEnabled() ? ReportsPublishTaskHandler.getInstance() : OfflinePublishTaskHandler.getInstance();
    }

    public EventTaskHandler getPaymentPublishTaskHandler()
    {
        return ApplicationConfiguration.publishEventsEnabled() ? PaymentPublishTaskHandler.getInstance() : OfflinePublishTaskHandler.getInstance();
    }

    public EventTaskHandler getLoyaltyPublishTaskHandler()
    {
        return ApplicationConfiguration.publishEventsEnabled() ? LoyaltyPublishTaskHandler.getInstance() : OfflinePublishTaskHandler.getInstance();
    }
}