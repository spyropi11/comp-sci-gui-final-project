package edu.vanier.template.save;

import edu.vanier.template.drumshapes.ConstructFormable;
import java.io.Serializable;
import java.util.ArrayList;

public class Tracker implements Serializable {
    
    private final ArrayList<Instance> instances = new ArrayList<>();
    
    private final ConstructFormable form;
    
    private int index = 0;
    
    public Tracker(ConstructFormable form) {
        this.form = form;
    }
    
    public void record(Instance instance) {
        instances.add(instance);
    }
    
    public Instance next() throws IndexOutOfBoundsException {
        Instance instance = instances.get(index);
        index++;
        return instance;
    }
    
    public ConstructFormable getForm() {
        return form;
    }
    
}