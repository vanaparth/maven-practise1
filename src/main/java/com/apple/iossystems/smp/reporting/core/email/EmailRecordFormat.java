package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.util.ValidValue;

/**
 * @author Toch
 */
class EmailRecordFormat
{
    private EmailRecordFormat()
    {
    }

    public static String getLocale(EmailRecord record, boolean useDefaultLocale)
    {
        return (useDefaultLocale ? "en_US" : record.getLocale());
    }

    public static String getFmipSource(EmailRecord record)
    {
        String value = null;

        ManageDeviceEvent manageDeviceEvent = record.getManageDeviceEvent();

        if (manageDeviceEvent != null)
        {
            FmipSource fmipSource = manageDeviceEvent.getFmipSource();

            if (fmipSource != null)
            {
                value = fmipSource.getDescription();
            }
        }

        return getValidValue(value);
    }

    public static String getValidValue(String value)
    {
        return ValidValue.getStringValueWithDefault(value, "");
    }
}