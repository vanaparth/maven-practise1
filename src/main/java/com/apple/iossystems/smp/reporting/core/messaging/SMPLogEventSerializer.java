package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.iossystems.logging.pubsub.LogEvent;
import com.apple.iossystems.logging.pubsub.LogEventSerializer;

/**
 * @author Toch
 */
public class SMPLogEventSerializer extends LogEventSerializer<LogEvent>
{
    private SMPLogEventSerializer()
    {
    }

    public static SMPLogEventSerializer getInstance()
    {
        return new SMPLogEventSerializer();
    }
}