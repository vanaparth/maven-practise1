package com.apple.iossystems.smp.reporting.ireporter.publish;

import java.util.List;

/**
 * @author Toch
 */
public class AuditRequest
{
    private List<AuditRecord> auditRecords;

    public AuditRequest(List<AuditRecord> auditRecords)
    {
        this.auditRecords = auditRecords;
    }

    public String getVersion()
    {
        return "1.0";
    }

    public List<AuditRecord> getAuditRecords()
    {
        return auditRecords;
    }
}