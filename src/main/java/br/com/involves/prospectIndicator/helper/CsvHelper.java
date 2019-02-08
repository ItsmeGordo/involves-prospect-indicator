package br.com.involves.prospectIndicator.helper;

import br.com.involves.prospectIndicator.dto.BestRouteDTO;
import br.com.involves.prospectIndicator.dto.ShopDistanceDTO;
import br.com.involves.prospectIndicator.model.GeoLocatedObject;

import java.util.List;

public class CsvHelper {

    public static String generateCsvFromShopDistanceDTO(List<ShopDistanceDTO> shopsInRadius) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome da Loja, Latitude, Longitude, Distancia");
        sb.append("\n");
        for (ShopDistanceDTO shopDistance : shopsInRadius) {
            sb.append(shopDistance.getShop().getName());
            sb.append(",");
            sb.append(shopDistance.getShop().getLatitude());
            sb.append(",");
            sb.append(shopDistance.getShop().getLongitude());
            sb.append(",");
            sb.append(shopDistance.getDistance());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static String generateCsvFromBestRouteDTO(BestRouteDTO bestRoute) {
        StringBuilder sb = new StringBuilder();
        sb.append("Sequencia, Ponto, Latitude, Longitude");
        sb.append("\n");
        int sequencia = 0;
        for (GeoLocatedObject geoObject : bestRoute.getPoints()) {
            sb.append(sequencia);
            sb.append(",");
            sb.append(geoObject.getName());
            sb.append(",");
            sb.append(geoObject.getLatitude());
            sb.append(",");
            sb.append(geoObject.getLongitude());
            sb.append("\n");
            sequencia++;
        }

        return sb.toString();
    }
}
