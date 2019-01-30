package br.com.involves.prospectIndicator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee extends GeoLocatedObject {

    private String name;

}
