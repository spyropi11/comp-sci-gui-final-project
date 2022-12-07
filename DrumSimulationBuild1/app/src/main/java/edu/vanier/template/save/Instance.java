package edu.vanier.template.save;

public class Instance {
    
    private final double deltaTime;
    
    private final int i;
    
    private final int j;
    
    private final double position;
    
    private final double velocity;
    
    private final double vPrevious;
    
    public Instance(double deltaTime, int i, int j, double position, double velocity, double vPrevious) {
        this.deltaTime = deltaTime;
        this.i = i;
        this.j = j;
        this.position = position;
        this.velocity = velocity;
        this.vPrevious = vPrevious;
    }
    
    @Override
    public String toString() {
        return " " + deltaTime + " " + i + " " + j + " " + position + " " + velocity + " " + vPrevious + " ";
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public double getPosition() {
        return position;
    }

    public double getVelocity() {
        return velocity;
    }

    public double getVPrevious() {
        return vPrevious;
    }
    
}