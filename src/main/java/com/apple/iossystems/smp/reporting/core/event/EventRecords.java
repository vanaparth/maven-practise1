package com.apple.iossystems.smp.reporting.core.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
public class EventRecords
{
    private List<EventRecord> list = new ArrayList<EventRecord>();

    private EventRecords()
    {
    }

    public static EventRecords getInstance()
    {
        return new EventRecords();
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
