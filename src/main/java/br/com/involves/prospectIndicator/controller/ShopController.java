package br.com.involves.prospectIndicator.controller;

import br.com.involves.prospectIndicator.dto.BestRouteDTO;
import br.com.involves.prospectIndicator.dto.ShopDistanceDTO;
import br.com.involves.prospectIndicator.helper.GeoMathHelper;
import br.com.involves.prospectIndicator.helper.TravellerSalesmanHelper;
import br.com.involves.prospectIndicator.model.Employee;
import br.com.involves.prospectIndicator.model.GeoLocatedObject;
import br.com.involves.prospectIndicator.model.Shop;
import br.com.involves.prospectIndicator.reader.ShopCSVReader;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/shop")
public class ShopController {

    @GetMapping(value="getShopsInRadius")
    public List<ShopDistanceDTO> getShopsInRadius(@RequestParam("name") String name, @RequestParam("employee_lat") double employeeLat, @RequestParam("employee_log") double employeeLog,
                                                  @RequestParam("radius") double radius ) {
        return getShopDistanceDTOS(name, employeeLat, employeeLog, radius);
    }

    @GetMapping(value = "downloadShopsInRadius")
    public void downloadShopsInRadius(@RequestParam("name") String name, @RequestParam("employee_lat") double employeeLat, @RequestParam("employee_log") double employeeLog,
                                      @RequestParam("radius") double radius, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename=shopsInRadius.csv");
        try
        {
            ServletOutputStream out = response.getOutputStream();
            List<ShopDistanceDTO> shopDistanceDTOS = getShopDistanceDTOS(name, employeeLat, employeeLog, radius);
            String stringCsv = generateCsv(shopDistanceDTOS);

            InputStream in =
                    new ByteArrayInputStream(stringCsv.getBytes("UTF-8"));

            byte[] outputByte = new byte[4096];
            //copy binary contect to output stream
            while(in.read(outputByte, 0, 4096) != -1)
            {
                out.write(outputByte, 0, 4096);
            }
            in.close();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "getBestRoute")
    public BestRouteDTO getBestRoute(@RequestParam("name") String name, @RequestParam("employee_lat") double employeeLat, @RequestParam("employee_log") double employeeLog,
                                     @RequestParam("radius") double radius) {
        try {
            LinkedList<GeoLocatedObject> points = new LinkedList<>();
            Employee employee = Employee.builder().name(name).latitude(employeeLat).longitude(employeeLog).build();
            points.add(employee);
            ShopCSVReader csvReader = new ShopCSVReader("lojas.csv");
            List<GeoLocatedObject> geoLocatedObjects = csvReader.readObjects();
            List<Shop> shops = geoLocatedObjects.stream().map(geoLocatedObject -> (Shop) geoLocatedObject).collect(Collectors.toList());
            points.addAll(GeoMathHelper.getShopInRadiusWithoutDistance(employee, shops, radius));
            TravellerSalesmanHelper teste = new TravellerSalesmanHelper();
            return teste.calculate(points);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<ShopDistanceDTO> getShopDistanceDTOS(@RequestParam("name") String name, @RequestParam("employee_lat") double employeeLat, @RequestParam("employee_log") double employeeLog, @RequestParam("radius") double radius) {
        Employee employee = Employee.builder().name(name).latitude(employeeLat).longitude(employeeLog).build();
        ShopCSVReader csvReader = new ShopCSVReader("lojas.csv");
        List<GeoLocatedObject> geoLocatedObjects = csvReader.readObjects();
        List<Shop> shops = geoLocatedObjects.stream().map(geoLocatedObject -> (Shop) geoLocatedObject).collect(Collectors.toList());
        return GeoMathHelper.getShopInRadius(employee, shops, radius);
    }

    private String generateCsv(List<ShopDistanceDTO> shopsInRadius) {
        StringBuilder sb = new StringBuilder();
        sb.append("Nome da Loja, Latitude, Longitude, Distancia");
        sb.append("\n");
        for (ShopDistanceDTO shopDistance : shopsInRadius) {
            sb.append(shopDistance.getShop().getName());
            sb.append(",");
            sb.append(shopDistance.getShop().getLatitude());
            sb.append(",");
            sb.append(shopDistance.getShop().getLongitude());
            sb.append(",");
            sb.append(shopDistance.getDistance());
            sb.append("\n");
        }
        return sb.toString();
    }
}
