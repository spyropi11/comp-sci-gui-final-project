/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProjectTester;

import java.util.ArrayList;
import javafx.scene.shape.Sphere;

/**
 * 
 * @author Spyros
 */
public class Point extends Sphere{
    
    public double position;
    public double nextPosition;
    public double vPrevious = 0;
    public double velocity = 0;
    public static final double DELTATIME = 0.01;
    public double springConstant;
    public double mass;

    
    //Constructors
    public Point() {

    }

    /**
     * 
     * @param d
     * @param position Starting point of the node
     * @param springConstant Spring constant of neighboring springs
     * @param mass Mass of node
     */
    public Point(double d, double position, double springConstant, double mass) {
        super(d);
        
        setPosition(position);
        setSpringConstant(springConstant);
        setMass(mass);
    }

    
    /**
     * 
     * @param d
     * @param i
     * @param position Starting point of the node
     * @param springConstant Spring constant of neighboring springs
     * @param mass Mass of node
     */
    public Point(double d, int i, double position, double springConstant, double mass) {
        super(d, i);
        
        setPosition(position);
        setSpringConstant(springConstant);
        setMass(mass);
        
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


    public double getSpringConstant() {
        return springConstant;
    }

    public void setSpringConstant(double springConstant) {
        this.springConstant = springConstant;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
    
    
    

    
    
    
    
    /**
     * 
     * @param array
     * @param a The left neighbor of point b
     * @param c The right neighbor of point b
     */
    
    public void updateCurrentVelocity(ArrayList<Point> array, int a, int c){
        
        this.velocity = (((this.springConstant * DELTATIME) / this.mass) * 
                (array.get(a).getPosition() + array.get(c).getPosition() - (2 * this.position)) + vPrevious);
        
    }
    
    
    public void updateNextPosition(){
        
       
       this.nextPosition = DELTATIME* (this.velocity) + position;
       
       this.position = this.nextPosition;
       this.vPrevious = this.velocity;
       
        
    }
    
    
}
