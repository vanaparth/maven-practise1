package com.apple.iossystems.smp.reporting.core.tests;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.smp.reporting.core.email.CardEventRecord;
import com.apple.iossystems.smp.reporting.core.email.ManageDeviceEvent;
import com.apple.iossystems.smp.reporting.core.email.ProvisionCardEvent;
import com.apple.iossystems.smp.reporting.core.event.EventRecord;
import com.apple.iossystems.smp.reporting.core.event.EventRecords;
import com.apple.iossystems.smp.reporting.core.eventhandler.EventListener;
import com.apple.iossystems.smp.reporting.core.logging.EmailEventLogger;
import com.apple.iossystems.smp.reporting.core.logging.SMPEventLogger;

/**
 * @author Toch
 */
public class TestEventListener implements EventListener
{
    private SMPEventLogger smpEventLogger = new SMPEventLogger(PropertyManager.getInstance().getBooleanValueForKeyWithDefault("smp.reporting.test.event.log", false));
    private EmailEventLogger emailEventLogger = new EmailEventLogger();

    public TestEventListener()
    {
    }

    @Override
    public void handleEvent(EventRecords records)
    {
        smpEventLogger.log(records);
    }

    @Override
    public void handleEvent(ProvisionCardEvent provisionCardEvent)
    {
        emailEventLogger.log(provisionCardEvent);
    }

    @Override
    public void handleEvent(EventRecord record, ManageDeviceEvent manageDeviceEvent, CardEventRecord cardEventRecord)
    {
        emailEventLogger.log(record, manageDeviceEvent, cardEventRecord);
    }
}