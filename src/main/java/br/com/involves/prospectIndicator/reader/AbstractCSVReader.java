package br.com.involves.prospectIndicator.reader;

import br.com.involves.prospectIndicator.model.GeoLocatedObject;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;


@Getter
@Setter
public abstract class AbstractCSVReader {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected String fileName;
    protected static final String CSV_SPLITER = ",";

    public AbstractCSVReader(String fileName) {
        this.fileName = fileName;
    }

    public abstract List<GeoLocatedObject> readObjects();

    public BufferedReader reader() {

        try {
            InputStream inputStream = getClass().getResourceAsStream("/files/" + fileName);
            return new BufferedReader(new InputStreamReader(inputStream));
        } catch (NullPointerException e) {
            log.error("O arquivo mandado não foi encontrado");
            return null;
        }

    }

}
