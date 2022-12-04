package edu.vanier.template.drumshapes;

import edu.vanier.template.elements.Point;
import edu.vanier.template.elements.Spring;
import static edu.vanier.template.simulation.Simulation.NATURAL_MASS;
import static edu.vanier.template.simulation.Simulation.RADIUS;
import java.util.ArrayList;

public class SquareDrum extends Formable {
    
    private final int side;
    
    public SquareDrum(int side) throws ArithmeticException {
        this.side = side;
        checkCap();
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void generateMesh() {
        Point[][] points = new Point[side][side];
        for (int j = 0; j < side; j++){
            for(int i = 0; i < side; i++) {
                if(j == 0 || j == side - 1) {
                    //This puts two points on the edges and sets their onEdge value to true
                    points[i][j] = new Point(RADIUS, 0, NATURAL_MASS);
                    points[i][j].setOnEdge(true);
                } else if(i == 0 || i == side-1) {
                    //This puts two points on the edges and sets their onEdge value to true
                    points[i][j] = new Point(RADIUS, 0, NATURAL_MASS);
                    points[i][j].setOnEdge(true);
                    
                } else{
                    points[i][j] = new Point(RADIUS, 0,NATURAL_MASS);
                    points[i][j].setOnEdge(false);
                }
                points[i][j].setup(2*RADIUS*i, 2*RADIUS*j);
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
        for(int i = 0; i < side; i++){
            for(int j = 0; j < side; j++){
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
        return side^2;
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
                return distribution.getStops()[0] + i*(distribution.getStops()[1] - distribution.getStops()[0])/(side - 1);
            }
            case VERTICAL_GRADIENT -> {
                return distribution.getStops()[0] + j*(distribution.getStops()[1] - distribution.getStops()[0])/(side - 1);
            }
            case RADIAL_GRADIENT -> {
                int ring;
                int ringTotal = (int)Math.ceil(side/2);
                if(i < side/2) {
                    ring = i;
                } else {
                    ring = side - i;
                }
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
        for(int i = 0; i < side; i++) {
            for(int j = 0; j < side; j++) {
                mesh[i][j].setMass(distributeIndex(mass, i, j));
            }
        }
    }
    
    
    public int getSide() {
        return side;
    }
    
}