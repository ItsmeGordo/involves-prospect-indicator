package br.com.involves.prospectIndicator.reader;

import br.com.involves.prospectIndicator.model.Employee;
import br.com.involves.prospectIndicator.model.GeoLocatedObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeCSVReader extends AbstractCSVReader {

    private static final int NAME_POS = 0;
    private static final int LAT_POS = 1;
    private static final int LOG_POS = 2;

    public EmployeeCSVReader(String fileName) {
        super(fileName);
    }

    @Override
    public List<GeoLocatedObject> readObjects() {
        BufferedReader br = super.reader();
        String line;
        List<GeoLocatedObject> employees = new ArrayList<>();

        try {
            String[] header = br.readLine().split(CSV_SPLITER);
            while ((line = br.readLine()) != null) {
                String[] data = line.split(CSV_SPLITER);
                employees.add(createEmployee(data));
            }
            return employees;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Employee createEmployee(String[] lineData) {
        return Employee.builder().name(lineData[NAME_POS]).latitude(Double.parseDouble(lineData[LAT_POS])).longitude(Double.parseDouble(lineData[LOG_POS])).build();
    }
}
