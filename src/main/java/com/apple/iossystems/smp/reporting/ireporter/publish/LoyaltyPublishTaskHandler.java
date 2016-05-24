package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.event.EventType;

/**
 * @author Toch
 */
class LoyaltyPublishTaskHandler extends PublishTaskHandler
{
    private LoyaltyPublishTaskHandler()
    {
        super(EventType.LOYALTY, PublishMetric.getLoyaltyReportsMetrics(), LoyaltyReportsPublishService.getInstance(), LoyaltyAuditPublishService.getInstance());
    }

    static LoyaltyPublishTaskHandler getInstance()
    {
        return new LoyaltyPublishTaskHandler();
    }
}