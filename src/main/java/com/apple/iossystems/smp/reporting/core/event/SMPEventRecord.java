package com.apple.iossystems.smp.reporting.core.event;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Toch
 */
public class SMPEventRecord implements EventRecord
{
    private Map<SMPEventAttribute, String> fields = new HashMap<SMPEventAttribute, String>();

    private SMPEventRecord(Builder builder)
    {
        fields.putAll(builder.fields);
    }

    public Map<SMPEventAttribute, String> getFields()
    {
        return fields;
    }

    @Override
    public String toString()
    {
        StringBuffer strBuf = new StringBuffer();

        Set<Map.Entry<SMPEventAttribute, String>> entrySet = fields.entrySet();

        for (Map.Entry<SMPEventAttribute, String> entry : entrySet)
        {
            strBuf.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }

        return strBuf.toString();
    }

    public static class Builder
    {
        private Map<SMPEventAttribute, String> fields = new HashMap<SMPEventAttribute, String>();

        private Builder()
        {
        }

        public static Builder getInstance()
        {
            return new Builder();
        }

        public Builder set(SMPEventAttribute key, String value)
        {
            if (value != null)
            {
                fields.put(key, value);
            }

            return this;
        }

        public SMPEventRecord build()
        {
            return new SMPEventRecord(this);
        }
    }
}
