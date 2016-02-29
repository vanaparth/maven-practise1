package com.apple.iossystems.smp.reporting.core.event;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Toch
 */
public class EventRecords
{
    private static final EventRecords EMPTY = getEmptyRecords();

    private List<EventRecord> list = new ArrayList<>();

    private EventRecords()
    {
    }

    public static EventRecords getInstance()
    {
        return new EventRecords();
    }

    private static EventRecords getEmptyRecords()
    {
        EventRecords records = new EventRecords();

        records.list = Collections.emptyList();

        return records;
    }

    public static EventRecords getEmpty()
    {
        return EMPTY;
    }

    public void add(EventRecord e)
    {
        list.add(e);
    }

    public List<EventRecord> getList()
    {
        return list;
    }

    public int size()
    {
        return list.size();
    }

    public EventRecords getCopy()
    {
        EventRecords copy = new EventRecords();

        for (EventRecord record : list)
        {
            copy.add(record.getCopy());
        }

        return copy;
    }
}