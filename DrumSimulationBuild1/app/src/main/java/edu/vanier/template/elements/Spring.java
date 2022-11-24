package edu.vanier.template.elements;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Spring extends Line {
    
    /**
     * Pair of points that the spring connects.
     */
    Point[] couple = new Point[2];

    //Constructor
    public Spring(Point pointA, Point pointB) {
        //Adding the first point that the spring is connected to
        couple[0] = pointA;
        //Adding the second point
        couple[1] = pointB;
        
        //Adding this spring to the array of connectors in both Point objects
        pointA.connectors.add(this);
        pointB.connectors.add(this);
        
        setStrokeWidth(3);
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
    
    public void updateLine() {
        setStartX(couple[0].translateXProperty().doubleValue());
        setStartY(couple[0].translateYProperty().doubleValue());
        setEndX(couple[1].translateXProperty().doubleValue());
        setEndY(couple[1].translateYProperty().doubleValue());
        double red = 127*(couple[0].material.getDiffuseColor().getRed()+couple[1].material.getDiffuseColor().getRed());
        double green = 127*(couple[0].material.getDiffuseColor().getGreen()+couple[1].material.getDiffuseColor().getGreen());
        double blue = 127*(couple[0].material.getDiffuseColor().getBlue()+couple[1].material.getDiffuseColor().getBlue());
        setStroke(Color.rgb((int)red, (int)green, (int)blue));
    }
    
    //Getters and Setters

    public Point[] getCouple() {
        return couple;
    }

    public void setCouple(Point[] couple) {
        this.couple = couple;
    }
    
}