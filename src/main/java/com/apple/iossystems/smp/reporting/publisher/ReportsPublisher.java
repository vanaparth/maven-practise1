package com.apple.iossystems.smp.reporting.publisher;

import com.apple.iossystems.smp.reporting.data.SMPDataReport;

/**
 * @author Toch
 */
public interface ReportsPublisher
{
    public void postReport(SMPDataReport report);
}
