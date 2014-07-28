package com.apple.iossystems.smp.reporting.core.event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Toch
 */
public class EventRecordList
{
    private List<EventRecord> list = new ArrayList<EventRecord>();

    private EventRecordList()
    {
    }

    public static EventRecordList getInstance()
    {
        return new EventRecordList();
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
