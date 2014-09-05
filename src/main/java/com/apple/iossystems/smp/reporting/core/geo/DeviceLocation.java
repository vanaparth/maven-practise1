package com.apple.iossystems.smp.reporting.core.geo;

/**
 * @author Toch
 */
public class DeviceLocation
{
    private DeviceLocation()
    {
    }

    public static Coordinates getCoordinates(String value)
    {
        int index = value.indexOf('/');

        if (index >= 0)
        {
            float lat = Float.valueOf(value.substring(0, index));
            float lon = Float.valueOf(value.substring(index + 1, value.length()));

            return Coordinates.getInstance(lon, lat);
        }
        else
        {
            return Coordinates.getDefaultCoordinates();
        }
    }

    public static Coordinates truncateCoordinates(Coordinates coordinates)
    {
        return Coordinates.getInstance((int) coordinates.getLongitude(), (int) coordinates.getLatitude());
    }
}