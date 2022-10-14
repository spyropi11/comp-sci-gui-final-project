package edu.vanier.template.elements;

public class Physics {
    
    DrumCreator drummer;
    
    public Physics() {
        drummer = new DrumCreator();
    }
    
    public DrumCreator getDrummer() {
        return drummer;
    }
    
    public void setDrummer(DrumCreator drummer) {
        this.drummer = drummer;
    }
    
}