package br.com.involves.prospectIndicator.facade;

import br.com.involves.prospectIndicator.dto.ShopDistanceDTO;
import br.com.involves.prospectIndicator.helper.CsvHelper;
import br.com.involves.prospectIndicator.helper.GeoMathHelper;
import br.com.involves.prospectIndicator.helper.TravellerSalesmanHelper;
import br.com.involves.prospectIndicator.model.Employee;
import br.com.involves.prospectIndicator.model.GeoLocatedObject;
import br.com.involves.prospectIndicator.model.Shop;
import br.com.involves.prospectIndicator.reader.ShopCSVReader;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopFacade {

    public String generateBestRouteCsv(@RequestParam("name") String name, @RequestParam("employee_lat") double employeeLat, @RequestParam("employee_log") double employeeLog, @RequestParam("radius") double radius) {
        try {
            LinkedList<GeoLocatedObject> points = new LinkedList<>();
            Employee employee = Employee.builder().name(name).latitude(employeeLat).longitude(employeeLog).build();
            points.add(employee);
            ShopCSVReader csvReader = new ShopCSVReader("lojas.csv");
            List<GeoLocatedObject> geoLocatedObjects = csvReader.readObjects();
            List<Shop> shops = geoLocatedObjects.stream().map(geoLocatedObject -> (Shop) geoLocatedObject).collect(Collectors.toList());
            points.addAll(GeoMathHelper.getShopInRadiusWithoutDistance(employee, shops, radius));
            TravellerSalesmanHelper travellerSalesmanHelper = new TravellerSalesmanHelper();
            return CsvHelper.generateCsvFromBestRouteDTO(travellerSalesmanHelper.calculate(points));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ShopDistanceDTO> getShopDistanceDTOS(@RequestParam("name") String name, @RequestParam("employee_lat") double employeeLat, @RequestParam("employee_log") double employeeLog, @RequestParam("radius") double radius) {
        Employee employee = Employee.builder().name(name).latitude(employeeLat).longitude(employeeLog).build();
        ShopCSVReader csvReader = new ShopCSVReader("lojas.csv");
        List<GeoLocatedObject> geoLocatedObjects = csvReader.readObjects();
        List<Shop> shops = geoLocatedObjects.stream().map(geoLocatedObject -> (Shop) geoLocatedObject).collect(Collectors.toList());
        return GeoMathHelper.getShopInRadius(employee, shops, radius);
    }
}
