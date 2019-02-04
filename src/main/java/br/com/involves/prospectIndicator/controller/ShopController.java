package br.com.involves.prospectIndicator.controller;

import br.com.involves.prospectIndicator.dto.ShopDistanceDTO;
import br.com.involves.prospectIndicator.helper.GeoMathHelper;
import br.com.involves.prospectIndicator.model.Employee;
import br.com.involves.prospectIndicator.model.GeoLocatedObject;
import br.com.involves.prospectIndicator.model.Shop;
import br.com.involves.prospectIndicator.reader.ShopCSVReader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @RequestMapping(value="getShopsInRadius", method = RequestMethod.GET)
    public List<ShopDistanceDTO> getShopsInRadius(@RequestParam("name") String name, @RequestParam("employee_lat") double employeeLat, @RequestParam("employee_log") double employeeLog, @RequestParam("radius") double radius ) {
        Employee employee = Employee.builder().name(name).latitude(employeeLat).longitude(employeeLog).build();
        ShopCSVReader csvReader = new ShopCSVReader("lojas.csv");
        List<GeoLocatedObject> geoLocatedObjects = csvReader.readObjects();
        List<Shop> shops = geoLocatedObjects.stream().map(geoLocatedObject -> (Shop) geoLocatedObject).collect(Collectors.toList());
        return GeoMathHelper.getShopInRadius(employee, shops, radius);
    }
}
