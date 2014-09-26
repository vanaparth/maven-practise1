package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.email.service.impl.ssp.domain.SMPEmailCardData;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
class CardEventRecord
{
    private List<SMPEmailCardData> successCards = new ArrayList<SMPEmailCardData>();
    private List<SMPEmailCardData> failedCards = new ArrayList<SMPEmailCardData>();

    private CardEventRecord()
    {
    }

    public static CardEventRecord getInstance()
    {
        return new CardEventRecord();
    }

    public List<SMPEmailCardData> getSuccessCards()
    {
        return successCards;
    }

    public List<SMPEmailCardData> getFailedCards()
    {
        return failedCards;
    }

    public void addSuccessCard(SMPEmailCardData card)
    {
        successCards.add(card);
    }

    public void addFailedCard(SMPEmailCardData card)
    {
        failedCards.add(card);
    }

    public boolean isSuccessful()
    {
        return (!successCards.isEmpty() && failedCards.isEmpty());
    }

    public boolean hasPartialSuccess()
    {
        return (!failedCards.isEmpty());
    }

    public static CardEventRecord getCardEventRecord(List<Card> cards)
    {
        CardEventRecord cardEventRecord = new CardEventRecord();

        for (Card card : cards)
        {
            if (card.getCardEvent().getStatus())
            {
                cardEventRecord.addSuccessCard(new SMPEmailCardData(card.getDisplayNumber(), card.getDescription()));
            }
            else
            {
                cardEventRecord.addFailedCard(new SMPEmailCardData(card.getDisplayNumber(), card.getDescription()));
            }
        }

        return cardEventRecord;
    }
}