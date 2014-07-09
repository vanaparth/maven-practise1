package com.apple.iossystems.smp.reporting.service;

import com.apple.iossystems.smp.reporting.data.SMPDataReport;

/**
 * @author Toch
 */
public interface ReporterService
{
    public void postReport(SMPDataReport report);
}
