package com.apple.iossystems.smp.reporting.core.event;

import java.util.HashMap;
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

    public Map<String, String> getData()
    {
        return data;
    }

    public int size()
    {
        return data.size();
    }

    public void putAll(Map<String, String> map)
    {
        data.putAll(map);
    }

    @Override
    public String toString()
    {
        StringBuffer strBuf = new StringBuffer();

        Set<Map.Entry<String, String>> entrySet = data.entrySet();

        for (Map.Entry<String, String> entry : entrySet)
        {
            strBuf.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        return strBuf.toString();
    }
}