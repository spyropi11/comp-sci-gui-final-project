package edu.vanier.template.linear;

import edu.vanier.template.elements.Physics;
import edu.vanier.template.elements.Point;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * The CameraAxis is line that originates from the camera's origin and extends in the direction of a certain axis.
 * The possible axes are X, Y, Zup, and Zdown. These have different colors, and transformations of the camera, i.e.
 * translation, rotation, and zooming affect the direction and length of the displayed line.
 */
public class CameraAxis extends Line {
    /**
     * The initial length of an axis.
     */
    public static final double MAGNIFICATION = 50;
    /**
     * The possible axes that can be projected onto the screen.
     */
    public enum Axis {
        X,
        Y,
        Zup,
        Zdown
    }
    /**
     * The axis is projected onto the screen, represented by a 2D plane.
     */
    private final Axis axis;
    /**
     * The physics property provides the nature of the screen's plane representation.
     */
    private final Physics physics;
    
    public double xV;
    public double yV;
    
    /**
     * Instantiates a CameraAxis that utilizes the physics' projecting plane.
     * @param physics
     * @param axis 
     */
    public CameraAxis(Physics physics, Axis axis) {
        this.axis = axis;
        this.physics = physics;
    }
    /**
     * Displays a line on screen representing an axis in the current camera's frame of reference.
     * @param root The line is displayed on this pane.
     */
    public void display(Pane root) {
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
        
        xV = direction * MAGNIFICATION * physics.getAlpha()[component] * Point.norm(physics.getAlpha());
        yV = direction * MAGNIFICATION * physics.getBeta()[component] * Point.norm(physics.getBeta());
        setEndX(xV + getStartX());
        setEndY(yV + getStartY());
        
        double depth = -direction * physics.getN()[component] * Point.norm(physics.getN());
        double tryDepth = 0.6 + depth;
        setOpacity(
                tryDepth < 0.2 ? 0.2 :
                        tryDepth > 1 ? 1 : tryDepth
        );
    }
}