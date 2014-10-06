package com.apple.iossystems.smp.reporting.core.email;

/**
 * @author Toch
 */
public class NetworkCheckCardEventCache
{
    private static final long CACHE_TIMEOUT = 15 * 60 * 1000;

    private NetworkCheckCardEventCache()
    {
    }

    private static String getCacheKey(Attribute attribute, String conversationId)
    {
        String key = null;

        if (conversationId != null)
        {
            key = "smp_reporting_ncc_event_" + attribute.key + "_" + conversationId;
        }

        return key;
    }

    public static void put(Attribute attribute, String conversationId, String value)
    {
        CacheService.put(getCacheKey(attribute, conversationId), value, CACHE_TIMEOUT);
    }

    public static String get(Attribute attribute, String conversationId)
    {
        return CacheService.get(getCacheKey(attribute, conversationId));
    }

    public enum Attribute
    {
        DEVICE_TYPE("device_type"),
        EMAIL("email"),
        LOCALE("locale");

        private final String key;

        private Attribute(String key)
        {
            this.key = key;
        }
    }
}