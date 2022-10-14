package edu.vanier.template.elements;

public class Spring {
    
    /**
     * Pair of points that the spring connects. When doing recursion, go from couple[0] to couple[1]. -Ryan
     */
    Point[] couple = new Point[2];
    /**
     * Spring constant of spring
     */
    double springConstant;
    

    //Constructor
    public Spring(Point pointA, Point pointB, double springConstant) {
        couple[0] = pointA;
        couple[1] = pointB;
        this.springConstant = springConstant;
        pointA.connectors.add(this);
        pointB.connectors.add(this);
    }
    
    //Compares if another spring is the same
    public boolean isCopy(Spring otherSpring) {
        return otherSpring.getCouple() == this.couple;
    }
    
    //Getters and Setters

    public Point[] getCouple() {
        return couple;
    }

    public void setCouple(Point[] couple) {
        this.couple = couple;
    }

    public double getSpringConstant() {
        return springConstant;
    }

    public void setSpringConstant(double springConstant) {
        this.springConstant = springConstant;
    }
    
}