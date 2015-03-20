package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.Actor;

/**
 * @author Toch
 */
public enum CardEventSource
{
    DEVICE(Actor.DEVICE, "1"),
    FMIP(Actor.FMIP, "2"),
    INTERNAL(Actor.INTERNAL, "3"),
    PNO(Actor.PNO, "4");

    private final Actor actor;
    private final String code;

    private CardEventSource(Actor actor, String code)
    {
        this.actor = actor;
        this.code = code;
    }

    private static CardEventSource getUnknownSource()
    {
        return DEVICE;
    }

    public String getCode()
    {
        return code;
    }

    public static CardEventSource getCardEventSource(Actor actor)
    {
        for (CardEventSource e : CardEventSource.values())
        {
            if (e.actor == actor)
            {
                return e;
            }
        }

        return getUnknownSource();
    }

    public static CardEventSource getCardEventSource(String code)
    {
        for (CardEventSource e : CardEventSource.values())
        {
            if (e.code.equals(code))
            {
                return e;
            }
        }

        return getUnknownSource();
    }
}