package br.com.involves.prospectIndicator.controller;

import br.com.involves.prospectIndicator.dto.BestRouteDTO;
import br.com.involves.prospectIndicator.dto.ShopDistanceDTO;
import br.com.involves.prospectIndicator.facade.ShopFacade;
import br.com.involves.prospectIndicator.helper.CsvHelper;
import br.com.involves.prospectIndicator.helper.GeoMathHelper;
import br.com.involves.prospectIndicator.helper.TravellerSalesmanHelper;
import br.com.involves.prospectIndicator.model.Employee;
import br.com.involves.prospectIndicator.model.GeoLocatedObject;
import br.com.involves.prospectIndicator.model.Shop;
import br.com.involves.prospectIndicator.reader.ShopCSVReader;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private ShopFacade shopFacade;

    @GetMapping("/getShopsInRadius")
    public List<ShopDistanceDTO> getShopsInRadius(@RequestParam("name") String name, @RequestParam("employee_lat") double employeeLat, @RequestParam("employee_log") double employeeLog,
                                                  @RequestParam("radius") double radius) {
        return shopFacade.getShopDistanceDTOS(name, employeeLat, employeeLog, radius);
    }

    @GetMapping("/downloadShopsInRadius")
    public void downloadShopsInRadius(@RequestParam("name") String name, @RequestParam("employee_lat") double employeeLat, @RequestParam("employee_log") double employeeLog,
                                      @RequestParam("radius") double radius, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=shopsInRadius.csv");
        try {
            ServletOutputStream out = response.getOutputStream();
            List<ShopDistanceDTO> shopDistanceDTOS = shopFacade.getShopDistanceDTOS(name, employeeLat, employeeLog, radius);
            String stringCsv = CsvHelper.generateCsvFromShopDistanceDTO(shopDistanceDTOS);

            InputStream in =
                    new ByteArrayInputStream(stringCsv.getBytes("UTF-8"));

            byte[] outputByte = new byte[4096];
            //copy binary contect to output stream
            while (in.read(outputByte, 0, 4096) != -1) {
                out.write(outputByte, 0, 4096);
            }
            in.close();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GetMapping(value = "downloadBestRoute")
    public void downloadBestRoute(@RequestParam("name") String name, @RequestParam("employee_lat") double employeeLat, @RequestParam("employee_log") double employeeLog,
                                  @RequestParam("radius") double radius, HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=shopsInRadius.csv");
        try {
            ServletOutputStream out = response.getOutputStream();
            String stringCsv = shopFacade.generateBestRouteCsv(name, employeeLat, employeeLog, radius);

            InputStream in =
                    new ByteArrayInputStream(stringCsv.getBytes("UTF-8"));

            byte[] outputByte = new byte[4096];
            //copy binary contect to output stream
            while (in.read(outputByte, 0, 4096) != -1) {
                out.write(outputByte, 0, 4096);
            }
            in.close();
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
