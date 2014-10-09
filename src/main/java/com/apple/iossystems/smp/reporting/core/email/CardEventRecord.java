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
        if ((emailRecord != null) && (emailRecord.getCardEvents() != null))
        {
            return getCardEventRecord(emailRecord.getCardEvents());
        }
        else
        {
            return new CardEventRecord();
        }
    }

    private static CardEventRecord getCardEventRecord(List<CardEvent> cardEvents)
    {
        CardEventRecord cardEventRecord = new CardEventRecord();

        if (cardEvents != null)
        {
            for (CardEvent cardEvent : cardEvents)
            {
                if (cardEvent.getEventStatus())
                {
                    cardEventRecord.addSuccessCard(new SMPEmailCardData(EmailRecordFormat.getValidValue(cardEvent.getCardDisplayNumber()), EmailRecordFormat.getValidValue(cardEvent.getCardDescription())));
                }
                else
                {
                    cardEventRecord.addFailedCard(new SMPEmailCardData(EmailRecordFormat.getValidValue(cardEvent.getCardDisplayNumber()), EmailRecordFormat.getValidValue(cardEvent.getCardDescription())));
                }
            }
        }

        return cardEventRecord;
    }
}