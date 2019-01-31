package br.com.involves.prospectIndicator.reader;

import br.com.involves.prospectIndicator.model.GeoLocatedObject;

import java.io.*;
import java.util.List;

public abstract class AbstractCSVReader {

    protected String fileName;
    protected static final String CSV_SPLITER = ",";

    public AbstractCSVReader(String fileName) {
        this.fileName = fileName;
    }

    public abstract List<GeoLocatedObject> readObjects();

    public BufferedReader reader() {

        try {
            File file = new File(getClass().getClassLoader().getResource("files/" + fileName).getFile());
            InputStream inputStream = new FileInputStream(file);
            return new BufferedReader(new InputStreamReader(inputStream));
        } catch (NullPointerException | FileNotFoundException e) {
            System.out.println("O arquivo mandado não foi encontrado");
            return null;
        }

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
