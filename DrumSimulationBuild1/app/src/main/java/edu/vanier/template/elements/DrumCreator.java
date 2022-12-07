package edu.vanier.template.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class DrumCreator {
    
    List<Spring> drum = new ArrayList<>();
    List<Point> mesh = new ArrayList<>();
    
    /**
     * Adds spring to drum
     * @param a Point a
     * @param b Point b
     * @return true if successful and false if there already exists a spring with Point a and Point b, or a both points are the same.
     */
    public boolean addSpring(Point a, Point b) {
        if(a==b) {
            return false;
        }
        Spring toBeAdded = new Spring(a, b);
        for(Spring otherSpring : drum) {
            if(toBeAdded.isCopyOf(otherSpring)) {
                return false;
            }
        }
        drum.add(toBeAdded);
        return true;
    }
    
    /**
     * Adds multiple springs to drum
     * @param springs Springs to be added
     * @return true if successful and false if any one of the spring parameters is a copy of a spring in drum.
     */
    public boolean addSprings(Spring... springs) {
        Collection<Spring> sps = Arrays.asList(springs);
        for(Spring toBeAdded : sps) {
            for(Spring otherSpring : drum) {
                if(toBeAdded.isCopyOf(otherSpring)) {
                    return false;
                }
            }
        }
        drum.addAll(sps);
        return true;
    }
    
    /**
     * Removes spring, for example if you want to replace existing spring.
     * @param spring Spring to be removed
     * @return true if successful and false if there exists no such spring
     */
    public boolean removeSpring(Spring spring) {
        try{
            drum.remove(spring);
            return true;
        }
        catch(NullPointerException e) {
            return false;
        }
    }
    
    /**
     * Removes multiple springs, for example if you want to replace existing springs.
     * @param springs Springs to be removed
     * @return true if successful and false if there exists no such spring
     */
    public boolean removeSprings(Spring... springs) {
        try{
            drum.removeAll(Arrays.asList(springs));
            return true;
        }
        catch(NullPointerException e) {
            return false;
        }
    }
    
    public void addToMesh(Point[][] points) {
        for(Point[] pointList : points) {
            mesh.addAll(Arrays.asList(pointList));
        }
    }
    
    public Point getPoint(int i, int j) throws NullPointerException {
        for(Point point : mesh) {
            if(point.i == i && point.j == j) {
                return point;
            }
        }
        throw new NullPointerException("Point " + i + ", " + j + " not found.");
    }
    
    //Getters and Setters
    
    public List<Spring> getDrum() {
        return drum;
    }
    
    public void setDrum(ArrayList<Spring> drum) {
        this.drum = drum;
    }
    
    public List<Point> getMesh() {
        return mesh;
    }
    
    public void setMesh(ArrayList<Point> mesh) {
        this.mesh = mesh;
    }
    
}