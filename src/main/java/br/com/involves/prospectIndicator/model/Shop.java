package br.com.involves.prospectIndicator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Shop extends GeoLocatedObject {

    @Builder
    public Shop(String name, Double latitude, Double longitude) {
        super(name, longitude, latitude);
    }

}
