package com.apple.iossystems.smp.reporting.core.geo;

/**
 * @author Toch
 */
public class Coordinates
{
    private static final Coordinates DEFAULT_COORDINATES = new Coordinates(0, 0);

    private final float longitude;
    private final float latitude;

    private Coordinates(float longitude, float latitude)
    {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getReverseString()
    {
        return latitude + "," + longitude;
    }

    public Coordinates truncateToInteger()
    {
        return new Coordinates((int) longitude, (int) latitude);
    }

    public static Coordinates getCoordinates(String value)
    {
        int index = value.indexOf('/');

        if (index >= 0)
        {
            float lat = Float.parseFloat(value.substring(0, index));
            float lon = Float.parseFloat(value.substring(index + 1, value.length()));

            return new Coordinates(lon, lat);
        }
        else
        {
            return DEFAULT_COORDINATES;
        }
    }

    @Override
    public String toString()
    {
        return longitude + "," + latitude;
    }
}