package com.apple.iossystems.smp.reporting.core.util;

import com.apple.iossystems.logging.Pair;

import java.util.Map;

/**
 * @author Toch
 */
public class MapToPair
{
    private MapToPair()
    {
    }

    public static Pair[] toPairs(Map<String, String> data)
    {
        int i = 0;
        Pair[] pairs = new Pair[data.size()];

        for (Map.Entry<String, String> entry : data.entrySet())
        {
            pairs[i++] = new Pair(entry.getKey(), entry.getValue());
        }

        return pairs;
    }
}