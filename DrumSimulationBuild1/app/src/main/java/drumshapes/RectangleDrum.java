package drumshapes;

import edu.vanier.template.elements.Point;
import edu.vanier.template.elements.Spring;
import edu.vanier.template.simulation.Simulation;

public class RectangleDrum extends Formable {
    
    private int width;
    private int height;
    
    private int getMeshWidth() {
        return (int)Math.floor(density*width);
    }
    
    private int getMeshHeight() {
        return (int)Math.floor(density*height);
    }
    
    @Override
    public Point[] formMesh() {
        Point[][] points = new Point[getMeshWidth()][getMeshHeight()];
        for (int j = 0; j < getMeshHeight(); j++){
            
            for(int i = 0; i < getMeshWidth(); i++) {
                
                if(j == 0 || j == getMeshHeight() - 1) {
                    //This puts two points on the edges and sets their onEdge value to true
                    points[i][j] = new Point(Simulation.RADIUS, 1, 0, Simulation.NATURAL_MASS);
                    points[i][j].setOnEdge(true);
                    
                }
                
                else if(i == 0 || i == getMeshWidth()-1) {
                    //This puts two points on the edges and sets their onEdge value to true
                    points[i][j] = new Point(Simulation.RADIUS, 1, 0, Simulation.NATURAL_MASS);
                    points[i][j].setOnEdge(true);
                    
                }
                
                

                //2d Gausian initial condition (bell curve type shape) 
                //u(x,y,t=0)= Amplitude*exp(-((x+shift)^2+(y+ shift)^2)/(spread)
                else{
                    
                    
                    //points[counter] = new Point(2,1,(amplitude*Math.exp(-((Math.pow(i - shift, 2))+(Math.pow(j - shift, 2)))/spread)),NATURAL_MASS, false);
                    points[i][j] = new Point(Simulation.RADIUS, 1, 0,Simulation.NATURAL_MASS);
                    points[i][j].setOnEdge(false);
                }
                
                
                
                //points[i].setMass(NATURAL_MASS*(1+i/50));
                
            }
            
        }
        
        //TODO
        return null;
    }

    @Override
    public Spring[] formDrum() {
        //TODO
        return null;
    }

    @Override
    protected int particleCount(double density) {
        //TODO
        return -1;
    }
    
}