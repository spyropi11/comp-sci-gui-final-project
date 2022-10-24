package edu.vanier.template.elements;

import edu.vanier.template.tests.Tester;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;

public class Physics {
    
    DrumCreator drummer;
    
    PhongMaterial mBlue = new PhongMaterial(Color.LIGHTBLUE);
    PhongMaterial mRed = new PhongMaterial(Color.LIGHTPINK);
    PhongMaterial mBlack = new PhongMaterial(Color.BLACK);
    
    public Point[] points = Tester.getPoints();
    
    private final AnimationTimer timer = new AnimationTimer() {
        int delay = 0;
        int delayCounter = 0;
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
            if(point.getPosition() < 0){
                point.setMaterial(mRed);
                
            }
            
            else if(point.getPosition() > 0){
                
                point.setMaterial(mBlue);
                
            }
            else{
                
                point.setMaterial(mBlack);
                
            }
            
            point.setOnMouseDragOver(event -> {
            
            System.out.println("point has been hovered!");
                
            });
        
            point.setOnMouseClicked(event -> {
            
                double amplitude = 30;
                double spread = 10;
                double shiftX = point.getTranslateX()/4;
                double shiftZ = point.getTranslateZ()/4;
                
                int counter = 0;
                
                /*amplitude and spread per click will be variables that can be changed 
                by the user after further implementation*/
                
                for (int j = 0; j < Math.sqrt(points.length); j++){
                    for(int i = 0; i < Math.sqrt(points.length); i++) {
                        
                        if(points[counter].getOnEdge() ==  false){
                            
                            points[counter].setPosition(points[counter].getPosition() + amplitude*Math.exp(-((Math.pow(i - shiftX, 2))+(Math.pow(j - shiftZ, 2)))/spread));
                            
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