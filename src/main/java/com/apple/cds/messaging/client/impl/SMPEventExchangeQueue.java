package com.apple.cds.messaging.client.impl;

/**
 * @author Toch
 */
class SMPEventExchangeQueue
{
    private String name;

    private boolean active;

    private SMPEventExchangeQueue(String name)
    {
        this.name = name;
    }

    static SMPEventExchangeQueue getInstance(String name)
    {
        return new SMPEventExchangeQueue(name);
    }

    String getName()
    {
        return name;
    }

    boolean isActive()
    {
        return active;
    }

    void setActive(boolean value)
    {
        active = value;
    }

    @Override
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
