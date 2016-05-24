package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.email.service.impl.ssp.domain.SMPEmailCardData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
class CardEventRecord
{
    private final List<SMPEmailCardData> successCards = new ArrayList<>();
    private final List<SMPEmailCardData> failedCards = new ArrayList<>();

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

    List<SMPEmailCardData> getSuccessCards()
    {
        return successCards;
    }

    List<SMPEmailCardData> getFailedCards()
    {
        return failedCards;
    }

    boolean isSuccessful()
    {
        return (!successCards.isEmpty() && failedCards.isEmpty());
    }

    boolean hasPartialSuccess()
    {
        return (!successCards.isEmpty() && !failedCards.isEmpty());
    }

    boolean isFailed()
    {
        return (successCards.isEmpty() && !failedCards.isEmpty());
    }

    static CardEventRecord getCardEventRecord(ManageDeviceEvent manageDeviceEvent)
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