package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.jsonAdapter.GsonBuilderFactory;

/**
 * @author Toch
 */
public class ProvisionEmailPublishService
{
    private ProvisionEmailPublishService()
    {
    }

    public static void publishEvent(String conversationId, boolean firstProvision)
    {
        if (firstProvision)
        {
            String json = SMPEventCache.get(SMPEventCache.Attribute.PROVISION_EVENT, conversationId);

            if (json != null)
            {
                ProvisionCardEvent provisionCardEvent = GsonBuilderFactory.getInstance().fromJson(json, ProvisionCardEvent.class);

                ProvisionCardEvent updatedProvisionCardEvent = ProvisionCardEvent.getBuilder().
                        conversationId(provisionCardEvent.getConversationId()).
                        timestamp(provisionCardEvent.getTimestamp()).
                        cardHolderName(provisionCardEvent.getCardHolderName()).
                        cardHolderEmail(provisionCardEvent.getCardHolderEmail()).
                        cardDisplayNumber(provisionCardEvent.getCardDisplayNumber()).
                        deviceName(provisionCardEvent.getDeviceName()).
                        deviceType(provisionCardEvent.getDeviceType()).
                        dsid(provisionCardEvent.getDsid()).
                        locale(provisionCardEvent.getLocale()).
                        firstProvision(true).build();

                EmailPublishService.sendProvisionEventRequest(updatedProvisionCardEvent);
            }
        }
    }
}