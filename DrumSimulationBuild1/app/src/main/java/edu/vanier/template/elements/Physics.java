package edu.vanier.template.elements;

import edu.vanier.template.linear.Matrix;
import edu.vanier.template.simulation.Simulation;
import javafx.animation.AnimationTimer;

public final class Physics {
    
    DrumCreator drummer;
    
    private final double[] p = {0, 0, 0};
    private double[] alpha = {1, 0, 0};
    private double[] beta = {0, 1, 0};
    private double[] n = {0, 0, 1};
    
    private int counter = 0;
    
    //Camera centre:
    private double cX;
    private double cY;
    
    final Simulation simulation;
    
    public Point[][] points;
    
    //The color is already handled in Point
    //PhongMaterial mBlue = new PhongMaterial(Color.LIGHTBLUE);
    //PhongMaterial mRed = new PhongMaterial(Color.LIGHTPINK);
    //PhongMaterial mBlack = new PhongMaterial(Color.BLACK);
    
    public Physics(Simulation simulation) {
        this.simulation = simulation;
        drummer = new DrumCreator();
        cX = simulation.getRoot().getPrefWidth()/2;
        cY = simulation.getRoot().getPrefHeight()/2;
    }
    
    public void setPoints(Point[][] points) {
        this.points = points;
    }
    
    private final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            update(simulation.getDELTATIME());
            counter++;
        }
    };
    
    public void update(double DELTATIME) {
        
        simulation.getCameraLine().display(simulation.getRoot(), cX, cY, simulation.getDisplay());
        
        for(Point point : drummer.mesh) {
            point.updateVelocity(DELTATIME);
        }
        for(Point point : drummer.mesh) {
            point.updatePosition(DELTATIME);
            point.updateColour();
            point.projection(p, alpha, beta, n, cX, cY);
        }
    }
    
    public void setMouseClicked() {
        
        for(int clickedJ = 0; clickedJ < simulation.MESH_HEIGHT; clickedJ++){
            for(int clickedI = 0; clickedI < simulation.MESH_WIDTH; clickedI++){
                
                int iclicked = clickedI;
                int jclicked = clickedJ;
                
                points[clickedI][clickedJ].setOnMouseClicked(event -> {
                    
                    double amplitude = 30;
                    double spread = 10;

                
                    for (int j = 0; j < simulation.MESH_HEIGHT; j++){
                        for(int i = 0; i < simulation.MESH_WIDTH; i++) {

                            if(!points[i][j].isOnEdge()){

                                points[i][j].setPosition(points[i][j].getPosition() + amplitude*Math.exp(-((Math.pow(i - iclicked, 2))+(Math.pow(j - jclicked, 2)))/spread));

                            }
                        }

                    }
                    
                    
                
                });
                
            }
            
        }
        
        
    }
    
    public void startTimer() {
        timer.start();
    }
    
    public void stopTimer() {
        timer.stop();
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
        for(Point point : drummer.mesh) {
            point.setScaleX(s*point.getScaleX());
            point.setScaleY(s*point.getScaleY());
            point.currentScale *= s;
        }
    }
    
    //Axis of rotation
    public static enum Axis {
        ALPHA,
        BETA,
        N
    }
    
    /**
     * Rotates the screen along the axis.
     * @param theta Angle in radians.
     * @param axis Axis of rotation.
     */
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