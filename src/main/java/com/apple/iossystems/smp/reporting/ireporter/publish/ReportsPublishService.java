package com.apple.iossystems.smp.reporting.ireporter.publish;

import com.apple.iossystems.smp.reporting.core.util.ValueSelector;
import com.apple.iossystems.smp.reporting.ireporter.analytics.IReporterAnalytics;
import com.apple.iossystems.smp.reporting.ireporter.analytics.IReporterMetric;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfigurationService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.ReportsConfigurationService;

/**
 * @author Toch
 */
class ReportsPublishService extends IReporterPublishService
{
    private ReportsPublishService(IReporterAnalytics analytics) throws Exception
    {
        super(analytics);
    }

    static ReportsPublishService getInstance(IReporterAnalytics analytics) throws Exception
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
        int available = ValueSelector.getIntValueWithDefault(getAnalytics().getMetricStatistics(IReporterMetric.REPORTS_AVAILABLE_COUNT), 0);

        return ((available >= getConfiguration().getMaxBatchSize()) || ((available > 0) && publishDelayExpired()));
    }

    @Override
    long getLastPublishTime()
    {
        return ValueSelector.getLongValueWithDefault(getAnalytics().getMetricStatistics(IReporterMetric.REPORTS_PUBLISH_TIME), 0);
    }
}
