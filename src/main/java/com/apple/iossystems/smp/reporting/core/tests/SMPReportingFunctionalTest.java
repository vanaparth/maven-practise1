package com.apple.iossystems.smp.reporting.core.tests;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.smp.StockholmHTTPResponse;
import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.http.HttpRequest;
import com.apple.iossystems.smp.reporting.core.http.SMPHttpClient;
import com.apple.iossystems.smp.reporting.core.messaging.SMPReportingService;
import com.apple.iossystems.smp.reporting.ireporter.configuration.IReporterConfiguration;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterJsonBuilder;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Toch
 */
public class SMPReportingFunctionalTest
{
    private static final Logger LOGGER = Logger.getLogger(SMPReportingFunctionalTest.class);

    public static void main(String[] args)
    {
        new SMPReportingFunctionalTest().integrationTests();
    }

    private void integrationTests()
    {
        setSystemProperties();
        initPropertyManager();

        testICloudProdConfig();
        testICloudProdPublish();

        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
        test9();
        test10();
    }

    private void setSystemProperties()
    {
        System.setProperty("enable.hubble", "true");
    }

    private void initPropertyManager()
    {
        try
        {
            PropertyManager propertyManager = PropertyManager.getInstance();
            propertyManager.initializeProperties(true);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void test1()
    {
        postRecords(getRecords(1));
    }

    private void test2()
    {
        postRecords(getRecords(1));
    }

    private void test3()
    {
        postRecords(getRecords(5));
        sleep(60 * 1000);

        postRecords(getRecords(10));
        sleep(60 * 1000);

        postRecords(getRecords(20));
    }

    private void test4()
    {
        postRecords(getRecords(100));
    }

    private void test5()
    {
        // Test 3
        postRecords(getRecords(5));
        sleep(60 * 1000);

        postRecords(getRecords(10));
        sleep(60 * 1000);

        postRecords(getRecords(20));
        sleep(5 * 60 * 1000);

        // Configuration changed
        postRecords(getRecords(200));
    }

    private void test6()
    {
        IReporterConfiguration configuration = IReporterConfiguration.getDefaultConfiguration(IReporterConfiguration.Type.REPORTS);

        EventRecords records = getRecords(1);

        HttpRequest httpRequest = HttpRequest.getInstance(configuration.getPublishURL(), "GET", null, configuration.getContentType(), IReporterJsonBuilder.toJson(records.getList()), configuration.getRequestHeaders());

        try
        {
            SMPHttpClient.getInstance().request(httpRequest);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void test7()
    {
        try
        {
            SMPHttpClient.getInstance().request(HttpRequest.getInstance(IReporterConfiguration.Type.REPORTS.getConfigurationURL(), "POST", null, null, "", null));
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void test8()
    {
        testMissingHeader("X-LoadText");
    }

    private void test9()
    {
        testMissingHeader("Content-Type");
    }

    private void test10()
    {
        try
        {
            SMPHttpClient.getInstance().request(HttpRequest.getInstance(IReporterConfiguration.Type.REPORTS.getConfigurationURL(), "GET", null, null, null, null));
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void testMissingHeader(String headerKey)
    {
        IReporterConfiguration configuration = IReporterConfiguration.getDefaultConfiguration(IReporterConfiguration.Type.REPORTS);
        Map<String, String> headers = configuration.getRequestHeaders();

        headers.remove(headerKey);

        EventRecords records = getRecords(1);

        HttpRequest httpRequest = HttpRequest.getInstance(configuration.getPublishURL(), "POST", null, configuration.getContentType(), IReporterJsonBuilder.toJson(records.getList()), headers);

        try
        {
            SMPHttpClient.getInstance().request(httpRequest);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void sleep(long millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void postRecords(EventRecords records)
    {
        SMPReportingService smpReportingService = SMPReportingService.getInstance();

        postRecords(smpReportingService, records);
    }

    private void postRecords(SMPReportingService smpReportingService, EventRecords records)
    {
        List<EventRecord> list = records.getList();

        for (EventRecord record : list)
        {
            smpReportingService.postSMPEvent(record);
        }
    }

    private EventRecords getRecords(int count)
    {
        EventRecords records = EventRecords.getInstance();

        for (int i = 0; i < count; i++)
        {
            records.add(getRecord());
        }

        return records;
    }

    private void testICloudProdConfig()
    {
        String url = "https://icloud4-e3.icloud.com";

        HttpRequest httpRequest = HttpRequest.getInstance(url + "/e3/rest/1/config/stockholm", "GET", null, null, null, null);

        try
        {
            StockholmHTTPResponse response = SMPHttpClient.getInstance().request(httpRequest);

            System.out.println(response.getContent());
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void testICloudProdPublish()
    {
        IReporterConfiguration configuration = IReporterConfiguration.getDefaultConfiguration(IReporterConfiguration.Type.REPORTS);
        Map<String, String> headers = configuration.getRequestHeaders();

        EventRecords records = getRecords(10);

        HttpRequest httpRequest = HttpRequest.getInstance("https://mr-e3sh.icloud.com/e3/rest/1/stockholm", "POST", null, configuration.getContentType(), IReporterJsonBuilder.toJson(records.getList()), headers);

        try
        {
            SMPHttpClient.getInstance().request(httpRequest);
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private EventRecord getRecord()
    {
        Map<String, String> data = new HashMap<>();

        data.put(EventAttribute.ATHENA_COLOR.key(), "1");
        data.put(EventAttribute.CARD_BIN.key(), "2");
        data.put(EventAttribute.CARD_ISSUER.key(), "3");

        data.put(EventAttribute.CARD_SOURCE.key(), "4");
        data.put(EventAttribute.CARD_STATUS.key(), "5");
        data.put(EventAttribute.CARD_TYPE.key(), "6");

        data.put(EventAttribute.CONVERSATION_ID.key(), "7");
        data.put(EventAttribute.DEVICE_LANGUAGE.key(), "8");
        data.put(EventAttribute.DEVICE_LOCATION.key(), "9");

        data.put(EventAttribute.DEVICE_SERIAL_NUMBER.key(), "10");
        data.put(EventAttribute.DPAN_ID.key(), "11");
        data.put(EventAttribute.DSID.key(), "12");
        data.put(EventAttribute.EVENT.key(), "13");

        data.put(EventAttribute.FPAN_ID.key(), "14");
        data.put(EventAttribute.PNO.key(), "15");
        data.put(EventAttribute.PROVISION_STATUS.key(), "16");

        data.put(EventAttribute.PROVISION_RESPONSE.key(), "17");
        data.put(EventAttribute.TIMESTAMP.key(), "18");
        data.put(EventAttribute.USE_CASE_TYPE.key(), "19");

        data.put(EventAttribute.USER_AGENT.key(), "20");

        EventRecord record = EventRecord.getInstance();

        record.putAll(data);

        return record;
    }
}