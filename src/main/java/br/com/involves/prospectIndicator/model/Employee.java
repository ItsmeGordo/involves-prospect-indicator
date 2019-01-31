package br.com.involves.prospectIndicator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Employee extends GeoLocatedObject {

    private String name;

    @Builder
    public Employee(String name, Double latitude, Double longitude) {
        super(longitude, latitude);
        this.name = name;
    }

}
