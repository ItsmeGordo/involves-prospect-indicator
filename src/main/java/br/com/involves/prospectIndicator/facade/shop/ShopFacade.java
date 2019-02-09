package br.com.involves.prospectIndicator.facade.shop;

import br.com.involves.prospectIndicator.controller.shop.ShopVMI;
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

import javax.servlet.ServletOutputStream;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopFacade {

    public String generateBestRouteCsv(ShopVMI vmi) {
        try {
            LinkedList<GeoLocatedObject> points = new LinkedList<>();
            Employee employee = Employee.builder().name(vmi.getName()).latitude(vmi.getEmployeeLat()).longitude(vmi.getEmployeeLog()).build();
            points.add(employee);
            ShopCSVReader csvReader = new ShopCSVReader("lojas.csv");
            List<GeoLocatedObject> geoLocatedObjects = csvReader.readObjects();
            List<Shop> shops = geoLocatedObjects.stream().map(geoLocatedObject -> (Shop) geoLocatedObject).collect(Collectors.toList());
            points.addAll(GeoMathHelper.getShopInRadiusWithoutDistance(employee, shops, vmi.getRadius()));
            TravellerSalesmanHelper travellerSalesmanHelper = new TravellerSalesmanHelper();
            return CsvHelper.generateCsvFromBestRouteDTO(travellerSalesmanHelper.calculate(points));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ShopDistanceDTO> getShopDistanceDTOS(ShopVMI vmi) {
        Employee employee = Employee.builder().name(vmi.getName()).latitude(vmi.getEmployeeLat()).longitude(vmi.getEmployeeLog()).build();
        ShopCSVReader csvReader = new ShopCSVReader("lojas.csv");
        List<GeoLocatedObject> geoLocatedObjects = csvReader.readObjects();
        List<Shop> shops = geoLocatedObjects.stream().map(geoLocatedObject -> (Shop) geoLocatedObject).collect(Collectors.toList());
        return GeoMathHelper.getShopInRadius(employee, shops, vmi.getRadius());
    }

    public byte[] getBytesFromShopDistanceCsv(ShopVMI vmi) {
        try {
            List<ShopDistanceDTO> shopDistanceDTOS = this.getShopDistanceDTOS(vmi);
            String stringCsv = CsvHelper.generateCsvFromShopDistanceDTO(shopDistanceDTOS);
            return stringCsv.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "File not found!".getBytes();
        }
    }

    public byte[] getByteFromBestRoute(ShopVMI vmi) {
        try {
            String stringCsv = this.generateBestRouteCsv(vmi);
            return stringCsv.getBytes("UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return "File not found!".getBytes();
        }
    }

}
