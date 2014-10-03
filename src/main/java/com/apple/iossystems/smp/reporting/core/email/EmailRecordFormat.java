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

    public static String formatCardDescription(String cardDescription)
    {
        return (cardDescription != null) ? ("****" + cardDescription) : "";
    }

    public static String getFmipSource(EmailRecord record)
    {
        String value = null;

        ManageCardEvent manageCardEvent = record.getManageCardEvent();

        if (manageCardEvent != null)
        {
            FmipSource fmipSource = manageCardEvent.getFmipSource();

            if (fmipSource != null)
            {
                value = fmipSource.getDescription();
            }
        }

        return ValidValue.getStringValueWithDefault(value, "");
    }

    public static String getValidValue(String value)
    {
        return ValidValue.getStringValueWithDefault(value, "");
    }

    public static String getDeviceType(EmailRecord record)
    {
        String deviceType = record.getDeviceType();

        if (deviceType != null)
        {
            String value = getDeviceTypeFromCode(deviceType);

            if ((value != null) && (!value.isEmpty()))
            {
                deviceType = value;
            }
        }

        return ValidValue.getStringValueWithDefault(deviceType, "");
    }

    private static String getDeviceTypeFromCode(String value)
    {
        if (value.equals("1"))
        {
            return "iPhone";
        }
        else if (value.equals("2"))
        {
            return "iPad";
        }
        else if (value.equals("3"))
        {
            return "Watch";
        }
        else
        {
            return "";
        }
    }
}