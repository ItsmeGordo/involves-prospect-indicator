package br.com.involves.prospectIndicator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Shop extends GeoLocatedObject {

    private String name;

    @Builder
    public Shop(String name, Double latitude, Double longitude) {
        super(longitude, latitude);
        this.name = name;
    }

}
