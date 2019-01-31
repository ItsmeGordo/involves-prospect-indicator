package br.com.involves.prospectIndicator.helper;

import br.com.involves.prospectIndicator.model.Employee;
import br.com.involves.prospectIndicator.model.Shop;
import org.junit.Assert;
import org.junit.Test;

public class GeoMathHelperTest {

    @Test
    public void calcHaversineTest() {
        Employee employee = Employee.builder().name("Juca").latitude(-27.6019111d).longitude(-48.5957299d).build();
        Shop shop = Shop.builder().name("Loja bacana").latitude(-27.6066129d).longitude(-48.5803426d).build();

        Double result = GeoMathHelper.calcHaversine(employee, shop);

        Assert.assertTrue(result.equals(1.603832548369759d));

    }
}
