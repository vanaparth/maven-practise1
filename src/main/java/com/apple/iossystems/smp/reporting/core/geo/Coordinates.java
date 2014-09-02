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

    public static Coordinates getInstance(float longitude, float latitude)
    {
        return new Coordinates(longitude, latitude);
    }

    public float getLongitude()
    {
        return longitude;
    }

    public float getLatitude()
    {
        return latitude;
    }

    @Override
    public String toString()
    {
        return longitude + "," + latitude;
    }

    public String getReverseString()
    {
        return latitude + "," + longitude;
    }

    public static Coordinates getDefaultCoordinates()
    {
        return DEFAULT_COORDINATES;
    }
}