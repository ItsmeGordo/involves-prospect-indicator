package br.com.involves.prospectIndicator.helper;

import br.com.involves.prospectIndicator.dto.ShopDistanceDTO;
import br.com.involves.prospectIndicator.model.Employee;
import br.com.involves.prospectIndicator.model.GeoLocatedObject;
import br.com.involves.prospectIndicator.model.Shop;

import java.util.ArrayList;
import java.util.List;

public class GeoMathHelper {
    private static final int EARTH_RADIUS = 6371;
    private static final double RADIAN_PI = 0.017453292519943295;

    public static double calcHaversine(GeoLocatedObject firstPoint, GeoLocatedObject secondPoint) {
        double lat = (secondPoint.getLatitude() - firstPoint.getLatitude()) * RADIAN_PI;
        double lon = (secondPoint.getLongitude() - firstPoint.getLongitude()) * RADIAN_PI;
        double internalSum = (0.5 - Math.cos(lat) / 2)
                + Math.cos(firstPoint.getLatitude() * RADIAN_PI) * Math.cos(secondPoint.getLatitude() * RADIAN_PI)
                * ((1 - Math.cos(lon)) / 2);
        double sqrt = Math.sqrt(internalSum);
        return (EARTH_RADIUS * 2) * Math.asin(sqrt);
    }

    public static List<ShopDistanceDTO> getShopInRadius(Employee employee, List<Shop> shops, Double radius) {
        List<ShopDistanceDTO> shopsInRadius = new ArrayList<>();
        for (Shop shop : shops) {
            double distance = calcHaversine(employee, shop);
            if (distance < radius) {
                ShopDistanceDTO dto = ShopDistanceDTO.builder().distance(distance).shop(shop).build();
                shopsInRadius.add(dto);
            }
        }
        return shopsInRadius;
    }

    public static List<Shop> getShopInRadiusWithoutDistance(Employee employee, List<Shop> shops, Double radius) {
        List<Shop> shopsInRadius = new ArrayList<>();
        for (Shop shop : shops) {
            double distance = calcHaversine(employee, shop);
            if (distance < radius) {
                shopsInRadius.add(shop);
            }
        }
        return shopsInRadius;
    }
}
