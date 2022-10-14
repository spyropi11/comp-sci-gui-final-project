package edu.vanier.template.elements;

import javafx.animation.AnimationTimer;

public class Physics {
    
    DrumCreator drummer;
    
    private AnimationTimer timer = new AnimationTimer() {
        int delay = 1;
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