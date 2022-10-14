package edu.vanier.template.elements;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DrumCreator {
    
    Set<Spring> drum = new HashSet<>();
    //Set<Point> allPoints = new HashSet<>();
    
    public DrumCreator() {
        
    }
    
    /**
     * Adds spring to drum
     * @param a Point a
     * @param b Point b
     * @param k Spring constant
     * @return true if successful and false if there already exists a spring with Point a and Point b.
     */
    public boolean addSpring(Point a, Point b, double k) {
        Spring toBeAdded = new Spring(a,b,k);
        for(Spring otherSpring : drum) {
            if(toBeAdded.isCopy(otherSpring)) {
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
    public boolean addSpring(Spring... springs) {
        Collection<Spring> sps = Arrays.asList(springs);
        for(Spring toBeAdded : sps) {
            for(Spring otherSpring : drum) {
                if(toBeAdded.isCopy(otherSpring)) {
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
    public boolean removeSpring(Spring... springs) {
        try{
            drum.removeAll(Arrays.asList(springs));
            return true;
        }
        catch(NullPointerException e) {
            return false;
        }
    }
    
}