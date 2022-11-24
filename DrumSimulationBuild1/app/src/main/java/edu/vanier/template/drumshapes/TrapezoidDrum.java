package edu.vanier.template.drumshapes;

import edu.vanier.template.elements.Point;
import edu.vanier.template.elements.Spring;
import static edu.vanier.template.simulation.Simulation.RADIUS;
import static edu.vanier.template.simulation.Simulation.NATURAL_MASS;
import java.util.ArrayList;

public class TrapezoidDrum extends Formable {
    
    private final int baseOne;
    private final int baseTwo;
    private final int widthSide;
    private final int heightSide;
    private final double angle;
    
    public TrapezoidDrum(int baseOne, int baseTwo, int heightSide, double angle) {
        this.baseOne = baseOne;
        this.baseTwo = baseTwo;
        this.heightSide = heightSide;
        this.angle = angle;
        widthSide = baseOne > baseTwo ? baseOne : baseTwo;
    }
    
    private int getMeshWidth() {
        return (int)Math.floor(density*widthSide);
    }
    
    private int getMeshHeight() {
        return (int)Math.floor(density*heightSide);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void generateMesh() {
        Point[][] points = new Point[getMeshWidth()][getMeshHeight()];
        for (int j = 0; j < getMeshHeight(); j++){
            for(int i = 0; i < getMeshWidth(); i++) {
                if(j == 0 || j == getMeshHeight() - 1) {
                    //This puts two points on the edges and sets their onEdge value to true
                    points[i][j] = new Point(RADIUS, 1, 0, NATURAL_MASS);
                    points[i][j].setOnEdge(true);
                } else if(i == 0 || i == getMeshWidth()-1) {
                    //This puts two points on the edges and sets their onEdge value to true
                    points[i][j] = new Point(RADIUS, 1, 0, NATURAL_MASS);
                    points[i][j].setOnEdge(true);
                    
                } else{
                    points[i][j] = new Point(RADIUS, 1, 0,NATURAL_MASS);
                    points[i][j].setOnEdge(false);
                }
                double level = baseTwo + j*(baseOne - baseTwo)/getMeshHeight();
                double step = i*level/getMeshWidth();
                double offset = 2*RADIUS*j/Math.tan(angle) + step;
                points[i][j].setup(2*RADIUS*i + offset, 2*RADIUS*j);
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
        for(int i = 0; i < getMeshWidth(); i++){
            for(int j = 0; j < getMeshHeight(); j++){
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
    protected int particleCount(double density) {
        int meshWidth = (int)Math.floor(density*widthSide);
        int meshHeight = (int)Math.floor(density*heightSide);
        return meshWidth*meshHeight;
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
                return distribution.getStops()[0] + i*(distribution.getStops()[1] - distribution.getStops()[0])/(getMeshWidth() - 1);
            }
            case VERTICAL_GRADIENT -> {
                return distribution.getStops()[0] + j*(distribution.getStops()[1] - distribution.getStops()[0])/(getMeshHeight() - 1);
            }
            case RADIAL_GRADIENT -> {
                int ringI;
                int ringJ;
                int ringTotal = getMeshWidth() < getMeshHeight() ? (int)Math.ceil(getMeshWidth()/2) : (int)Math.ceil(getMeshHeight()/2);
                if(i < getMeshWidth()/2) {
                    ringI = i;
                } else {
                    ringI = getMeshWidth() - i;
                }
                if(j < getMeshHeight()/2) {
                    ringJ = j;
                } else {
                    ringJ = getMeshHeight() - j;
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
        for(int i = 0; i < getMeshWidth(); i++) {
            for(int j = 0; j < getMeshHeight(); j++) {
                mesh[i][j].setMass(distributeIndex(mass, i, j));
            }
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void generateDecay() {
        for(int i = 0; i < getMeshWidth(); i++) {
            for(int j = 0; j < getMeshHeight(); j++) {
                mesh[i][j].setMass(distributeIndex(decay, i, j));
            }
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void formStrength(Distribution strength) {
        // TODO
    }
    
}