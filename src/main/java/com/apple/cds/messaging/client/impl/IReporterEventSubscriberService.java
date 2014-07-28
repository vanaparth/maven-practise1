package com.apple.cds.messaging.client.impl;

import com.apple.iossystems.smp.reporting.core.event.SMPEventAttribute;
import com.apple.iossystems.smp.reporting.core.event.SMPEventRecord;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterService;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * @author Toch
 */
class IReporterEventSubscriberService<LogEvent> extends SMPEventSubscriberService<LogEvent>
{
    private IReporterService iReporterService = IReporterService.getInstance();

    private IReporterEventSubscriberService(String queueName)
    {
        super(queueName);
    }

    static IReporterEventSubscriberService getInstance(String queueName)
    {
        return new IReporterEventSubscriberService(queueName);
    }

    @Override
    void handleEvent(com.apple.iossystems.logging.pubsub.LogEvent logEvent)
    {
        postSMPEvent(logEvent);
    }

    private void postSMPEvent(com.apple.iossystems.logging.pubsub.LogEvent logEvent)
    {
        Map<String, String> metaData = logEvent.getMetadata();

        SMPEventRecord record = SMPEventRecord.Builder.getInstance().
                set(SMPEventAttribute.CARD_BIN, "iOS Systems Test").
                set(SMPEventAttribute.CARD_ISSUER, "iOS Systems Test").
                set(SMPEventAttribute.CARD_SOURCE, "iOS Systems Test").
                set(SMPEventAttribute.CARD_STATUS, "iOS Systems Test").
                set(SMPEventAttribute.CARD_TYPE, "iOS Systems Test").
                set(SMPEventAttribute.DEVICE_LOCATION_IP, "iOS Systems Test").
                set(SMPEventAttribute.DEVICE_LOCATION_LATLON, "iOS Systems Test").
                set(SMPEventAttribute.DEVICE_SERIAL_NUMBER, "iOS Systems Test").
                set(SMPEventAttribute.DPAN_GUID, "iOS Systems Test").
                set(SMPEventAttribute.EVENT, "iOS Systems Test").
                set(SMPEventAttribute.FAILURE_CODE, "iOS Systems Test").
                set(SMPEventAttribute.FPAN_GUID, "iOS Systems Test").
                set(SMPEventAttribute.PNO, "iOS Systems Test").
                set(SMPEventAttribute.TIMESTAMP, "iOS Systems Test").
                set(SMPEventAttribute.USER_AGENT, "iOS Systems Test").
                set(SMPEventAttribute.USER_ID, "iOS Systems Test").build();

        //iReporterService.postSMPEvent(record);

        // Logging test
        //Logger.getLogger(IReporterEventSubscriberService.class).info(record);
    }
}
