package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Toch
 */
public class IReporterEventRecord
{
    private static final Set<String> EVENT_ATTRIBUTES = getAttributes();

    private IReporterEventRecord()
    {
    }

    private static Set<String> getAttributes()
    {
        Set<String> eventAttributes = new HashSet<String>();

        eventAttributes.add(EventAttribute.ATHENA_COLOR.key());
        eventAttributes.add(EventAttribute.CARD_BIN.key());
        eventAttributes.add(EventAttribute.CARD_EVENT.key());
        eventAttributes.add(EventAttribute.CARD_ID.key());
        eventAttributes.add(EventAttribute.CARD_ISSUER.key());
        eventAttributes.add(EventAttribute.CARD_SOURCE.key());
        eventAttributes.add(EventAttribute.CARD_STATUS.key());
        eventAttributes.add(EventAttribute.CARD_TYPE.key());
        eventAttributes.add(EventAttribute.CONVERSATION_ID.key());
        eventAttributes.add(EventAttribute.DEVICE_LANGUAGE.key());
        eventAttributes.add(EventAttribute.DEVICE_LOCATION.key());
        eventAttributes.add(EventAttribute.DEVICE_SERIAL_NUMBER.key());
        eventAttributes.add(EventAttribute.DEVICE_TYPE.key());
        eventAttributes.add(EventAttribute.DPAN_ID.key());
        eventAttributes.add(EventAttribute.DSID.key());
        eventAttributes.add(EventAttribute.FPAN_ID.key());
        eventAttributes.add(EventAttribute.PNO.key());
        eventAttributes.add(EventAttribute.PROVISION_STATUS.key());
        eventAttributes.add(EventAttribute.REGION.key());
        eventAttributes.add(EventAttribute.SUPPORTS_IN_APP_PAYMENT.key());
        eventAttributes.add(EventAttribute.TIMESTAMP.key());
        eventAttributes.add(EventAttribute.USE_CASE_TYPE.key());
        eventAttributes.add(EventAttribute.USER_AGENT.key());
        eventAttributes.add(EventAttribute.MERCHANT_ID.key());
        eventAttributes.add(EventAttribute.TRANSACTION_AMOUNT.key());
        eventAttributes.add(EventAttribute.TRANSACTION_ID.key());
        eventAttributes.add(EventAttribute.TRANSACTION_STATUS.key());

        return eventAttributes;
    }

    public static EventRecord removeAttributes(EventRecord record)
    {
        for (Map.Entry<String, String> entry : record.getData().entrySet())
        {
            String key = entry.getKey();

            if (!EVENT_ATTRIBUTES.contains(key))
            {
                record.removeAttributeValue(key);
            }
        }

        return record;
    }
}