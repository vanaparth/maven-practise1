package com.apple.iossystems.smp.reporting.core.persistence;

/**
 * @author Toch
 */
public class SMPEventCache
{
    private static final long DEFAULT_CACHE_TIMEOUT_MILLISECONDS = 72 * 60 * 60 * 1000;

    private static final CacheService CACHE_SERVICE = CacheService.getInstance();

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
        CACHE_SERVICE.put(getCacheKey(attribute, key), value, attribute.cacheTimeout);
    }

    public static String get(Attribute attribute, String key)
    {
        return CACHE_SERVICE.get(getCacheKey(attribute, key));
    }

    public static String remove(Attribute attribute, String key)
    {
        return CACHE_SERVICE.remove(getCacheKey(attribute, key));
    }

    public enum Attribute
    {
        GET_OTP_RESOLUTION_METHODS("GetOtpResolutionMethods", DEFAULT_CACHE_TIMEOUT_MILLISECONDS),
        PROVISION_EVENT("ProvisionEvent", DEFAULT_CACHE_TIMEOUT_MILLISECONDS);

        private final String key;
        private final long cacheTimeout;

        private Attribute(String key, long cacheTimeout)
        {
            this.key = key;
            this.cacheTimeout = cacheTimeout;
        }
    }
}