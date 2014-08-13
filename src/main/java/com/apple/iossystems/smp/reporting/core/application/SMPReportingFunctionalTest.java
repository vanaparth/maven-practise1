package com.apple.iossystems.smp.reporting.core.application;

import com.apple.iossystems.smp.reporting.core.event.EventAttribute;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.ireporter.publish.IReporterService;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class SMPReportingFunctionalTest
{
    public static void main(String[] args)
    {
        //SMPReportingApplication.start();
        testService();
    }

    private static void testService()
    {
        for (int i = 0; i < 10; i++)
        {
            Map<String, String> data = new HashMap<String, String>();

            data.put(EventAttribute.CARD_BIN.key(), "1");
            data.put(EventAttribute.CARD_ISSUER.key(), "2");
            data.put(EventAttribute.CARD_SOURCE.key(), "3");

            data.put(EventAttribute.CARD_STATUS.key(), "4");
            data.put(EventAttribute.CARD_TYPE.key(), "5");
            data.put(EventAttribute.CONVERSATION_ID.key(), "6");

            data.put(EventAttribute.DEVICE_LANGUAGE.key(), "7");
            data.put(EventAttribute.DEVICE_LOCATION.key(), "8");
            data.put(EventAttribute.DEVICE_SERIAL_NUMBER.key(), "9");

            data.put(EventAttribute.DPAN_ID.key(), "10");
            data.put(EventAttribute.DSID.key(), "11");
            data.put(EventAttribute.CARD_EVENT.key(), "12");


            data.put(EventAttribute.FPAN_ID.key(), "13");
            data.put(EventAttribute.PNO.key(), "14");
            data.put(EventAttribute.PROVISION_STATUS.key(), "15");

            data.put(EventAttribute.SUPPORTS_IN_APP_PAYMENT.key(), "16");
            data.put(EventAttribute.TIMESTAMP.key(), "17");
            data.put(EventAttribute.USE_CASE_TYPE.key(), "18");

            data.put(EventAttribute.USER_AGENT.key(), "19");

            EventRecord record = EventRecord.getInstance(data);

            IReporterService.getInstance().postSMPEvent(record);

            Logger.getLogger(SMPReportingFunctionalTest.class).info(record);

            try
            {
                Thread.sleep(5000);
            }
            catch (Exception e)
            {
                Logger.getLogger(SMPReportingFunctionalTest.class).error(e);
            }
        }
    }
}