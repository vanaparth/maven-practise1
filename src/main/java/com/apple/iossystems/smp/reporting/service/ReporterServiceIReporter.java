package com.apple.iossystems.smp.reporting.service;

import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.reporting.config.BaseConfiguration;
import com.apple.iossystems.smp.reporting.config.IReporterConfiguration;
import com.apple.iossystems.smp.reporting.data.IReporterDataReport;
import com.apple.iossystems.smp.reporting.data.SMPDataReport;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Toch
 */
public class ReporterServiceIReporter implements ReporterService
{
    private static final Logger LOGGER = Logger.getLogger(ReporterServiceIReporter.class);
    /**
     * Report buffer
     */
    private List<SMPDataReport> buffer = new ArrayList<SMPDataReport>();
    /**
     * Configuration for sending data
     * Need a default configuration to start with for reloading in the constructor
     */
    private IReporterConfiguration configuration = IReporterConfiguration.Builder.newInstance().build();
    /**
     * Client for sending data
     */
    private HTTPClientSMPReporter httpClient = HTTPClientIReporter.newInstance();
    /**
     * Last time report was sent
     */
    private long lastReportSentTimestamp = 0;
    /**
     * Last time configuration was loaded
     */
    private long lastConfigurationLoadTimestamp = 0;

    private ReporterServiceIReporter() throws Exception
    {
        reloadConfiguration();
    }

    public static ReporterService newInstance() throws Exception
    {
        return new ReporterServiceIReporter();
    }

    // A better way to do this without synchronizing. Assuming multiple event consumer threads can call this
    @Override
    public synchronized void postReport(SMPDataReport report)
    {
        addReportToBuffer(report);

        if (doSendReports())
        {
            if (doReloadConfiguration())
            {
                reloadConfiguration();
            }

            sendReports();
        }
    }


    private void sendReports()
    {
        List<SMPDataReport> tempBuffer = clearBufferAndGet();

        // send the data
        sendRequest(generateRequest(tempBuffer));

        // Clear the temp buffer
        tempBuffer.clear();

        // reset
        updateReportSentTimestamp();
    }

    private void addReportToBuffer(SMPDataReport report)
    {
        buffer.add(report);
    }

    private List<SMPDataReport> clearBufferAndGet()
    {
        List<SMPDataReport> tempBuffer = buffer;
        buffer = new ArrayList<SMPDataReport>();

        return tempBuffer;
    }

    private void updateReportSentTimestamp()
    {
        lastReportSentTimestamp = System.currentTimeMillis();
    }

    private void updateConfigurationLoadTimestamp()
    {
        lastConfigurationLoadTimestamp = System.currentTimeMillis();
    }

    private boolean doSendReports()
    {
        return (isPublishEnabled() && ((batchFull() || (publishWaitTimeExpired() && batchNotEmpty()))));
    }

    private boolean isPublishEnabled()
    {
        return configuration.isPublishEnabled();
    }

    private boolean batchFull()
    {
        return (buffer.size() >= configuration.getMaxBatchSize());
    }

    private boolean batchNotEmpty()
    {
        return (buffer.isEmpty());
    }

    private boolean publishWaitTimeExpired()
    {
        return ((System.currentTimeMillis() - lastReportSentTimestamp) >= configuration.getPublishFrequency());
    }

    private boolean doReloadConfiguration()
    {
        return ((System.currentTimeMillis() - lastConfigurationLoadTimestamp) >= configuration.getConfigurationReloadFrequency());
    }

    private Map<String, Object> generateRequest(List<SMPDataReport> reports)
    {
        Map<String, Object> request = new HashMap<String, Object>();

        request.put(HTTPClientSMPReporter.HTTP_METHOD_KEY, "POST");
        request.put(HTTPClientSMPReporter.URL_KEY, configuration.getPublishURL());
        request.put(HTTPClientSMPReporter.CONTENT_TYPE_KEY, configuration.getContentType());
        request.put(HTTPClientSMPReporter.HEADERS_KEY, configuration.getHeadersForReports());
        request.put(HTTPClientSMPReporter.DATA_KEY, IReporterDataReport.toJSON(reports));

        return request;
    }

    private StockholmHTTPResponse sendRequest(Map<String, Object> request)
    {
        StockholmHTTPResponse response = null;

        try
        {
            response = httpClient.request(request);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return response;
    }

    private void reloadConfiguration()
    {
        Map<String, Object> request = new HashMap<String, Object>();

        request.put(HTTPClientSMPReporter.HTTP_METHOD_KEY, "GET");
        request.put(HTTPClientSMPReporter.URL_KEY, IReporterConfiguration.getConfigurationURL());

        StockholmHTTPResponse response = sendRequest(request);
        String content = null;

        try
        {
            content = response.getContent();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        if (response.isSuccessful() && content != null)
        {
            configuration = IReporterConfiguration.Builder.fromJSON(content);
            updateConfigurationLoadTimestamp();
        }
    }
}
