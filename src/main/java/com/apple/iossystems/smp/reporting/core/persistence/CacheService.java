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

    private final Cache cache = getCacheInstance();

    private CacheService()
    {
    }

    static CacheService getInstance()
    {
        return new CacheService();
    }

    private Cache getCacheInstance()
    {
        Cache cache = null;

        try
        {
            cache = StockholmCacheFactory.getInstance().getDefaultCache();
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return cache;
    }

    private Cache getCache()
    {
        return (cache != null) ? cache : getCacheInstance();
    }

    void put(String key, String value, long timeoutInMilliseconds)
    {
        try
        {
            if ((key != null) && (value != null))
            {
                getCache().setValueForKeyWithTimeout(value, key, timeoutInMilliseconds);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    String get(String key)
    {
        String value = null;

        try
        {
            if (key != null)
            {
                Object cacheValue = getCache().valueForKey(key);

                if (cacheValue != null)
                {
                    value = String.valueOf(cacheValue);
                }
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return value;
    }

    String remove(String key)
    {
        String value = null;

        try
        {
            if (key != null)
            {
                value = get(key);

                getCache().removeValueForKey(key);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return value;
    }
}