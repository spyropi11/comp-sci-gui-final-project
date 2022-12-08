package edu.vanier.template.drumshapes;

import edu.vanier.template.drumshapes.Distribution.Surface;
import edu.vanier.template.drumshapes.Formable.Arrangement;
import java.io.Serializable;

public class ConstructFormable implements Serializable {
    
    enum Shape {
        SQUARE,
        RECTANGLE,
        PARALLELOGRAM,
        TRAPEZOID
    }
    
    final Shape shape;
    
    final int[] parameters;
    
    final Arrangement texture;
    
    final Surface mass;
    
    final double[] stops;
    
    public ConstructFormable(Shape shape, int[] parameters, Arrangement texture, Surface mass, double[] stops) {
        this.shape = shape;
        this.parameters = parameters;
        this.texture = texture;
        this.mass = mass;
        this.stops = stops;
    }
    
}