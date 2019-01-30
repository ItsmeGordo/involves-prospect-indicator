package br.com.involves.prospectIndicator.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class GeoLocatedObject {

    private BigDecimal longitude;
    private BigDecimal latitude;
}
