package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.event.EventType;

/**
 * @author Toch
 */
class ReportsPublishTaskHandler extends PublishTaskHandler
{
    private ReportsPublishTaskHandler()
    {
        super(EventType.REPORTS, PublishMetric.getReportsMetrics(), ReportsPublishService.getInstance(), AuditPublishService.getInstance());
    }

    static ReportsPublishTaskHandler getInstance()
    {
        return new ReportsPublishTaskHandler();
    }
}