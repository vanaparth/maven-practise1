package com.apple.iossystems.smp.reporting.ireporter.publish;

/**
 * @author Toch
 */
public class AuditRecord
{
    private final int sent;
    private final int failed;
    private final int pending;
    private final int lost;

    public AuditRecord(int sent, int failed, int pending, int lost)
    {
        this.sent = sent;
        this.failed = failed;
        this.pending = pending;
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

    public int getPending()
    {
        return pending;
    }

    public int getLost()
    {
        return lost;
    }
}