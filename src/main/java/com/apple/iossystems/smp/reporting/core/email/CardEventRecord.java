package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.email.service.impl.ssp.domain.SMPEmailCardData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
public class CardEventRecord
{
    private List<SMPEmailCardData> successCards = new ArrayList<SMPEmailCardData>();
    private List<SMPEmailCardData> failedCards = new ArrayList<SMPEmailCardData>();

    private CardEventRecord()
    {
    }

    private void addSuccessCard(SMPEmailCardData card)
    {
        successCards.add(card);
    }

    private void addFailedCard(SMPEmailCardData card)
    {
        failedCards.add(card);
    }

    public List<SMPEmailCardData> getSuccessCards()
    {
        return successCards;
    }

    public List<SMPEmailCardData> getFailedCards()
    {
        return failedCards;
    }

    public boolean isSuccessful()
    {
        return (!successCards.isEmpty() && failedCards.isEmpty());
    }

    public boolean hasPartialSuccess()
    {
        return (!successCards.isEmpty() && !failedCards.isEmpty());
    }

    public boolean isFailed()
    {
        return (successCards.isEmpty() && !failedCards.isEmpty());
    }

    public static CardEventRecord getCardEventRecord(ManageDeviceEvent manageDeviceEvent)
    {
        List<CardEvent> cardEvents = null;

        if (manageDeviceEvent != null)
        {
            cardEvents = manageDeviceEvent.getCardEvents();
        }

        return (cardEvents != null) ? getCardEventRecord(cardEvents) : new CardEventRecord();
    }

    private static CardEventRecord getCardEventRecord(List<CardEvent> cardEvents)
    {
        CardEventRecord cardEventRecord = new CardEventRecord();

        if (cardEvents != null)
        {
            for (CardEvent cardEvent : cardEvents)
            {
                if (cardEvent.isSuccessful())
                {
                    cardEventRecord.addSuccessCard(new SMPEmailCardData(cardEvent.getCardDisplayNumber(), cardEvent.getCardDescription()));
                }
                else
                {
                    cardEventRecord.addFailedCard(new SMPEmailCardData(cardEvent.getCardDisplayNumber(), cardEvent.getCardDescription()));
                }
            }
        }

        return cardEventRecord;
    }
}