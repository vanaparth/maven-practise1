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

    public static CardEventRecord getCardEventRecord(EmailRecord emailRecord)
    {
        if ((emailRecord != null) && (emailRecord.getCards() != null))
        {
            return CardEventRecord.getCardEventRecord(emailRecord.getCards());
        }
        else
        {
            return new CardEventRecord();
        }
    }

    private static CardEventRecord getCardEventRecord(List<Card> cards)
    {
        CardEventRecord cardEventRecord = new CardEventRecord();

        if (cards != null)
        {
            for (Card card : cards)
            {
                CardEvent cardEvent = card.getCardEvent();

                if ((cardEvent == null) || (cardEvent.getEventStatus()))
                {
                    cardEventRecord.addSuccessCard(new SMPEmailCardData(EmailRecordFormat.getValidValue(card.getDisplayNumber()), EmailRecordFormat.getValidValue(card.getDescription())));
                }
                else
                {
                    cardEventRecord.addFailedCard(new SMPEmailCardData(EmailRecordFormat.getValidValue(card.getDisplayNumber()), EmailRecordFormat.getValidValue(card.getDescription())));
                }
            }
        }

        return cardEventRecord;
    }
}