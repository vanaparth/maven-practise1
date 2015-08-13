package com.apple.iossystems.smp.reporting.core.messaging;

import com.apple.cds.keystone.config.PropertyManager;
import com.apple.iossystems.logging.LogServiceConfigurator;
import com.apple.iossystems.smp.reporting.core.configuration.ApplicationConfiguration;

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

        map.put("rabbit.host", ApplicationConfiguration.getKeystoneRabbitHost());
        map.put("rabbit.port", ApplicationConfiguration.getKeystoneRabbitPort());
        map.put("rabbit.user", ApplicationConfiguration.getKeystoneRabbitUser());
        map.put("rabbit.pass", ApplicationConfiguration.getKeystoneRabbitPassword());
        map.put("rabbit.vhost", ApplicationConfiguration.getKeystoneRabbitVirtualHost());
        map.put("rabbit.exchange", ApplicationConfiguration.getSMPEventsExchangeName());

        map.put("logservice.owner", ApplicationConfiguration.getLogServiceOwner());
        map.put("logservice.path", ApplicationConfiguration.getLogServicePath());
        map.put("logservice.category", ApplicationConfiguration.getLogServiceCategory());
        map.put("logservice.localStoreName", ApplicationConfiguration.getLogServiceStore());
        map.put("loggingservice.bdb.batch.size", String.valueOf(ApplicationConfiguration.getLogServiceBdbBatchSize()));
        map.put("loggingservice.bdb.service.interval.ms", String.valueOf(ApplicationConfiguration.getLogServiceBdbInterval()));

        return map;
    }

    @Override
    public String valueForKeyWithDefault(String key, String defaultValue)
    {
        String value = PROPERTY_MAP.get(key);

        return (value != null) ? value : PropertyManager.getInstance().valueForKeyWithDefault(key, defaultValue);
    }
}