package br.com.involves.prospectIndicator.dto;

import br.com.involves.prospectIndicator.model.Shop;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShopDistanceDTO {

    private Shop shop;
    private Double distance;
}
