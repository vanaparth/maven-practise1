package com.apple.cds.messaging.client.impl;

/**
 * @author Toch
 */
public class SMPEventExchangeQueue
{
    private String name;

    private SMPEventExchangeQueue(String name)
    {
        this.name = name;
    }

    protected static SMPEventExchangeQueue getInstance(String name)
    {
        return new SMPEventExchangeQueue(name);
    }

    public String getName()
    {
        return name;
    }

    public boolean equals(Object o)
    {
        return (name.equals(((SMPEventExchangeQueue) o).name));
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }
}
