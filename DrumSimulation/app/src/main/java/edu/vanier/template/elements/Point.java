package edu.vanier.template.elements;

import java.util.ArrayList;
import javafx.scene.shape.Sphere;

public class Point extends Sphere {
    
    double position;
    double vPrevious = 0;
    double velocity = 0;
    static final double DELTATIME = 0.01;
    double mass;
    boolean isEdge;
    ArrayList<Spring> connectors;

    //Constructors
    public Point() {
        super();
    }

    /**
     * 
     * @param d
     * @param position Starting point of the node
     * @param mass Mass of node
     */
    public Point(double d, double position, double mass) {
        super(d);
        this.position = position;
        this.mass = mass;
    }
    
    /**
     * 
     * @param d Radius
     * @param i Number of divisions
     * @param position Starting point of the node
     * @param mass Mass of node
     */
    public Point(double d, int i, double position, double mass) {
        super(d, i);
        this.position = position;
        this.mass = mass;
        
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
    
    public boolean isEdge() {
        return isEdge;
    }
    
    public void setIsEdge(boolean isEdge) {
        this.isEdge = isEdge;
    }

    
    
    //I think that the calculations should be done in a separate class now that we're using Springs. -Ryan
    
    /**
     * 
     * @param array
     * @param a The left neighbor of point b
     * @param c The right neighbor of point b
     */
    
    public void updateCurrentVelocity(ArrayList<Point> array, int a, int c){
        if(isEdge) {
            velocity = 0;
        }
        else{
            /*
            this.velocity = (((this.springConstant * DELTATIME) / this.mass) * 
                    (array.get(a).getPosition() + array.get(c).getPosition() - (2 * this.position)) + vPrevious);
            */
        }
    }
    
    
    public void updateNextPosition(){

        /*

        this.nextPosition = DELTATIME* (this.velocity) + position;

        this.position = this.nextPosition;
        this.vPrevious = this.velocity;
        */

        
    }
    
    
}