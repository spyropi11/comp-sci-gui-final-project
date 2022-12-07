package edu.vanier.template.save;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import edu.vanier.template.elements.Physics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SavedSim {
    
    private List<PulseInstance> sim = new ArrayList<>();
    
    private final File csvFile;
    
    private int currentCounter = -1;
    private int nextIndex;
    private int initialCount;
    
    public SavedSim(File csvFile) throws IOException {
        this.csvFile = csvFile;
        this.csvFile.createNewFile();
    }
    
    public void download() throws FileNotFoundException {
        sim = new CsvToBeanBuilder(new FileReader(csvFile)).withType(PulseInstance.class).build().parse();
    }
    
    public void upload() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
        StatefulBeanToCsv<PulseInstance> beanToCsv = new StatefulBeanToCsvBuilder(new FileWriter(csvFile)).withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
        beanToCsv.write(sim);
    }
    
    public void play(int counter, Physics physics) {
        if(sim.get(nextIndex).counter == (counter - initialCount)) {
            sim.get(nextIndex).createPulse(physics);
        } else {
            currentCounter++;
            if(currentCounter > sim.size()) {
                physics.endPlayBack();
            }
        }
    }
    
    public void record(int counter, int pointI, int pointJ, double spread, double amplitude) {
        PulseInstance pulse = new PulseInstance((counter - initialCount), pointI, pointJ, spread, amplitude);
        if(sim.isEmpty()) {
            nextIndex = 0;
        }
        sim.add(pulse);
    }
    
    public void startCount(int counter) {
        initialCount = counter;
    }
    
}