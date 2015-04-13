package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventType;
import com.apple.iossystems.smp.reporting.ireporter.publish.PublishTaskHandler;

/**
 * @author Toch
 */
public class SMPReportingService
{
    private PublishTaskHandler publishTaskHandler = PublishTaskHandler.getInstance();

    private SMPReportingService()
    {
    }

    public static SMPReportingService getInstance()
    {
        return new SMPReportingService();
    }

    public void start()
    {
        SMPReportingSubscriberService.getInstance(EventType.REPORTS.getQueueName(), this).begin();

        SMPReportingSubscriberService.getInstance(EventType.PAYMENT.getQueueName(), this).begin();

        SMPReportingSubscriberService.getInstance(EventType.EMAIL.getQueueName(), this).begin();
    }

    public boolean postSMPEvent(EventRecord eventRecord)
    {
        return publishTaskHandler.add(eventRecord);
    }
}