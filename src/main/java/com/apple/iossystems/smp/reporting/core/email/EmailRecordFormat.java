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

    public static String formatCardDisplayNumber(String cardDisplayNumber)
    {
        return ((cardDisplayNumber != null) ? ("****" + cardDisplayNumber) : "");
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

        return getValidValue(value);
    }

    public static String getValidValue(String value)
    {
        return ValidValue.getStringValueWithDefault(value, "");
    }
}