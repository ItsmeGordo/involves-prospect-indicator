package br.com.involves.prospectIndicator.facade.employee;

import br.com.involves.prospectIndicator.model.Employee;
import br.com.involves.prospectIndicator.model.GeoLocatedObject;
import br.com.involves.prospectIndicator.reader.EmployeeCSVReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeFacade {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public List<Employee> getEmployees() {
        long startTime = System.currentTimeMillis();
        EmployeeCSVReader csvReader = new EmployeeCSVReader("funcionarios.csv");
        List<GeoLocatedObject> geoLocatedObjects = csvReader.readObjects();
        List<Employee> employees = geoLocatedObjects.stream().map(geoLocatedObject -> (Employee) geoLocatedObject).collect(Collectors.toList());
        long endTime = System.currentTimeMillis();
        log.info(String.format("getEmployees time: %d ms", endTime - startTime));
        return employees;
    }
}
