package com.apple.iossystems.smp.reporting.core.email;

/**
 * @author Toch
 */
public class SMPEventCache
{
    private static final long CACHE_TIMEOUT = 30 * 60 * 1000;

    private SMPEventCache()
    {
    }

    private static String getCacheKey(Attribute attribute, String conversationId)
    {
        String key = null;

        if (conversationId != null)
        {
            key = "SMPReporting_" + attribute.key + "_" + conversationId;
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
        PROVISION_EVENT("ProvisionEvent");

        private final String key;

        private Attribute(String key)
        {
            this.key = key;
        }
    }
}