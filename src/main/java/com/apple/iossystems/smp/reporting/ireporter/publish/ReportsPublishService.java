package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.analytics.Analytics;
import com.apple.iossystems.smp.reporting.core.analytics.Metric;
import com.apple.iossystems.smp.reporting.core.util.ValidValue;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.ReportsConfigurationService;

/**
 * @author Toch
 */
class ReportsPublishService extends IReporterPublishService
{
    private ReportsPublishService(Analytics analytics) throws Exception
    {
        super(analytics);
    }

    static ReportsPublishService getInstance(Analytics analytics) throws Exception
    {
        return new ReportsPublishService(analytics);
    }

    @Override
    IReporterConfigurationService getConfigurationService()
    {
        return ReportsConfigurationService.getInstance();
    }

    @Override
    boolean publishReady()
    {
        int available = ValidValue.getIntValueWithDefault(getAnalytics().getMetric(Metric.REPORTS_AVAILABLE_COUNT), 0);

        return ((available >= getConfiguration().getMaxBatchSize()) || ((available > 0) && publishDelayExpired()));
    }

    @Override
    long getLastPublishTime()
    {
        return ValidValue.getLongValueWithDefault(getAnalytics().getMetric(Metric.REPORTS_PUBLISH_TIME), 0);
    }
}
