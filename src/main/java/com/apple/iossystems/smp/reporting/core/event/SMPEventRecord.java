package com.apple.iossystems.smp.reporting.core.event;

import com.apple.iossystems.smp.reporting.core.geo.Coordinates;
import com.apple.iossystems.smp.reporting.core.util.SecurityProvider;
import org.apache.commons.lang.StringUtils;

/**
 * @author Toch
 */
public class SMPEventRecord
{
    private SMPEventRecord()
    {
    }

    public static void maskAttributes(EventRecord record)
    {
        setLocation(record);

        setHash(record, EventAttribute.CARD_ID);
        setHash(record, EventAttribute.COMPANION_DEVICE_SERIAL_NUMBER);
        setHash(record, EventAttribute.DEVICE_SERIAL_NUMBER);
        setHash(record, EventAttribute.DPAN_ID);
        setHash(record, EventAttribute.DSID);
        setHash(record, EventAttribute.FPAN_ID);
        setHash(record, EventAttribute.TRANSACTION_ID);
    }

    private static void setLocation(EventRecord record)
    {
        String key = EventAttribute.DEVICE_LOCATION.key();
        String value = record.getAttributeValue(key);

        if (StringUtils.isNotBlank(value))
        {
            record.setAttributeValue(key, Coordinates.parse(value).truncateToInteger().getReverseString());
        }
    }

    private static void setHash(EventRecord record, EventAttribute attribute)
    {
        String key = attribute.key();
        String value = record.getAttributeValue(key);

        if (StringUtils.isNotBlank(value))
        {
            record.setAttributeValue(key, SecurityProvider.getHash(value));
        }
    }
}