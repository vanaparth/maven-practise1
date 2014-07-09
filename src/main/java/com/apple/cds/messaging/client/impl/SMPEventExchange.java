package com.apple.cds.messaging.client.impl;

import java.util.HashSet;;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Toch
 */
public class SMPEventExchange
{
    private String name;

    private Set<SMPEventExchangeQueue> queues = new HashSet<SMPEventExchangeQueue>();

    private SMPEventExchange(String name)
    {
        this.name = name;
    }

    public static SMPEventExchange newInstance(String name)
    {
        return new SMPEventExchange(name);
    }

    public String getName()
    {
        return name;
    }

    public void register(SMPEventExchangeQueue queue)
    {
        queues.add(queue);
    }

    public Iterator<SMPEventExchangeQueue> iterator()
    {
        return queues.iterator();
    }
}
