package com.apple.iossystems.smp.reporting.core.persistence;

/**
 * @author Toch
 */
public class SMPEventCache
{
    private static final long DEFAULT_CACHE_TIMEOUT_MILLISECONDS = 72 * 60 * 60 * 1000;

    private CacheService cacheService = CacheService.getInstance();

    private static final SMPEventCache INSTANCE = new SMPEventCache();

    private SMPEventCache()
    {
    }

    public static SMPEventCache getInstance()
    {
        return INSTANCE;
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

    public void put(Attribute attribute, String key, String value)
    {
        cacheService.put(getCacheKey(attribute, key), value, attribute.cacheTimeout);
    }

    public String get(Attribute attribute, String key)
    {
        return cacheService.get(getCacheKey(attribute, key));
    }

    public String remove(Attribute attribute, String key)
    {
        return cacheService.remove(getCacheKey(attribute, key));
    }

    public enum Attribute
    {
        GET_OTP_RESOLUTION_METHODS("GetOtpResolutionMethods", DEFAULT_CACHE_TIMEOUT_MILLISECONDS),
        PROVISION_EVENT("ProvisionEvent", DEFAULT_CACHE_TIMEOUT_MILLISECONDS);

        private final String key;
        private final long cacheTimeout;

        Attribute(String key, long cacheTimeout)
        {
            this.key = key;
            this.cacheTimeout = cacheTimeout;
        }
    }
}