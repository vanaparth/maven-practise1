package com.apple.iossystems.smp.reporting.core.messaging;

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
    public boolean equals(Object object)
    {
        if (this == object)
        {
            return true;
        }

        if (this instanceof SMPEventExchangeQueue)
        {
            return (name.equals(((SMPEventExchangeQueue) object).name));
        }

        return false;
    }

    @Override
    public int hashCode()
    {
        return name.hashCode();
    }
}
