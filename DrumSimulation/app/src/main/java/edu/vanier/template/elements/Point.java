package edu.vanier.template.elements;

import java.util.ArrayList;
import javafx.scene.shape.Sphere;

public class Point extends Sphere {
    
    double position;
    double vPrevious = 0;
    double velocity = 0;
    static final double DELTATIME = 0.1;
    double mass;
    boolean onEdge;
    ArrayList<Spring> connectors = new ArrayList<>();

    //Constructors
    public Point() {
        super();
    }

    /**
     * 
     * @param d
     * @param position Starting point of the node
     * @param mass Mass of node
     * @param isEdge Boolean denoting when a point is on edge (velocity==0)
     */
    public Point(double d, double position, double mass, boolean isEdge) {
        super(d);
        this.position = position;
        this.mass = mass;
        this.onEdge = isEdge;
    }
    
    /**
     * 
     * @param d Radius
     * @param i Number of divisions
     * @param position Starting point of the node
     * @param mass Mass of node
     * @param isEdge Boolean denoting when a point is on edge (velocity==0)
     */
    public Point(double d, int i, double position, double mass, boolean isEdge) {
        super(d, i);
        this.position = position;
        this.mass = mass;
        this.onEdge = isEdge;
        this.setTranslateY(position);
    }
    
    /**
     * Positions the point in 1D
     * @param x 
     */
    public void setup(int x) {
        this.translateXProperty().set(x);
    }
    
    //Getters and Setters
    public double getPosition() {
        return position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public double getvPrevious() {
        return vPrevious;
    }

    public void setvPrevious(double vPrevious) {
        this.vPrevious = vPrevious;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public static double getDELTATIME() {
        return DELTATIME;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
    
    public boolean getOnEdge() {
        return onEdge;
    }
    
    public void setOnEdge(boolean isEdge) {
        this.onEdge = isEdge;
    }
    
    
    //I think that the calculations should be done in a separate class now that we're using Springs. -Ryan
    
    /**
     * Updates the velocity
     */
    
    public void updateVelocity() {
        if(!onEdge) {
            //set previous velocity to velocity.
            vPrevious = velocity;
            //force.
            double force = 0;
            //individual force from each connected point is added on to get net force.
            for(Spring spring : connectors) {
                
                Point otherPoint = spring.otherPoint(this);
                force += spring.springConstant*(otherPoint.position - position);
                
            }
            //divide by the mass (F=ma --> a=F/m).
            double acc = force / mass;
            //get new velocity.
            velocity = vPrevious + DELTATIME*acc;
        }
    }
    
    public void updatePosition() {
        if(!onEdge) {
            position += DELTATIME*velocity;
            move();
        }
    }
    
    public void move() {
        this.setTranslateY(position);
    }
    
}