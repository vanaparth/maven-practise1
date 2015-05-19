package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.SMPEventRecord;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Toch
 */
public class IReporterEvent
{
    private static final Set<String> EVENT_ATTRIBUTES = getEventAttributes();

    private IReporterEvent()
    {
    }

    private static Set<String> getEventAttributes()
    {
        Set<String> eventAttributes = new HashSet<>();

        eventAttributes.add(EventAttribute.ATHENA_COLOR.key());
        eventAttributes.add(EventAttribute.BUNDLE_ID.key());
        eventAttributes.add(EventAttribute.CARD_BIN.key());
        eventAttributes.add(EventAttribute.EVENT.key());
        eventAttributes.add(EventAttribute.CARD_ID.key());
        eventAttributes.add(EventAttribute.CARD_ISSUER.key());
        eventAttributes.add(EventAttribute.CARD_SOURCE.key());
        eventAttributes.add(EventAttribute.CARD_STATUS.key());
        eventAttributes.add(EventAttribute.CARD_TYPE.key());
        eventAttributes.add(EventAttribute.COMPANION_DEVICE_SERIAL_NUMBER.key());
        eventAttributes.add(EventAttribute.COMPANION_USER_AGENT.key());
        eventAttributes.add(EventAttribute.CONVERSATION_ID.key());
        eventAttributes.add(EventAttribute.COUNTRY.key());
        eventAttributes.add(EventAttribute.CURRENCY.key());
        eventAttributes.add(EventAttribute.DEVICE_LANGUAGE.key());
        eventAttributes.add(EventAttribute.DEVICE_LOCATION.key());
        eventAttributes.add(EventAttribute.DEVICE_SERIAL_NUMBER.key());
        eventAttributes.add(EventAttribute.DEVICE_TYPE.key());
        eventAttributes.add(EventAttribute.DPAN_ID.key());
        eventAttributes.add(EventAttribute.DSID.key());
        eventAttributes.add(EventAttribute.FPAN_ID.key());
        eventAttributes.add(EventAttribute.OTP.key());
        eventAttributes.add(EventAttribute.PNO.key());
        eventAttributes.add(EventAttribute.PROVISION_RESPONSE.key());
        eventAttributes.add(EventAttribute.PROVISION_STATUS.key());
        eventAttributes.add(EventAttribute.TIMESTAMP.key());
        eventAttributes.add(EventAttribute.USE_CASE_TYPE.key());
        eventAttributes.add(EventAttribute.USER_AGENT.key());
        eventAttributes.add(EventAttribute.MERCHANT_ID.key());
        eventAttributes.add(EventAttribute.TRANSACTION_AMOUNT.key());
        eventAttributes.add(EventAttribute.TRANSACTION_ID.key());
        eventAttributes.add(EventAttribute.TRANSACTION_STATUS.key());
        eventAttributes.add(EventAttribute.WATCH_MATERIAL_PART_NUMBER.key());

        return eventAttributes;
    }

    public static EventRecord processEventRecord(EventRecord record)
    {
        record.removeAttributesIfAbsent(EVENT_ATTRIBUTES);

        SMPEventRecord.maskAttributes(record);

        return record;
    }
}