package edu.vanier.template.elements;

import edu.vanier.template.linear.Matrix;
import edu.vanier.template.simulation.Simulation;
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
    
    final Simulation tester;
    
    public Point[][] points;
    
    //The color is already handled in Point
    //PhongMaterial mBlue = new PhongMaterial(Color.LIGHTBLUE);
    //PhongMaterial mRed = new PhongMaterial(Color.LIGHTPINK);
    //PhongMaterial mBlack = new PhongMaterial(Color.BLACK);
    
    public Physics(Simulation tester) {
        this.tester = tester;
        drummer = new DrumCreator();
    }
    
    public void setPoints(Point[][] points) {
        this.points = points;
    }
    
    private final AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            update();
        }
    };
    
    public void update() {
        tester.displayCameraLine(tester.getRoot(), cX, cY, true);
        /*
        * This shows springs
        for(Spring spring : drummer.drum) {
            spring.updateLine();
        }
        */
        for(Point point : drummer.mesh) {
            point.updateVelocity();
        }
        for(Point point : drummer.mesh) {
            point.updatePosition();
            point.updateColour();
            point.projection(p, alpha, beta, cX, cY);
            /*
            * Coloring is done in Point class
            if(point.getPosition() < 0){
                point.setMaterial(mRed);
                
            }
            else if(point.getPosition() > 0){
                
                point.setMaterial(mBlue);
                
            }
            else{
                
                point.setMaterial(mBlack);
                
            }
            */
            
            point.setOnMouseDragOver(event -> {
            
            System.out.println("point has been hovered!");
                
            });
        
            point.setOnMouseClicked(event -> {
            
                double amplitude = 30;
                double spread = 10;
                double shiftX = point.getX()/4;
                double shiftZ = point.getY()/4;
                
                int counter = 0;
                
                /*amplitude and spread per click will be variables that can be changed 
                by the user after further implementation*/
                /*TODO This doesn't work. I think it would be better if points
                was a two-dimensional array instead of a one-dimensional array. That way, it's easier to retrieve a specific point,
                or points next to the point clicked on.*/
                for (int j = 0; j < tester.MESH_HEIGHT; j++){
                    for(int i = 0; i < tester.MESH_WIDTH; i++) {
                        
                        if(points[i][j].getOnEdge() ==  false){
                            
                            points[i][j].setPosition(points[i][j].getPosition() + amplitude*Math.exp(-((Math.pow(i - shiftX, 2))+(Math.pow(j - shiftZ, 2)))/spread));
                            
                        }

                        counter++;
                    }
                    
                    
                }
            });
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