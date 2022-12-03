package edu.vanier.template.linear;

import edu.vanier.template.elements.Physics;
import edu.vanier.template.elements.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class CameraAxis extends Line {
    
    private static final double MAGNIFICATION = 50;
    
    public enum Axis {
        X,
        Y,
        Zup,
        Zdown
    }
    
    private final Axis axis;
    
    private final Physics physics;
    
    public CameraAxis(Physics physics, Axis axis) {
        this.axis = axis;
        this.physics = physics;
    }
    
    public void display(Pane root, double oX, double oY, boolean display) {
        if(!display) {
            return;
        }
        setStartX(oX);
        setStartY(oY);
        
        int component = -1;
        double direction = 0;
        switch(axis) {
            case X -> {
                component = 0;
                setFill(Color.PURPLE);
                setStroke(Color.PURPLE);
                direction = 1;
            }
            case Y -> {
                component = 1;
                setFill(Color.GREEN);
                setStroke(Color.GREEN);
                direction = 1;
            }
            case Zup -> {
                component = 2;
                setFill(Color.BLUE);
                setStroke(Color.BLUE);
                direction = 1.5;
            }
            case Zdown -> {
                component = 2;
                setFill(Color.RED);
                setStroke(Color.RED);
                direction = -1.5;
            }
        }
        
        double xV = direction * MAGNIFICATION * physics.getAlpha()[component] * Point.norm(physics.getAlpha());
        double yV = direction * MAGNIFICATION * physics.getBeta()[component] * Point.norm(physics.getBeta());
        setEndX(xV + oX);
        setEndY(yV + oY);
        
        double depth = -direction * physics.getN()[component] * Point.norm(physics.getN());
        double tryDepth = 0.6 + depth;
        setOpacity(
                tryDepth < 0.2 ? 0.2 :
                        tryDepth > 1 ? 1 : tryDepth
        );
    }
    
}