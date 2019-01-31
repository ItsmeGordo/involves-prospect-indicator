package br.com.involves.prospectIndicator.reader;

import br.com.involves.prospectIndicator.model.GeoLocatedObject;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class ShopCSVReaderTest {

    @Test
    public void readDateTest() {
        ShopCSVReader csvReader = new ShopCSVReader("lojas.csv");
        List<GeoLocatedObject> employees = csvReader.readObjects();
        Assert.assertTrue(employees != null);
        Assert.assertTrue(employees.size() == 25);

    }
}
