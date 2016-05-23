package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Toch
 */
class EmailConfiguration
{
    private EmailConfiguration()
    {
    }

    static Map<String, String> getEmailLocaleMap()
    {
        Map<String, String> map = new HashMap<>();

        List<String> list = ApplicationConfiguration.getEmailLocalesMapping();

        for (String str : list)
        {
            String[] array = StringUtils.split(str, ':');

            if ((array != null) && (array.length == 2))
            {
                String mappedLocale = array[0];
                String values = array[1];

                if (StringUtils.isNotBlank(mappedLocale) && StringUtils.isNotBlank(values))
                {
                    array = StringUtils.split(values, ';');

                    if (array != null)
                    {
                        for (String locale : array)
                        {
                            if (StringUtils.isNotBlank(locale))
                            {
                                map.put(locale, mappedLocale);
                            }
                        }
                    }
                }
            }
        }

        return map;
    }
}