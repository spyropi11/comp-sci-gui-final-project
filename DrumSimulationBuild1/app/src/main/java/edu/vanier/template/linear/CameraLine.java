package edu.vanier.template.linear;

import edu.vanier.template.elements.Physics;
import edu.vanier.template.elements.Point;
import static edu.vanier.template.simulation.Simulation.CAMERA_LINE_DIST;
import static edu.vanier.template.simulation.Simulation.CAMERA_LINE_LENGTH;
import static edu.vanier.template.simulation.Simulation.CAMERA_LINE_LIMIT;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;

public class CameraLine extends Line {
    
    private final Physics physics;
    
    public CameraLine(Physics physics) {
        this.physics = physics;
    }
    
    public void display(Pane root, double oX, double oY, boolean display) {
        if(!display){return;}
        
        Line base = new Line();
        
        base.setStartX(oX);
        base.setStartY(oY);
        
        Matrix matrix = getProjectionMatrix();
        
        double[] projN = matrix.act(physics.getN());
        
        double[] end = {CAMERA_LINE_LENGTH*projN[0], CAMERA_LINE_LENGTH*projN[1]};
        
        double[] nend = new double[2];
        
        nend[0] = CAMERA_LINE_LIMIT*Math.tanh(CAMERA_LINE_DIST*end[0]);
        nend[1] = CAMERA_LINE_LIMIT*Math.tanh(CAMERA_LINE_DIST*end[1]);
        
        if(Point.crossProduct(physics.getAlpha(), physics.getBeta())[2]>=0) {
            base.setEndX(nend[0] + oX);
            base.setEndY(nend[1] + oY);
        }
        else {
            base.setEndX(-nend[0] + oX);
            base.setEndY(-nend[1] + oY);
        }
        
        boolean onVerticalWall = true;
        
        if(base.getEndX() == oX && base.getEndY() == oY) {
            return;
        }
        
        try {
            if(Math.abs((base.getEndY() - base.getStartY())/(base.getEndX() - base.getStartX())) > 1) {
                onVerticalWall = false;
            }
        } catch(ArithmeticException e) {
            onVerticalWall = false;
        }
        
        if(onVerticalWall) {
            setStartX(0);
            setEndX(2*oX);
            setStartY(getLineBoundX(base, 0));
            setEndY(getLineBoundX(base, 2*oX));
        } else {
            setStartY(0);
            setEndY(2*oX);
            setStartX(getLineBoundY(base, 0));
            setEndX(getLineBoundY(base, 2*oY));
        }
        
        Stop[] stops = new Stop[] {
            new Stop(0, Color.BLUE),
            new Stop(1, Color.RED)
        };
        LinearGradient gradient = new LinearGradient(getStartX(), getStartY(), getEndX(), getEndY(), false, CycleMethod.NO_CYCLE, stops);
        setStroke(gradient);
    }
    
    private double getLineBoundX(Line base, double x) {
        return base.getStartY() + (x - base.getStartX())*(base.getEndY() - base.getStartY())/(base.getEndX() - base.getStartX());
    }
    
    private double getLineBoundY(Line base, double y) {
        return base.getStartX() + (y - base.getStartY())*(base.getEndX() - base.getStartX())/(base.getEndY() - base.getStartY());
    }
    
    private Matrix getProjectionMatrix() {
        double a0 = physics.getAlpha()[0]/Point.norm(physics.getAlpha());
        double a1 = physics.getAlpha()[1]/Point.norm(physics.getAlpha());
        double a2 = physics.getAlpha()[2]/Point.norm(physics.getAlpha());
        double b0 = physics.getBeta()[0]/Point.norm(physics.getBeta());
        double b1 = physics.getBeta()[1]/Point.norm(physics.getBeta());
        double b2 = physics.getBeta()[2]/Point.norm(physics.getBeta());
        
        double[][] m = new double[2][3];
        
        if(a0 != 0) {
            if(b0 != 0) {
                if(b1 != ((a1*b0)/a0)) {
                    double f = b1/b0 - a1/a0;
                    m[0][0] = 1/a0 - (a1/a0)*(-1/(f*a0));
                    m[0][1] = -1/(f*a0);
                    m[0][2] = 0;
                    m[1][0] = -a1/(f*a0*b0);
                    m[1][1] = 1/(f*b0);
                    m[1][2] = 0;
                }
                else {
                    double c = b2/b0 - a2/a0;
                    m[0][0] = 1/a0 + (a2/a0)*(1/a0*c);
                    m[0][1] = 0;
                    m[0][2] = -1/(a0*c);
                    m[1][0] = -a2/(a0*b0*c);
                    m[1][1] = 0;
                    m[1][2] = 1/(b0*c);
                }
            }
            else {
                if(b1 != 0) {
                    m[0][0] = 1/a0;
                    m[0][1] = 0;
                    m[0][2] = 0;
                    m[1][0] = -a1/(a0*b1);
                    m[1][1] = 1/b1;
                    m[1][2] = 0;
                }
                else {
                    m[0][0] = 1/a0;
                    m[0][1] = 0;
                    m[0][2] = 0;
                    m[1][0] = -a2/(a0*b2);
                    m[1][1] = 0;
                    m[1][2] = 1/b2;
                }
            }
        }
        else {
            if(b0 != 0) {
                if(a1 != 0) {
                    m[0][0] = -(b1/a1*b0);
                    m[0][1] = 1/a1;
                    m[0][2] = 0;
                    m[1][0] = 1/b0;
                    m[1][1] = 0;
                    m[1][2] = 0;
                }
                else {
                    m[0][0] = -b2/(a2*b0);
                    m[0][1] = 0;
                    m[0][2] = 1/a2;
                    m[1][0] = 1/b1;
                    m[1][1] = 0;
                    m[1][2] = 0;
                }
            }
            else {
                if(a1 != 0) {
                    if(b1 != 0) {
                        m[0][0] = 0;
                        m[0][1] = (a2*b2 - a1*b2 - a2*b1)/(a1*(a2*b2 - a1*b2));
                        m[0][2] = b1/(a2*b2 - a1*b2);
                        m[1][0] = 0;
                        m[1][1] = -a2/(a1*b2 - a2*b1);
                        m[1][2] = a1/(a1*b2 - a2*b1);
                    }
                    else {
                        m[0][0] = 0;
                        m[0][1] = 1/a1;
                        m[0][2] = 0;
                        m[1][0] = 0;
                        m[1][1] = -a2/(a1*b2);
                        m[1][2] = 1/b2;
                    }
                }
                else {
                    m[0][0] = 0;
                    m[0][1] = -b2/(b1*a1);
                    m[0][2] = 1/a2;
                    m[1][0] = 0;
                    m[1][1] = 1/b1;
                    m[1][2] = 0;
                }
            }
        }
        
        return new Matrix(m);
    }
    
}