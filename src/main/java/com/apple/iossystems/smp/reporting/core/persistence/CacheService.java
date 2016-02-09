package com.apple.iossystems.smp.reporting.core.persistence;

import com.apple.cds.cache.Cache;
import com.apple.iossystems.persistence.cache.StockholmCacheFactory;
import com.apple.iossystems.smp.reporting.core.concurrent.ScheduledEventTaskHandler;
import org.apache.log4j.Logger;

/**
 * @author Toch
 */
class CacheService
{
    private static final Logger LOGGER = Logger.getLogger(CacheService.class);

    private Cache cache;

    private CacheService()
    {
        initCache();
    }

    public static CacheService getInstance()
    {
        return new CacheService();
    }

    private void initCache()
    {
        cache = createCache();

        if (cache == null)
        {
            new TaskHandler();
        }
    }

    private Cache createCache()
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

    public void put(String key, String value, long timeoutInMilliseconds)
    {
        try
        {
            if ((key != null) && (value != null))
            {
                cache.setValueForKeyWithTimeout(value, key, timeoutInMilliseconds);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }
    }

    public String get(String key)
    {
        String value = null;

        try
        {
            if (key != null)
            {
                Object cacheValue = cache.valueForKey(key);

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

    public String remove(String key)
    {
        String value = null;

        try
        {
            if (key != null)
            {
                value = get(key);

                cache.removeValueForKey(key);
            }
        }
        catch (Exception e)
        {
            LOGGER.error(e.getMessage(), e);
        }

        return value;
    }

    private class TaskHandler extends ScheduledEventTaskHandler
    {
        private TaskHandler()
        {
        }

        @Override
        public void handleEvent()
        {
            if (cache != null)
            {
                shutdown();
            }
            else
            {
                LOGGER.info("Attempting to recreate cache");

                Cache c = createCache();

                if (c != null)
                {
                    cache = c;

                    shutdown();
                }
            }
        }
    }
}