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

    public static void completeBuild(EventRecord record)
    {
        setTimestamp(record);
        setLocation(record);

        setHash(record, EventAttribute.DEVICE_SERIAL_NUMBER);
        setHash(record, EventAttribute.DPAN_ID);
        setHash(record, EventAttribute.DSID);
        setHash(record, EventAttribute.FPAN_ID);
    }

    private static void setTimestamp(EventRecord record)
    {
        record.setAttributeValue(EventAttribute.TIMESTAMP.key(), String.valueOf(System.currentTimeMillis()));
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