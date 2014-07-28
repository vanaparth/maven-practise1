package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.util.ValueSelector;
import com.apple.iossystems.smp.reporting.ireporter.analytics.IReporterAnalytics;
import com.apple.iossystems.smp.reporting.ireporter.analytics.IReporterMetric;
import com.apple.iossystems.smp.reporting.ireporter.configuration.AuditConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;

/**
 * @author Toch
 */
class AuditPublishService extends IReporterPublishService
{
    private AuditPublishService(IReporterAnalytics analytics) throws Exception
    {
        super(analytics);
    }

    static AuditPublishService getInstance(IReporterAnalytics statistics) throws Exception
    {
        return new AuditPublishService(statistics);
    }

    @Override
    IReporterConfigurationService getConfigurationService()
    {
        return AuditConfigurationService.getInstance();
    }

    @Override
    boolean publishReady()
    {
        return true;
    }

    @Override
    long getLastPublishTime()
    {
        return ValueSelector.getLongValueWithDefault(getAnalytics().getMetricStatistics(IReporterMetric.AUDIT_PUBLISH_TIME), 0);
    }
}
