package com.apple.iossystems.smp.reporting.core.email;

import com.apple.iossystems.smp.domain.Actor;

/**
 * @author Toch
 */
public enum ManageDeviceEventSource
{
    DEVICE(Actor.DEVICE, "1"),
    FMIP(Actor.FMIP, "2"),
    INTERNAL(Actor.INTERNAL, "3"),
    PNO(Actor.PNO, "4");

    private final Actor actor;
    private final String code;

    private ManageDeviceEventSource(Actor actor, String code)
    {
        this.actor = actor;
        this.code = code;
    }

    private static ManageDeviceEventSource getDefaultSource()
    {
        return DEVICE;
    }

    public String getCode()
    {
        return code;
    }

    public static ManageDeviceEventSource fromActor(Actor actor)
    {
        for (ManageDeviceEventSource e : values())
        {
            if (e.actor.equals(actor))
            {
                return e;
            }
        }

        return getDefaultSource();
    }

    public static ManageDeviceEventSource fromCode(String code)
    {
        for (ManageDeviceEventSource e : values())
        {
            if (e.code.equals(code))
            {
                return e;
            }
        }

        return getDefaultSource();
    }
}