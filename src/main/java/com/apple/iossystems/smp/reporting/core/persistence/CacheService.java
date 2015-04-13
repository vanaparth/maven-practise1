package com.apple.iossystems.smp.reporting.core.persistence;

import com.apple.cds.cache.Cache;
import com.apple.iossystems.persistence.cache.StockholmCacheFactory;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class CacheService
{
    private static final Logger LOGGER = Logger.getLogger(CacheService.class);

    private static final CacheService INSTANCE = new CacheService();

    private Cache CACHE;

    private CacheService()
    {
        initCache();
    }

    public static CacheService getInstance()
    {
        return INSTANCE;
    }

    private void initCache()
    {
        try
        {
            CACHE = StockholmCacheFactory.getInstance().getDefaultCache();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    private void resetCache()
    {
        if (CACHE == null)
        {
            initCache();
        }
    }

    private void put(String key, String value, long timeoutInMilliseconds, boolean retryOnError)
    {
        try
        {
            if ((key != null) && (value != null))
            {
                CACHE.setValueForKeyWithTimeout(value, key, timeoutInMilliseconds);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);

            if (retryOnError)
            {
                resetCache();
                put(key, value, timeoutInMilliseconds, false);
            }
        }
    }

    public void put(String key, String value, long timeoutInMilliseconds)
    {
        put(key, value, timeoutInMilliseconds, true);
    }

    public String get(String key)
    {
        String value = null;

        try
        {
            if (key != null)
            {
                Object cacheValue = CACHE.valueForKey(key);

                if (cacheValue != null)
                {
                    value = String.valueOf(cacheValue);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return value;
    }

    public String remove(String key)
    {
        String value = null;

        try
        {
            if (key != null)
            {
                value = get(key);

                CACHE.removeValueForKey(key);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return value;
    }
}