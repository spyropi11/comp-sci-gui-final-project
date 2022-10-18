package edu.vanier.template.elements;

import edu.vanier.template.linear.Matrix;
import javafx.animation.AnimationTimer;

public class Physics {
    
    DrumCreator drummer;
    
    private double[] p = {0, 0, 0};
    private double[] alpha = {1, 0, 0};
    private double[] beta = {0, 1, 0};
    private double[] n = {0, 0, 1};
    
    private final AnimationTimer timer = new AnimationTimer() {
        int delay = 0;
        int delayCounter = -100;
        @Override
        public void handle(long now) {
            if(delayCounter == delay) {
                update();
                delayCounter = 0;
            }
            else{
                delayCounter++;
            }
        }
    };
    
    public Physics() {
        drummer = new DrumCreator();
    }
    
    public void update() {
        for(Point point : drummer.mesh) {
            point.updateVelocity();
        }
        for(Point point : drummer.mesh) {
            point.updatePosition();
            point.updateColour();
            point.projection(p, alpha, beta);
        }
    }
    
    public void translate(double x, double y) {
        for(int i = 0; i < 3; i++) {
            p[i] += x*alpha[i] + y*alpha[i];
        }
    }
    
    public void zoom(double s) throws ArithmeticException {
        if(s <= 0) {
            throw new ArithmeticException();
        }
        for(int i = 0; i < 3; i++) {
            alpha[i] *= s;
            beta[i] *= s;
        }
    }
    
    public void zoom(double a, double b) throws ArithmeticException {
        if((a <= 0) || (b <= 0)) {
            throw new ArithmeticException();
        }
        for(int i = 0; i < 3; i++) {
            alpha[i] *= a;
            beta[i] *= b;
        }
    }
    
    //Axis of rotation
    public static enum Axis {
        ALPHA,
        BETA,
        N
    }
    
    //theta is in radians
    public void rotate(double theta, Axis axis) {
        
        double[][] restArr = {
            {alpha[0], beta[0], n[0]},
            {alpha[1], beta[1], n[1]},
            {alpha[2], beta[2], n[2]}
        };
        
        Matrix restoring = new Matrix(restArr);
        
        double[][] arr = new double[3][3];
        switch(axis) {
            case ALPHA -> {
                arr[0][0] = 1;   arr[0][1] = 0;               arr[0][2] = 0;
                arr[1][0] = 0;   arr[1][1] = Math.cos(theta); arr[1][2] = -Math.sin(theta);
                arr[2][0] = 0;   arr[2][1] = Math.sin(theta); arr[2][2] = Math.cos(theta);
            }
            case BETA -> {
                arr[0][0] = Math.cos(theta);   arr[0][1] = 0; arr[0][2] = -Math.sin(theta);
                arr[1][0] = 0;                 arr[1][1] = 1; arr[1][2] = 0;
                arr[2][0] = Math.sin(theta);   arr[2][1] = 0; arr[2][2] = Math.cos(theta);
            }
            case N -> {
                arr[0][0] = Math.cos(theta);   arr[0][1] = -Math.sin(theta); arr[0][2] = 0;
                arr[1][0] = Math.sin(theta);   arr[1][1] = Math.cos(theta);  arr[1][2] = 0;
                arr[2][0] = 0;                 arr[2][1] = 0;                arr[2][2] = 1;
            }
        }
        
        Matrix rotation = new Matrix(arr);
        
        alpha = restoring.act(rotation.act(1,0,0));
        beta = restoring.act(rotation.act(0,1,0));
        n = restoring.act(rotation.act(0,0,1));
        
    }
    
    public void startTimer() {
        timer.start();
    }
    
    public void stopTimer() {
        timer.stop();
    }
    
    public void pauseTimer() {
        try {
            timer.wait();
        }
        catch (InterruptedException ex) {
            System.out.println("Could not execute timer.wait()");
        }
    }
    
    public void resumeTimer() {
        timer.notify();
    }
    
    //Getters and Setters
    
    public DrumCreator getDrummer() {
        return drummer;
    }
    
    public void setDrummer(DrumCreator drummer) {
        this.drummer = drummer;
    }
    
}