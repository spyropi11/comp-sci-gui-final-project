package edu.vanier.template.save;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SavedSim {
    
    private List<PulseInstance> sim;
    
    private final File csvFile;
    
    public SavedSim(String csvFilePath) throws IOException {
        csvFile = new File(csvFilePath);
        csvFile.createNewFile();
    }
    
    public void download() throws FileNotFoundException {
        sim = new CsvToBeanBuilder(new FileReader(csvFile)).withType(PulseInstance.class).build().parse();
    }
    
    public void upload() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        StatefulBeanToCsv<PulseInstance> beanToCsv = new StatefulBeanToCsvBuilder(new FileWriter(csvFile)).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
        beanToCsv.write(sim);
    }
    
}