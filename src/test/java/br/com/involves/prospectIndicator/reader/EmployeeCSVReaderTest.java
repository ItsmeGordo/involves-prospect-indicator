package br.com.involves.prospectIndicator.reader;

import br.com.involves.prospectIndicator.helper.GeoMathHelper;
import br.com.involves.prospectIndicator.model.Employee;
import br.com.involves.prospectIndicator.model.GeoLocatedObject;
import br.com.involves.prospectIndicator.model.Shop;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EmployeeCSVReaderTest {

    @Test
    public void readDateTest() {
        EmployeeCSVReader csvReader = new EmployeeCSVReader("funcionarios.csv");
        List<GeoLocatedObject> employees = csvReader.readObjects();
        Assert.assertTrue(employees != null);
        Assert.assertTrue(employees.size() == 5);

    }
}
