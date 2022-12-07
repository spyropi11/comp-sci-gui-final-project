package edu.vanier.template.save;

import java.io.Serializable;

public class Instance implements Serializable {
    
    private final double deltaTime;
    
    private final double position;
    
    private final double velocity;
    
    public Instance(double deltaTime, double position, double velocity) {
        this.deltaTime = deltaTime;
        this.position = position;
        this.velocity = velocity;
    }
    
    @Override
    public String toString() {
        return " " + deltaTime + " " + position + " " + velocity + " ";
    }
    
    public double getDeltaTime() {
        return deltaTime;
    }
    
    public double getPosition() {
        return position;
    }
    
    public double getVelocity() {
        return velocity;
    }
    
}