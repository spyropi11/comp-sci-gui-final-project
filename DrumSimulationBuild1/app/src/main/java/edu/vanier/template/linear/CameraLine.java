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
        
        setStartX(oX);
        setStartY(oY);
        
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
        
        Matrix matrix = new Matrix(m);
        double[] projN = matrix.act(physics.getN());
        
        double[] end = {CAMERA_LINE_LENGTH*projN[0], CAMERA_LINE_LENGTH*projN[1]};
        
        double[] nend = new double[2];
        
        nend[0] = CAMERA_LINE_LIMIT*Math.tanh(CAMERA_LINE_DIST*end[0]);
        nend[1] = CAMERA_LINE_LIMIT*Math.tanh(CAMERA_LINE_DIST*end[1]);
        
        if(Point.crossProduct(physics.getAlpha(), physics.getBeta())[2]>=0) {
            setEndX(nend[0] + oX);
            setEndY(nend[1] + oY);
        }
        else {
            setEndX(-nend[0] + oX);
            setEndY(-nend[1] + oY);
        }
        
        Stop[] stops = new Stop[] {
            new Stop(0, Color.YELLOW),
            new Stop(1, Color.GREEN)
        };
        LinearGradient gradient = new LinearGradient(getStartX(), getStartY(), getEndX(), getEndY(), false, CycleMethod.NO_CYCLE, stops);
        setStroke(gradient);
    }
    
}