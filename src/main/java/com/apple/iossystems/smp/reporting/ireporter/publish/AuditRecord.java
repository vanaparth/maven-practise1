package com.apple.iossystems.smp.reporting.ireporter.publish;

/**
 * @author Toch
 */
public class AuditRecord
{
    private final int sent;
    private final int failed;
    private final int backlog;
    private final int lost;

    public AuditRecord(int sent, int failed, int backlog, int lost)
    {
        this.sent = sent;
        this.failed = failed;
        this.backlog = backlog;
        this.lost = lost;
    }

    public int getSent()
    {
        return sent;
    }

    public int getFailed()
    {
        return failed;
    }

    public int getBacklog()
    {
        return backlog;
    }

    public int getLost()
    {
        return lost;
    }
}