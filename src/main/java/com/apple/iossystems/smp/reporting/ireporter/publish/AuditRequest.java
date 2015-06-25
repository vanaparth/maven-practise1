package com.apple.iossystems.smp.reporting.ireporter.publish;

import java.util.List;

/**
 * @author Toch
 */
public class AuditRequest
{
    private static final String VERSION = "1.0";
    private List<AuditRecord> auditRecords;

    public AuditRequest(List<AuditRecord> auditRecords)
    {
        this.auditRecords = auditRecords;
    }

    public String getVersion()
    {
        return VERSION;
    }

    public List<AuditRecord> getAuditRecords()
    {
        return auditRecords;
    }
}