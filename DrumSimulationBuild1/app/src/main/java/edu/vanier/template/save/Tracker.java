package edu.vanier.template.save;

import edu.vanier.template.drumshapes.Formable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Tracker implements Serializable {
    
    private final List<Instance> instances = new ArrayList<>();
    
    private final Formable formable;
    
    private int index = 0;
    
    public Tracker(Formable formable) {
        this.formable = formable;
    }
    
    public void record(Instance instance) {
        instances.add(instance);
    }
    
    public Instance next() throws IndexOutOfBoundsException {
        Instance instance = instances.get(index);
        index++;
        return instance;
    }
    
    public Formable getFormable() {
        return formable;
    }
    
}