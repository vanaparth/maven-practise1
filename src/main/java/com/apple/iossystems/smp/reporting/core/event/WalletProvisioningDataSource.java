package com.apple.iossystems.smp.reporting.core.event;

/**
 * @author Toch
 */
public enum WalletProvisioningDataSource
{
    ATHENA(EventAttribute.ATHENA_COLOR),
    PNO(EventAttribute.PROVISION_STATUS);

    private final EventAttribute eventAttribute;

    WalletProvisioningDataSource(EventAttribute eventAttribute)
    {
        this.eventAttribute = eventAttribute;
    }

    public EventAttribute getEventAttribute()
    {
        return eventAttribute;
    }
}