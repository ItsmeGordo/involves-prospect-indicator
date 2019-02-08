package br.com.involves.prospectIndicator.facade;

import br.com.involves.prospectIndicator.model.Employee;
import br.com.involves.prospectIndicator.model.GeoLocatedObject;
import br.com.involves.prospectIndicator.reader.EmployeeCSVReader;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeFacade {


    public List<Employee> getEmployees() {
        EmployeeCSVReader csvReader = new EmployeeCSVReader("funcionarios.csv");
        List<GeoLocatedObject> geoLocatedObjects = csvReader.readObjects();
        return geoLocatedObjects.stream().map(geoLocatedObject -> (Employee) geoLocatedObject).collect(Collectors.toList());
    }
}
