package br.com.involves.prospectIndicator.controller;

import br.com.involves.prospectIndicator.model.Employee;
import br.com.involves.prospectIndicator.model.GeoLocatedObject;
import br.com.involves.prospectIndicator.reader.EmployeeCSVReader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employee")
public class EmployeeController {

    @RequestMapping("/getEmployees")
    public List<Employee> getEmployees() {
        EmployeeCSVReader csvReader = new EmployeeCSVReader("funcionarios.csv");
        List<GeoLocatedObject> geoLocatedObjects = csvReader.readObjects();
        return geoLocatedObjects.stream().map(geoLocatedObject -> (Employee) geoLocatedObject).collect(Collectors.toList());
    }
}
