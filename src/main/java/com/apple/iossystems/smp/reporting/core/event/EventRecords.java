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

    private List<EventRecord> list = new ArrayList<EventRecord>();

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

    public boolean isEmpty()
    {
        return list.isEmpty();
    }
}
