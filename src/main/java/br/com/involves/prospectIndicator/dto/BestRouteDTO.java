package br.com.involves.prospectIndicator.dto;

import br.com.involves.prospectIndicator.model.GeoLocatedObject;
import br.com.involves.prospectIndicator.model.Shop;
import lombok.Builder;
import lombok.Data;

import java.util.LinkedList;

@Data
@Builder
public class BestRouteDTO {

    private LinkedList<GeoLocatedObject> points;
    private double distance;

}
