package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.event.EventType;

/**
 * @author Toch
 */
class PaymentPublishTaskHandler extends PublishTaskHandler
{
    private PaymentPublishTaskHandler()
    {
        super(EventType.PAYMENT, PublishMetric.getPaymentReportsMetrics(), PaymentReportsPublishService.getInstance(), PaymentAuditPublishService.getInstance());
    }

    static PaymentPublishTaskHandler getInstance()
    {
        return new PaymentPublishTaskHandler();
    }
}