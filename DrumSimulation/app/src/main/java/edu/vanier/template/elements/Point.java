package edu.vanier.template.elements;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Point extends Sphere {
    
    double position;
    double vPrevious = 0;
    double velocity = 0;
    static final double DELTATIME = 0.25;
    double mass;
    boolean onEdge;
    ArrayList<Spring> connectors = new ArrayList<>();
    double x;
    double y;
    
    PhongMaterial material = new PhongMaterial();
    
    static final double COLOUR_NORMALIZATION = 0.2;

    //Constructors
    public Point() {
        super();
    }
    
    /**
     * 
     * @param d Radius
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
     * @param i Number of divisions (Resolution of sphere aka how choppy it looks)
     * @param position Starting point of the node
     * @param mass Mass of node
     */
    public Point(double d, int i, double position, double mass) {
        super(d, i);
        this.position = position;
        this.mass = mass;
    }
    
    /**
     * Positions the point in 1D
     * @param x 
     * @param y
     */
    public void setup(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void projection(double[] p, double[] alpha, double[] beta, double cameraChangeX, double cameraChangeY) {
        double v0 = x-p[0];
        double v1 = y-p[1];
        double v2 = position-p[2];
        
        double sumX = v0*alpha[0]+v1*alpha[1]+v2*alpha[2];
        double sumY = v0*beta[0]+v1*beta[1]+v2*beta[2];
        
        this.setTranslateX(sumX + cameraChangeX);
        this.setTranslateY(sumY + cameraChangeY);
    }
    
    public static double norm(double[] vector) {
        double sum = 0;
        for(double v : vector) {
            sum += Math.pow(v, 2);
        }
        return Math.sqrt(sum);
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
    
    public double getX() {
        return x;
    }
    
    public double getY() {
        return y;
    }
    
    
    //I think that the calculations should be done in a separate class now that we're using Springs. -Ryan
    
    /**
     * Updates the velocity
     */
    
    public void updateVelocity() {
        //If onEdge boolean is set to false
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
    
    public void updateColour() {
        int temperature = (int)(255*Math.tanh(COLOUR_NORMALIZATION*position));
        if(temperature > 0) {
            material.setDiffuseColor(Color.rgb(temperature, 0, 0));
            material.setSpecularColor(Color.rgb(temperature, 0, 0));
        }
        else{
            material.setDiffuseColor(Color.rgb(0, 0, -temperature));
            material.setSpecularColor(Color.rgb(0, 0, -temperature));
        }
        setMaterial(material);
    }
    
    public int normal(double[] p, double[] n) {
        return (int)((x-p[0])*n[0] + (y-p[1])*n[1] + (position-p[2])*n[2]);
    }
    
}