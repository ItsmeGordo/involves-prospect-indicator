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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public String generateBestRouteCsv(ShopVMI vmi) {
        try {
            long startTime = System.currentTimeMillis();
            LinkedList<GeoLocatedObject> points = new LinkedList<>();
            Employee employee = Employee.builder().name(vmi.getName()).latitude(vmi.getEmployeeLat()).longitude(vmi.getEmployeeLog()).build();
            points.add(employee);
            ShopCSVReader csvReader = new ShopCSVReader("lojas.csv");
            List<GeoLocatedObject> geoLocatedObjects = csvReader.readObjects();
            List<Shop> shops = geoLocatedObjects.stream().map(geoLocatedObject -> (Shop) geoLocatedObject).collect(Collectors.toList());
            points.addAll(GeoMathHelper.getShopInRadiusWithoutDistance(employee, shops, vmi.getRadius()));
            TravellerSalesmanHelper travellerSalesmanHelper = new TravellerSalesmanHelper();
            String csv = CsvHelper.generateCsvFromBestRouteDTO(travellerSalesmanHelper.calculate(points));
            long endTime = System.currentTimeMillis();
            log.info(String.format("generateBestRouteCsv time: %d ms", endTime - startTime));
            return csv;
        } catch (Exception e) {
            log.error("Ocorreu um erro inesperado no sistema.", e);
            return null;
        }
    }

    public List<ShopDistanceDTO> getShopDistanceDTOS(ShopVMI vmi) {
        long startTime = System.currentTimeMillis();
        Employee employee = Employee.builder().name(vmi.getName()).latitude(vmi.getEmployeeLat()).longitude(vmi.getEmployeeLog()).build();
        ShopCSVReader csvReader = new ShopCSVReader("lojas.csv");
        List<GeoLocatedObject> geoLocatedObjects = csvReader.readObjects();
        List<Shop> shops = geoLocatedObjects.stream().map(geoLocatedObject -> (Shop) geoLocatedObject).collect(Collectors.toList());
        List<ShopDistanceDTO> shopInRadius = GeoMathHelper.getShopInRadius(employee, shops, vmi.getRadius());
        long endTime = System.currentTimeMillis();
        log.info(String.format("getShopDistanceDTOS time: %d ms", endTime - startTime));
        return shopInRadius;
    }

    public byte[] getBytesFromShopDistanceCsv(ShopVMI vmi) {
        try {
            long startTime = System.currentTimeMillis();
            List<ShopDistanceDTO> shopDistanceDTOS = this.getShopDistanceDTOS(vmi);
            String stringCsv = CsvHelper.generateCsvFromShopDistanceDTO(shopDistanceDTOS);
            byte[] csvBytes = stringCsv.getBytes("UTF-8");
            long endTime = System.currentTimeMillis();
            log.info(String.format("getBytesFromShopDistanceCsv time: %d ms", endTime - startTime));
            return csvBytes;
        } catch (Exception e) {
            String msg = "Ocorreu um erro inesperado no sistema.";
            log.error(msg, e);
            return msg.getBytes();
        }
    }

    public byte[] getByteFromBestRoute(ShopVMI vmi) {
        try {
            long startTime = System.currentTimeMillis();
            String stringCsv = this.generateBestRouteCsv(vmi);
            byte[] csvBytes = stringCsv.getBytes("UTF-8");
            long endTime = System.currentTimeMillis();
            log.info(String.format("getByteFromBestRoute time: %d ms", endTime - startTime));
            return csvBytes;
        } catch (Exception e) {
            String msg = "Ocorreu um erro inesperado no sistema.";
            log.error(msg, e);
            return msg.getBytes();
        }
    }

}
