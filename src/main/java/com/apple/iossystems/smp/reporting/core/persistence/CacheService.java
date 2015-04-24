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

    public void put(String key, String value, long timeoutInMilliseconds)
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
        }
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