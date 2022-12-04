package edu.vanier.template.drumshapes;

import edu.vanier.template.elements.Point;
import edu.vanier.template.elements.Spring;
import static edu.vanier.template.simulation.Simulation.RADIUS;
import static edu.vanier.template.simulation.Simulation.NATURAL_MASS;
import java.util.ArrayList;

public class RectangleDrum extends Formable {
    
    private final int width;
    private final int height;
    
    public RectangleDrum(int width, int height) throws ArithmeticException {
        this.width = width;
        this.height = height;
        checkCap();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void generateMesh() {
        Point[][] points = new Point[width][height];
        for (int j = 0; j < height; j++){
            for(int i = 0; i < width; i++) {
                if(j == 0 || j == height - 1) {
                    // This puts two points on the edges and sets their onEdge value to true
                    points[i][j] = new Point(RADIUS, 0, NATURAL_MASS);
                    points[i][j].setOnEdge(true);
                } else if(i == 0 || i == width-1) {
                    // This puts two points on the edges and sets their onEdge value to true
                    points[i][j] = new Point(RADIUS, 0, NATURAL_MASS);
                    points[i][j].setOnEdge(true);
                    
                } else{
                    points[i][j] = new Point(RADIUS, 0,NATURAL_MASS);
                    points[i][j].setOnEdge(false);
                }
                points[i][j].setup(2*RADIUS*i, 2*RADIUS*j);
                // One line:
                if((width == 1 && height != 1 && j != 0 && j != height-1)
                        || (height == 1 && width != 1 && i != 0 && i != width-1)
                        ) {
                    points[i][j].setOnEdge(false);
                }
            }
        }
        this.mesh = points;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void generateDrum() {
        ArrayList<Spring> springs = new ArrayList<>();
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                for(int[] pair : bindSpring(i, j)) {
                    try {
                        springs.add(new Spring(mesh[i][j], mesh[pair[0]][pair[1]]));
                    } catch(ArrayIndexOutOfBoundsException e) {}
                }
            }
        }
        this.drum = springs;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected int particleCount() {
        return width*height;
    }
    /**
     * Creates distribution for parameter mass, decay, or spring constant in drum.
     * @param distribution The distribution type.
     * @param i Index i of a point.
     * @param j Index j of a point.
     * @return 
     */
    protected double distributeIndex(Distribution distribution, int i, int j) {
        switch(distribution.getSurface()) {
            case UNIFORM -> {
                return distribution.getStops()[0];
            }
            case HORIZONTAL_GRADIENT -> {
                return distribution.getStops()[0] + i*(distribution.getStops()[1] - distribution.getStops()[0])/(width - 1);
            }
            case VERTICAL_GRADIENT -> {
                return distribution.getStops()[0] + j*(distribution.getStops()[1] - distribution.getStops()[0])/(height - 1);
            }
            case RADIAL_GRADIENT -> {
                int ringI;
                int ringJ;
                int ringTotal = width < height ? (int)Math.ceil(width/2) : (int)Math.ceil(height/2);
                if(i < width/2) {
                    ringI = i;
                } else {
                    ringI = width - i;
                }
                if(j < height/2) {
                    ringJ = j;
                } else {
                    ringJ = height - j;
                }
                int ring = ringI < ringJ ? ringI : ringJ;
                return distribution.getStops()[0] + ring*(distribution.getStops()[1] - distribution.getStops()[0])/(ringTotal);
            }
        }
        return -1;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void generateMass() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                mesh[i][j].setMass(distributeIndex(mass, i, j));
            }
        }
    }

    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
}