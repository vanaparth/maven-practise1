package com.apple.iossystems.smp.reporting.data;

/**
 * @author Toch
 */
public interface SMPDataReport
{
    public String getEvent();

    public String getTimestamp();

    public String getUser();

    public String getFPAN();

    public String getLocation();
}
