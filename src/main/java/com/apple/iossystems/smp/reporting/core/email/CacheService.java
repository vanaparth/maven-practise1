package com.apple.iossystems.smp.reporting.core.email;

import com.apple.cds.cache.Cache;
import com.apple.iossystems.persistence.cache.StockholmCacheFactory;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
public class CacheService
{
    private static final Logger LOGGER = Logger.getLogger(CacheService.class);

    private static Cache CACHE = getCache();

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

    public static void put(String key, Object value, long timeout)
    {
        try
        {
            if (key != null)
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
                value = String.valueOf(CACHE.valueForKey(key));
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return value;
    }
}