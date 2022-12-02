package edu.vanier.template.elements;

import edu.vanier.template.controller.CreateNewDrumController;
import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

public class Point extends Sphere {
    
    double position;
    double vPrevious = 0;
    double velocity = 0;
    //static double DELTATIME = 0.06;
    double springConstant = 3;
    double mass;
    double maximumDampening;
    /**
     * dampeningEditor value must be between 0.0 and 1.0
     */
    double dampeningEditor = 0.99;
    double dampeningConstant;
    boolean onEdge;
    ArrayList<Spring> connectors = new ArrayList<>();

    double x;
    double y;
    
    int i;
    int j;
    
    double currentScale = 1;
    
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
        super(d, 20);
        setOpacity(0.5);
        this.position = position;
        this.mass = mass;
        this.maximumDampening = (2*Math.sqrt(mass*springConstant));
        this.dampeningConstant = this.maximumDampening - ((this.dampeningEditor)*this.maximumDampening);

    }
    
    /**
     * Positions the point in 3D.
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     */
    public void setup(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public void projection(double[] p, double[] alpha, double[] beta, double[] n, double cameraChangeX, double cameraChangeY) {
        double v0 = x-p[0];
        double v1 = y-p[1];
        double v2 = position-p[2];
        
        double sumX = v0*alpha[0]+v1*alpha[1]+v2*alpha[2];
        double sumY = v0*beta[0]+v1*beta[1]+v2*beta[2];
        
        double sumN = v0*n[0] + v1*n[1] + v2*n[2];
        double height = sumN / Math.pow(norm(n), 2);
        opacityChange(height);
        
        this.setTranslateX(sumX + cameraChangeX);
        this.setTranslateY(sumY + cameraChangeY);
    }
    
    public static double norm(double... vector) {
        double sum = 0;
        for(double v : vector) {
            sum += Math.pow(v, 2);
        }
        return Math.sqrt(sum);
    }
    
    public static double[] crossProduct(double[] u, double[] v) {
        double[] cross = new double[3];
        cross[0] = u[1]*v[2] - u[2]*v[1];
        cross[1] = u[2]*v[0] - u[0]*v[2];
        cross[2] = u[0]*v[1] - u[1]*v[0];
        return cross;
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


    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
    
    public boolean isOnEdge() {
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
    
    public double getDampeningConstant() {
        return dampeningConstant;
    }
    
    public void setDampeningConstant(double dampeningConstant) {
        this.dampeningConstant = dampeningConstant;
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
                force += springConstant*(otherPoint.position - position);
                
            }
            force -= dampeningConstant*vPrevious;
            //divide by the mass (F=ma --> a=F/m).
            double acc = force / mass;
            //get new velocity.
            velocity = vPrevious + CreateNewDrumController.deltaTimeValue*acc;
        }
    }
    
    public void updatePosition() {
        if(!onEdge) {
            position += CreateNewDrumController.deltaTimeValue*velocity;
        }
    }
    
    public void updateColour() {
        int temperature = (int)(255*Math.atan(COLOUR_NORMALIZATION*position)/(Math.PI/2));
        if(temperature > 0) {
            material.setDiffuseColor(Color.rgb(0, 0, temperature));
            material.setSpecularColor(Color.rgb(0, 0, temperature));
        }
        else{
            material.setDiffuseColor(Color.rgb(-temperature, 0, 0));
            material.setSpecularColor(Color.rgb(-temperature, 0, 0));
        }
        setMaterial(material);
    }
    
    private void opacityChange(double height) {
        setOpacity(Math.pow(1.1, height)/2);
    }
    
}
