package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.logging.LogServiceConfigurator;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfigurationManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Toch
 */
public class SMPEventLogServiceConfigurator implements LogServiceConfigurator
{
    private static final Map<String, String> PROPERTY_MAP = getPropertyMap();

    private static Map<String, String> getPropertyMap()
    {
        Map<String, String> map = new HashMap<>();

        map.put("rabbit.host", ApplicationConfigurationManager.getKeystoneRabbitHost());
        map.put("rabbit.port", ApplicationConfigurationManager.getKeystoneRabbitPort());
        map.put("rabbit.user", ApplicationConfigurationManager.getKeystoneRabbitUser());
        map.put("rabbit.pass", ApplicationConfigurationManager.getKeystoneRabbitPassword());
        map.put("rabbit.vhost", ApplicationConfigurationManager.getKeystoneRabbitVirtualHost());
        map.put("rabbit.exchange", ApplicationConfigurationManager.getSMPEventsExchangeName());

        map.put("logservice.owner", ApplicationConfigurationManager.getLogServiceOwner());
        map.put("logservice.path", ApplicationConfigurationManager.getLogServicePath());
        map.put("logservice.category", ApplicationConfigurationManager.getLogServiceCategory());
        map.put("logservice.localStoreName", ApplicationConfigurationManager.getLogServiceStore());
        map.put("logservice.eventFinderClassName", ApplicationConfigurationManager.getLogServiceEventFinderClass());

        return map;
    }

    @Override
    public String valueForKeyWithDefault(String key, String defaultValue)
    {
        String value = PROPERTY_MAP.get(key);

        return ((value != null) ? value : PropertyManager.getInstance().valueForKeyWithDefault(key, defaultValue));
    }
}