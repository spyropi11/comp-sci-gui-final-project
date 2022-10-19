package edu.vanier.template.elements;

import edu.vanier.template.linear.Matrix;
import edu.vanier.template.tests.Tester;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javafx.animation.AnimationTimer;

public class Physics {
    
    DrumCreator drummer;
    
    private double[] p = {0, 0, 0};
    private double[] alpha = {1, 0, 0};
    private double[] beta = {0, 1, 0};
    private double[] n = {0, 0, 1};
    //Camera centre:
    private double cX;
    private double cY;
    
    Tester tester;
    
    //Comparator is used to render spheres in specific order so they are stacked in that order.
    private final Comparator<Point> comp = new Comparator<>() {
        @Override
        public int compare(Point p1, Point p2) {
            return p1.normal(p, n) - p2.normal(p, n);
        }
    };
    
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
    
    public Physics(Tester tester) {
        this.tester = tester;
        drummer = new DrumCreator();
    }
    
    public void update() {
        tester.displayCameraLine(tester.getRoot(), cX, cY, true);
        for(Spring spring : drummer.drum) {
            spring.updateLine();
        }
        for(Point point : drummer.mesh.values()) {
            point.updateVelocity();
        }
        for(Point point : drummer.mesh.values()) {
            point.updatePosition();
            point.updateColour();
            point.projection(p, alpha, beta, cX, cY);
        }
        List<Point> values = new ArrayList<>(drummer.mesh.values());
        Collections.sort(values, comp);
    }
    
    public void translate(double x, double y) {
        for(int i = 0; i < 3; i++) {
            p[i] += x*alpha[i] + y*beta[i];
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
        
        alpha = rotation.act(alpha);
        beta = rotation.act(beta);
        n = rotation.act(n);
        
    }
    
    public void setOrigin(double px, double py, double pz) {
        p[0] = px;
        p[1] = py;
        p[2] = pz;
    }
    
    public void setCameraCentre(double oX, double oY) {
        cX = oX;
        cY = oY;
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
    
    public double[] getN() {
        return n;
    }
    
    public double[] getP() {
        return p;
    }
    
    public double[] getAlpha() {
        return alpha;
    }
    
    public double[] getBeta() {
        return beta;
    }
    
}