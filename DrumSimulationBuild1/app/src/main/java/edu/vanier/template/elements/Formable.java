package edu.vanier.template.elements;

import java.util.HashSet;

public interface Formable {
    
    public HashSet<Point> formMesh();
    public HashSet<Spring> formDrum();
    
}