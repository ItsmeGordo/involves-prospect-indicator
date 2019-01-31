package br.com.involves.prospectIndicator.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class GeoLocatedObject {

    private String name;
    private Double longitude;
    private Double latitude;

}
