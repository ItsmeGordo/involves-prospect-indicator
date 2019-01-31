package br.com.involves.prospectIndicator.helper;

import br.com.involves.prospectIndicator.model.GeoLocatedObject;

public class GeoMathHelper {
    private static final int EARTH_RADIUS = 6371;
    private static final double RADIAN_PI = 0.017453292519943295;

    public static double calcHaversine(GeoLocatedObject firstPoint, GeoLocatedObject secondPoint) {
        double lat = (secondPoint.getLatitude() - firstPoint.getLatitude()) * RADIAN_PI;
        double lon = (secondPoint.getLongitude() - firstPoint.getLongitude()) * RADIAN_PI;
        double internalSum = (0.5 - Math.cos(lat)/2)
                + Math.cos(firstPoint.getLatitude() * RADIAN_PI) * Math.cos(secondPoint.getLatitude() * RADIAN_PI)
                * ((1 - Math.cos(lon)) / 2);
        double sqrt = Math.sqrt(internalSum);
        return (EARTH_RADIUS * 2) * Math.asin(sqrt);
    }
}
