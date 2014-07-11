package com.apple.cds.messaging.client.impl;

import com.apple.iossystems.smp.domain.event.SMPEventConstants;
import com.apple.iossystems.smp.reporting.data.IReporterDataReport;
import com.apple.iossystems.smp.reporting.data.SMPDataReport;
import com.apple.iossystems.smp.reporting.publisher.ReportsPublisher;
import com.apple.iossystems.smp.reporting.publisher.ReportsPublisherFactory;

import java.util.Map;

/**
 * @author Toch
 */
public class EventSubscriberServiceIReporter<LogEvent> extends EventSubscriberServiceSMP<LogEvent>
{
    private ReportsPublisher reportsPublisher = ReportsPublisherFactory.newIReporter();

    private IReporterDataReport.Builder reportBuilder = IReporterDataReport.Builder.getInstance();

    private EventSubscriberServiceIReporter(String queueName)
    {
        super(queueName);
    }

    protected static EventSubscriberServiceIReporter getInstance(String queueName)
    {
        return new EventSubscriberServiceIReporter(queueName);
    }

    @Override
    protected void handleEvent(com.apple.iossystems.logging.pubsub.LogEvent logEvent)
    {
        postReport(logEvent);
    }

    private void postReport(com.apple.iossystems.logging.pubsub.LogEvent logEvent)
    {
        Map<String, String> metaData = logEvent.getMetadata();

        SMPDataReport report = reportBuilder.event(metaData.get(SMPEventConstants.EVENT_TYPE)).
                timestamp(SMPEventConstants.TIMESTAMP).user(SMPEventConstants.DSID).
                fpan(SMPEventConstants.FPAN_FIRST_SIX).location(SMPEventConstants.SEID).build();

        reportsPublisher.postReport(report);
    }
}
