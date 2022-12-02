package edu.vanier.template.save;

import com.opencsv.bean.CsvBindByPosition;
import edu.vanier.template.elements.Physics;

public class PulseInstance {
    
    @CsvBindByPosition(position = 0)
    int counter;
    @CsvBindByPosition(position = 0)
    int pointI;
    @CsvBindByPosition(position = 0)
    int pointJ;
    @CsvBindByPosition(position = 0)
    double spread;
    @CsvBindByPosition(position = 0)
    double amplitude;
    
    public void createPulse(Physics physics) {
        //TODO
    }
    
}