package com.apple.iossystems.smp.reporting.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
public class SMPDataReportsBuffer
{
    private List<SMPDataReport> buffer = new ArrayList<SMPDataReport>();

    private SMPDataReportsBuffer()
    {
    }

    public static SMPDataReportsBuffer getInstance()
    {
        return new SMPDataReportsBuffer();
    }

    // A better way to do this without synchronizing? Assumes multiple event consumer threads will access this
    public synchronized void add(SMPDataReport report)
    {
        buffer.add(report);
    }

    public synchronized List<SMPDataReport> getAndFlushBuffers()
    {
        List<SMPDataReport> tempBuffer = buffer;
        buffer = new ArrayList<SMPDataReport>();

        return tempBuffer;
    }

    public synchronized int size()
    {
        return buffer.size();
    }

    public synchronized boolean isEmpty()
    {
        return buffer.isEmpty();
    }

    public synchronized boolean notEmpty()
    {
        return (!buffer.isEmpty());
    }
}
