package com.apple.iossystems.smp.reporting.publisher;

/**
 * @author Toch
 */
public class ReportsPublisherFactory
{
    public static ReportsPublisher newIReporter()
    {
        return ReportsPublisherIReporter.getInstance();
    }
}
