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

    private static final Cache CACHE = getCache();

    private CacheService()
    {
    }

    private static Cache getCache()
    {
        Cache cache = null;

        try
        {
            cache = StockholmCacheFactory.getInstance().getDefaultCache();
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return cache;
    }

    public static void put(String key, String value, long timeout)
    {
        try
        {
            if ((key != null) && (value != null))
            {
                CACHE.setValueForKeyWithTimeout(value, key, timeout);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }
    }

    public static String get(String key)
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

    public static String remove(String key)
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