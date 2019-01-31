package br.com.involves.prospectIndicator.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Employee extends GeoLocatedObject {

    @Builder
    public Employee(String name, Double latitude, Double longitude) {
        super(name, longitude, latitude);
    }

}
