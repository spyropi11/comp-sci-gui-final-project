package edu.vanier.template.drumshapes;

import edu.vanier.template.elements.Point;
import edu.vanier.template.elements.Spring;
import static edu.vanier.template.simulation.Simulation.RADIUS;
import static edu.vanier.template.simulation.Simulation.NATURAL_MASS;
import java.util.ArrayList;

public class TrapezoidDrum extends Formable {
    
    private final int baseOne;
    private final int baseTwo;
    private final int heightSide;
    private final int angle;
    
    public TrapezoidDrum(int baseOne, int baseTwo, int heightSide, int angle) throws ArithmeticException {
        this.baseOne = baseOne;
        this.baseTwo = baseTwo;
        this.heightSide = heightSide;
        this.angle = angle;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void generateMesh() {
        Point[][] points = new Point[baseOne][heightSide];
        for (int j = 0; j < heightSide; j++){
            for(int i = 0; i < baseOne; i++) {
                if(j == 0 || j == heightSide - 1) {
                    //This puts two points on the edges and sets their onEdge value to true
                    points[i][j] = new Point(RADIUS, 0, NATURAL_MASS);
                    points[i][j].setOnEdge(true);
                } else if(i == 0 || i == baseOne-1) {
                    //This puts two points on the edges and sets their onEdge value to true
                    points[i][j] = new Point(RADIUS, 0, NATURAL_MASS);
                    points[i][j].setOnEdge(true);
                    
                } else{
                    points[i][j] = new Point(RADIUS, 0,NATURAL_MASS);
                    points[i][j].setOnEdge(false);
                }
                double staircase = 2*RADIUS*j/Math.tan(Math.toRadians(angle));
                double changingLength = (baseOne-1) + j*(baseTwo-baseOne)/(heightSide-1);
                double shift = 2*RADIUS*i*changingLength/(baseOne-1);
                points[i][j].setup(staircase + shift, 2*RADIUS*j);
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
        for(int i = 0; i < baseOne; i++){
            for(int j = 0; j < heightSide; j++){
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
        return baseOne*heightSide;
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
                return distribution.getStops()[0] + i*(distribution.getStops()[1] - distribution.getStops()[0])/(baseOne - 1);
            }
            case VERTICAL_GRADIENT -> {
                return distribution.getStops()[0] + j*(distribution.getStops()[1] - distribution.getStops()[0])/(heightSide - 1);
            }
            case RADIAL_GRADIENT -> {
                int ringI;
                int ringJ;
                int ringTotal = baseOne < heightSide ? (int)Math.ceil(baseOne/2) : (int)Math.ceil(heightSide/2);
                if(i < baseOne/2) {
                    ringI = i;
                } else {
                    ringI = baseOne - i;
                }
                if(j < heightSide/2) {
                    ringJ = j;
                } else {
                    ringJ = heightSide - j;
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
        for(int i = 0; i < baseOne; i++) {
            for(int j = 0; j < heightSide; j++) {
                mesh[i][j].setMass(distributeIndex(mass, i, j));
            }
        }
    }

    public int getBaseOne() {
        return baseOne;
    }
    
    public int getBaseTwo() {
        return baseTwo;
    }
    
    public int getHeightSide() {
        return heightSide;
    }
    
    public int getAngle() {
        return angle;
    }
    
}