package br.com.involves.prospectIndicator.controller;

import br.com.involves.prospectIndicator.dto.ShopDistanceDTO;
import br.com.involves.prospectIndicator.model.Employee;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @RequestMapping("/getShopsInRadius")
    public List<ShopDistanceDTO> getShopsInRadius() {
        return null;
    }
}
