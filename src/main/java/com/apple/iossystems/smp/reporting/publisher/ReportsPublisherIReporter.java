package com.apple.iossystems.smp.reporting.publisher;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.reporting.config.IReporterConfiguration;
import com.apple.iossystems.smp.reporting.data.IReporterDataReport;
import com.apple.iossystems.smp.reporting.data.SMPDataReport;
import com.apple.iossystems.smp.reporting.data.SMPDataReportsBuffer;
import com.apple.iossystems.smp.reporting.httpclient.HTTPClientReporterSMP;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Toch
 */
public class ReportsPublisherIReporter implements ReportsPublisher
{
    private static final Logger LOGGER = Logger.getLogger(ReportsPublisherIReporter.class);

    private IReporterConfiguration configuration = IReporterConfiguration.Builder.getInstance().build();

    private SMPDataReportsBuffer reportsBuffer = SMPDataReportsBuffer.getInstance();

    private ReportingAudit reportingAudit = ReportingAudit.getInstance();

    private HTTPClientReporterSMP httpClient = HTTPClientReporterSMP.getInstance();

    private ReportsPublisherIReporter()
    {
        init();
    }

    private void init()
    {
        try
        {
            reloadConfiguration();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    public static ReportsPublisherIReporter getInstance()
    {
        return new ReportsPublisherIReporter();
    }

    @Override
    public void postReport(SMPDataReport report)
    {
        try
        {
            reportsBuffer.add(report);

            if (timeToSendReports())
            {
                if (reloadConfigurationWaitTimeExpired())
                {
                    reloadConfiguration();
                }

                sendReportsToClients();
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }


    private boolean timeToSendReports()
    {
        return (isPublishEnabled() && ((batchFull() || (publishWaitTimeExpired() && reportsBuffer.notEmpty()))));
    }

    private boolean isPublishEnabled()
    {
        return configuration.isPublishEnabled();
    }

    private boolean batchFull()
    {
        return (reportsBuffer.size() >= configuration.getMaxBatchSize());
    }

    private boolean publishWaitTimeExpired()
    {
        return timeExpired(reportingAudit.getLastReportSentTimestamp(), configuration.getPublishFrequency());
    }

    private boolean reloadConfigurationWaitTimeExpired()
    {
        return timeExpired(reportingAudit.getLastConfigurationLoadTimestamp(), configuration.getConfigurationReloadFrequency());
    }

    private boolean timeExpired(long time, long limit)
    {
        return ((System.currentTimeMillis() - time) >= limit);
    }


    private void reloadConfiguration() throws Exception
    {
        Map<String, Object> request = new HashMap<String, Object>();

        request.put(HTTPClientReporterSMP.HTTP_METHOD_KEY, "GET");
        request.put(HTTPClientReporterSMP.URL_KEY, IReporterConfiguration.getConfigurationURL());

        StockholmHTTPResponse response = httpClient.request(request);
        String content = response.getContent();

        if (response.isSuccessful() && content != null)
        {
            configuration = IReporterConfiguration.Builder.fromJSON(content);
            reportingAudit.setConfigurationLoadTimestampCurrentTime();
        }
    }

    private void sendReportsToClients() throws Exception
    {
        httpClient.request(getPublishHTTPRequest(reportsBuffer.getAndFlushBuffers()));
        reportingAudit.setReportSentTimestampCurrentTime();
    }

    private Map<String, Object> getPublishHTTPRequest(List<SMPDataReport> reports)
    {
        Map<String, Object> request = new HashMap<String, Object>();

        request.put(HTTPClientReporterSMP.HTTP_METHOD_KEY, "POST");
        request.put(HTTPClientReporterSMP.URL_KEY, configuration.getPublishURL());
        request.put(HTTPClientReporterSMP.CONTENT_TYPE_KEY, configuration.getContentType());
        request.put(HTTPClientReporterSMP.HEADERS_KEY, configuration.getHeadersForReports());
        request.put(HTTPClientReporterSMP.DATA_KEY, IReporterDataReport.toJSON(reports));

        return request;
    }
}
