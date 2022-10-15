package edu.vanier.template.elements;

public class Spring {
    
    /**
     * Pair of points that the spring connects.
     */
    Point[] couple = new Point[2];
    /**
     * Spring constant of spring
     */
    double springConstant;
    

    //Constructor
    public Spring(Point pointA, Point pointB, double springConstant) {
        //Adding the first point that the spring is connected to
        couple[0] = pointA;
        //Adding the second point
        couple[1] = pointB;
        //Specifying the spring between both points mentioned above
        this.springConstant = springConstant;
        
        //Adding this spring to the array of connectors in both Point objects
        pointA.connectors.add(this);
        pointB.connectors.add(this);
    }
    
    //Compares if another spring is the same
    public boolean isCopyOf(Spring otherSpring) {
        
        //If one of these statements is true the method will return true.
        return (couple[0]==otherSpring.couple[0] && couple[1]==otherSpring.couple[1]) || (couple[0]==otherSpring.couple[1] && couple[1]==otherSpring.couple[0]);
    }
    
    //Gets other point
    public Point otherPoint(Point point) {
        if(couple[0] == point) {
            return couple[1];
        }
        return couple[0];
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