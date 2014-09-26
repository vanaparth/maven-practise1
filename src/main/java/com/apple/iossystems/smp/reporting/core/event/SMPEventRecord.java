package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.reporting.core.geo.DeviceLocation;
import com.apple.iossystems.smp.reporting.core.util.SecurityProvider;

/**
 * @author Toch
 */
public class SMPEventRecord
{
    private SMPEventRecord()
    {
    }

    public static EventRecord maskSelectedAttributes(EventRecord record)
    {
        setLocation(record);

        setHash(record, EventAttribute.CARD_ID);
        setHash(record, EventAttribute.DEVICE_SERIAL_NUMBER);
        setHash(record, EventAttribute.DPAN_ID);
        setHash(record, EventAttribute.DSID);
        setHash(record, EventAttribute.FPAN_ID);
        setHash(record, EventAttribute.MERCHANT_ID);
        setHash(record, EventAttribute.TRANSACTION_ID);

        return record;
    }

    public static EventRecord removeEmailAttributes(EventRecord record)
    {
        record.removeAttributeValue(EventAttribute.ATHENA_CARD_EVENT.key());
        record.removeAttributeValue(EventAttribute.MANAGE_CARD_EVENT.key());
        record.removeAttributeValue(EventAttribute.MANAGE_CARD_EVENT_SOURCE.key());

        return record;
    }

    private static void setLocation(EventRecord record)
    {
        String key = EventAttribute.DEVICE_LOCATION.key();
        String value = record.getAttributeValue(key);

        if (value != null)
        {
            record.setAttributeValue(key, DeviceLocation.truncateCoordinates(DeviceLocation.getCoordinates(value)).getReverseString());
        }
    }

    private static void setHash(EventRecord record, EventAttribute attribute)
    {
        String key = attribute.key();
        String value = record.getAttributeValue(key);

        if (value != null)
        {
            record.setAttributeValue(key, SecurityProvider.getHash(value));
        }
    }
}