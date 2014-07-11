package com.apple.iossystems.smp.reporting.publisher;

/**
 * @author Toch
 */
public class ReportingAudit
{
    private long lastReportSentTimestamp;

    private long lastConfigurationLoadTimestamp;

    private ReportingAudit()
    {
    }

    public static ReportingAudit getInstance()
    {
        return new ReportingAudit();
    }

    public long getLastReportSentTimestamp()
    {
        return lastReportSentTimestamp;
    }

    public long getLastConfigurationLoadTimestamp()
    {
        return lastConfigurationLoadTimestamp;
    }

    public void setReportSentTimestamp(long val)
    {
        lastReportSentTimestamp = val;
    }

    public void setConfigurationLoadTimestamp(long val)
    {
        lastConfigurationLoadTimestamp = val;
    }

    public void setReportSentTimestampCurrentTime()
    {
        setReportSentTimestamp(System.currentTimeMillis());
    }

    public void setConfigurationLoadTimestampCurrentTime()
    {
        setConfigurationLoadTimestamp(System.currentTimeMillis());
    }
}
