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

    private Cache getCache()
    {
        if (CACHE == null)
        {
            initCache();
        }

        return CACHE;
    }

    public void put(String key, String value, long timeout)
    {
        try
        {
            if ((key != null) && (value != null))
            {
                getCache().setValueForKeyWithTimeout(value, key, timeout);
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
                Object cacheValue = getCache().valueForKey(key);

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

                getCache().removeValueForKey(key);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e);
        }

        return value;
    }
}