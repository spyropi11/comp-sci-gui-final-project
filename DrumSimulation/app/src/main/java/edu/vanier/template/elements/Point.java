package edu.vanier.template.elements;

import java.util.ArrayList;
import javafx.scene.shape.Sphere;

public class Point extends Sphere {
    
    double position;
    double tempPosition;
    double vPrevious = 0;
    double velocity = 0;
    static final double DELTATIME = 0.01;
    double mass;
    int edgeNumber = 0;
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
        if(isEdge) {
            edgeNumber = 1;
        }
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
        if(isEdge) {
            edgeNumber = 1;
        }
        
    }
    
    /**
     * Positions the point in 1D
     * @param x 
     */
    public void move(int x) {
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
    
    public int getEdgeNumber() {
        return edgeNumber;
    }
    
    public void setEdgeNumber(int edgeNumber) {
        this.edgeNumber = edgeNumber;
    }
    
    public double getTempPosition() {
        return tempPosition;
    }
    
    public void setTempPosition(double tempPosition) {
        this.tempPosition = tempPosition;
    }

    
    
    //I think that the calculations should be done in a separate class now that we're using Springs. -Ryan
    
    /**
     * 
     * @param array
     * @param a The left neighbor of point b
     * @param c The right neighbor of point b
     */
    
    public void updateCurrentVelocity(ArrayList<Point> array, int a, int c){
        if(edgeNumber == 1) {
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