package edu.vanier.template.elements;

import edu.vanier.template.controller.CreateNewDrumController;
import static edu.vanier.template.elements.Point.norm;
import edu.vanier.template.linear.Matrix;
import edu.vanier.template.save.SaveEnvelope;
import edu.vanier.template.simulation.Simulation;
import java.io.IOException;
import java.util.Objects;
import javafx.animation.AnimationTimer;

public final class Physics {
    
    DrumCreator drummer;
    
    private final double[] p = {0, 0, 0};
    private double[] alpha = {1, 0, 0};
    private double[] beta = {0, 1, 0};
    private double[] n = {0, 0, 1};
    
    private double totalTranslateX = 0;
    private double totalTranslateY = 0;
    
    double amplitude;
    double spread;
    
    private int counter = 0;
    private boolean playingBack = false;
    private boolean recording = false;
    private SaveEnvelope saveEnvelope;
    
    final Simulation simulation;
    
    public Point[][] points;
    
    private boolean terminated;
    
    public Physics(Simulation simulation) {
        this.simulation = simulation;
        drummer = new DrumCreator();
    }
    
    public void setPoints(Point[][] points) {
        this.points = points;
        for(Point[] pointList : this.points) {
            for(Point point : pointList) {
                point.setPhysics(this);
            }
        }
    }
    
    private final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            if(!Objects.isNull(saveEnvelope) && playingBack) {
                saveEnvelope.getSavedSim().play(counter, Physics.this);
                CreateNewDrumController.deltaTimeValue = saveEnvelope.getTimeTracker().updateTime(counter);
            }
            update(alpha, beta, n);
            counter++;
            if(recording) {
                saveEnvelope.getTimeTracker().trackTime(counter, CreateNewDrumController.deltaTimeValue);
            }
        }
    };
    
    public void update(double[] oa, double[] ob, double[] on) {
        
        simulation.getCamX().display(simulation.getRoot(), simulation.getDisplay());
        simulation.getCamY().display(simulation.getRoot(), simulation.getDisplay());
        simulation.getCamZup().display(simulation.getRoot(), simulation.getDisplay());
        simulation.getCamZdown().display(simulation.getRoot(), simulation.getDisplay());
        
        for(Point point : drummer.mesh) {
            if(terminated) {
                break;
            }
            point.updateVelocity();
        }
        for(Point point : drummer.mesh) {
            if(terminated) {
                break;
            }
            point.updatePosition();
            point.updateColour();
            project(point, oa, ob, on);
        }
        
    }
    
    private void project(Point point, double[] oa, double[] ob, double[] on) {
        
        double v0 = point.x-p[0];
        double v1 = point.y-p[1];
        double v2 = point.position-p[2];
        
        double sumX = v0*oa[0]+v1*oa[1]+v2*oa[2];
        double sumY = v0*ob[0]+v1*ob[1]+v2*ob[2];
        
        double sumN = v0*on[0] + v1*on[1] + v2*on[2];
        double height = sumN * norm(on);
        point.opacityChange(height);
        
        point.setTranslateX(sumX + simulation.oX);
        point.setTranslateY(sumY + simulation.oY);
    }
    
    public void setMouseClicked() {
            for(int clickedJ = 0; clickedJ < simulation.MESH_HEIGHT; clickedJ++){
                for(int clickedI = 0; clickedI < simulation.MESH_WIDTH; clickedI++){

                    int iclicked = clickedI;
                    int jclicked = clickedJ;

                    points[clickedI][clickedJ].setOnMouseClicked(event -> {
                        if(!playingBack) {
                            amplitude = -CreateNewDrumController.amplitudeValue;
                            spread = CreateNewDrumController.spreadValue;
                            if (spread != 0){
                                for (int j = 0; j < simulation.MESH_HEIGHT; j++){
                                    for(int i = 0; i < simulation.MESH_WIDTH; i++) {
                                        if(!points[i][j].isOnEdge()){
                                            points[i][j].setPosition(points[i][j].getPosition() + 
                                                    amplitude*Math.exp(-((Math.pow(i - iclicked, 2))+(Math.pow(j - jclicked, 2)))/spread));
                                        }
                                    }
                                }
                            }
                        }
                        if(recording) {
                            saveEnvelope.getSavedSim().record(counter, iclicked, jclicked, spread, amplitude);
                        }
                    });
                }
            }
    }
    
    public void loadSaveEnvelope(String folderPath) throws IOException {
        saveEnvelope = new SaveEnvelope(folderPath);
    }
    
    public void startTimer() {
        timer.start();
        terminated = false;
    }
    
    public void stopTimer() {
        timer.stop();
        terminated = true;
    }
    
    public void translate(double x, double y) {
        for(Point point : drummer.mesh) {
            point.x += x;
            point.y += y;
        }
        totalTranslateX += x;
        totalTranslateY += y;
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
    
    public void startPlayBack() {
        playingBack = true;
        saveEnvelope.getSavedSim().startCount(counter);
        saveEnvelope.getTimeTracker().startCount(counter);
    }
    
    public void startRecording() {
        recording = true;
        saveEnvelope.getSavedSim().startCount(counter);
        saveEnvelope.getTimeTracker().startCount(counter);
    }
    
    public void endPlayBack() {
        playingBack = false;
    }
    
    public void resetWaves() {
        //TODO
        for(Point point : drummer.mesh) {
            point.position = 0;
            point.velocity = 0;
            point.vPrevious = 0;
        }
    }
    
    public void resetCamera() {
        alpha[0] = 1;
        alpha[1] = 0;
        alpha[2] = 0;
        beta[0] = 0;
        beta[1] = 1;
        beta[2] = 0;
        n[0] = 0;
        n[1] = 0;
        n[2] = 1;
        for(Point point : drummer.mesh) {
            point.x -= totalTranslateX;
            point.y -= totalTranslateY;
        }
        totalTranslateX = 0;
        totalTranslateY = 0;
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