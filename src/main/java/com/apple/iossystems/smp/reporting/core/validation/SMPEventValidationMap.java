package com.apple.iossystems.smp.reporting.core.validation;

import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.SMPDeviceEvent;

import java.util.*;

/**
 * @author Toch
 */
class SMPEventValidationMap
{
    private final Map<SMPDeviceEvent, Set<String>> validationMap = getValidationMap();

    private SMPEventValidationMap()
    {
    }

    static SMPEventValidationMap getInstance()
    {
        return new SMPEventValidationMap();
    }

    private Map<SMPDeviceEvent, Set<String>> getValidationMap()
    {
        Map<SMPDeviceEvent, Set<String>> map = new HashMap<>();

        map.put(SMPDeviceEvent.CHECK_CARD, new HashSet<>(Arrays.asList("bin", "ccd", "cid", "con", "cst", "did", "dua", "evt", "lcd", "pno", "ptp", "sno", "tim")));
        map.put(SMPDeviceEvent.PROVISION_CARD, new HashSet<>(Arrays.asList("ast", "bin", "ccd", "con", "ctp", "did", "dpn", "dua", "evt", "fpn", "lcd", "pno", "psr", "pst", "ptp", "sno", "tim")));
        map.put(SMPDeviceEvent.SUSPEND_CARD, new HashSet<>(Arrays.asList("con", "cst", "dpn", "evt", "pno", "ssr", "tim")));
        map.put(SMPDeviceEvent.UNLINK_CARD, new HashSet<>(Arrays.asList("con", "cst", "dpn", "evt", "pno", "ssr", "tim")));
        map.put(SMPDeviceEvent.RESUME_CARD, new HashSet<>(Arrays.asList("con", "cst", "dpn", "evt", "pno", "ssr", "tim")));
        map.put(SMPDeviceEvent.PUT_PENDING, new HashSet<>(Arrays.asList("con", "evt", "pno", "tim")));
        map.put(SMPDeviceEvent.POST_COMMAND, new HashSet<>(Arrays.asList("con", "evt", "pno", "tim")));
        map.put(SMPDeviceEvent.GET_OTP_RESOLUTION_METHODS, new HashSet<>(Arrays.asList("con", "dpn", "evt", "otp", "pno", "tim")));
        map.put(SMPDeviceEvent.SEND_OTP, new HashSet<>(Arrays.asList("con", "dpn", "evt", "otp", "pno", "tim")));

        return map;
    }

    boolean isValid(EventRecord record)
    {
        boolean result = true;

        Set<String> requiredKeys = validationMap.get(SMPDeviceEvent.getEvent(record));

        if (requiredKeys != null)
        {
            for (String key : requiredKeys)
            {
                if (record.getAttributeValue(key) == null)
                {
                    result = false;
                    break;
                }
            }
        }

        return result;
    }
}