package com.apple.iossystems.smp.reporting.core.email;

/**
 * @author Toch
 */
public class SMPEventCache
{
    private static final long CACHE_TIMEOUT = 72 * 60 * 60 * 1000;

    private SMPEventCache()
    {
    }

    private static String getCacheKey(Attribute attribute, String key)
    {
        String cacheKey = null;

        if (key != null)
        {
            cacheKey = "SMPReporting_" + attribute.key + "_" + key;
        }

        return cacheKey;
    }

    public static void put(Attribute attribute, String key, String value)
    {
        CacheService.put(getCacheKey(attribute, key), value, CACHE_TIMEOUT);
    }

    public static String get(Attribute attribute, String key)
    {
        return CacheService.get(getCacheKey(attribute, key));
    }

    public static String remove(Attribute attribute, String key)
    {
        return CacheService.remove(getCacheKey(attribute, key));
    }

    public enum Attribute
    {
        GET_OTP_RESOLUTION_METHODS("GetOtpResolutionMethods"),
        PROVISION_EVENT("ProvisionEvent");

        private final String key;

        private Attribute(String key)
        {
            this.key = key;
        }
    }
}