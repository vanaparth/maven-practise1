package com.apple.cds.messaging.client.impl;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Toch
 */
class SMPEventExchange
{
    private String name;

    private boolean active;

    private Set<SMPEventExchangeQueue> queues = new HashSet<SMPEventExchangeQueue>();

    private SMPEventExchange(String name)
    {
        this.name = name;
    }

    static SMPEventExchange getInstance(String name)
    {
        return new SMPEventExchange(name);
    }

    String getName()
    {
        return name;
    }

    void addQueue(SMPEventExchangeQueue queue)
    {
        queues.add(queue);
    }

    Iterator<SMPEventExchangeQueue> iterator()
    {
        return queues.iterator();
    }

    boolean isActive()
    {
        return active;
    }

    void setActive(boolean value)
    {
        active = value;
    }
}
