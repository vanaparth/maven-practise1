package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.analytics.Analytics;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.util.ValidValue;
import com.apple.iossystems.smp.reporting.ireporter.configuration.AuditConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;

/**
 * @author Toch
 */
class AuditPublishService extends IReporterPublishService
{
    private AuditPublishService(Analytics analytics) throws Exception
    {
        super(analytics);
    }

    static AuditPublishService getInstance(Analytics statistics) throws Exception
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
        return ValidValue.getLongValueWithDefault(getAnalytics().getMetric(Metric.AUDIT_PUBLISH_TIME), 0);
    }
}
