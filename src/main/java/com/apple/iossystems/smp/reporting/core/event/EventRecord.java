package com.apple.iossystems.smp.reporting.core.event;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author Toch
 */
public class EventRecord
{
    private Map<String, String> data = new HashMap<String, String>();

    private EventRecord()
    {
    }

    public static EventRecord getInstance()
    {
        return new EventRecord();
    }

    public void setAttributeValue(String key, String value)
    {
        if (value != null)
        {
            data.put(key, value);
        }
    }

    public String getAttributeValue(String key)
    {
        return data.get(key);
    }

    public String removeAttribute(String key)
    {
        return data.remove(key);
    }

    public Map<String, String> getData()
    {
        return data;
    }

    public void putAll(Map<String, String> map)
    {
        data.putAll(map);
    }

    public void removeAttributesIfAbsent(Set<String> keys)
    {
        Iterator<Map.Entry<String, String>> iterator = data.entrySet().iterator();

        while (iterator.hasNext())
        {
            if (!keys.contains(iterator.next().getKey()))
            {
                iterator.remove();
            }
        }
    }

    @Override
    public String toString()
    {
        StringBuilder strBuilder = new StringBuilder();

        for (Map.Entry<String, String> entry : data.entrySet())
        {
            strBuilder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        return strBuilder.toString();
    }
}