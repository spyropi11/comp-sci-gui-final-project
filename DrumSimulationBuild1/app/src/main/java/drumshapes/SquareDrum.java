package drumshapes;

import edu.vanier.template.elements.Point;
import edu.vanier.template.elements.Spring;
import static edu.vanier.template.simulation.Simulation.NATURAL_MASS;
import static edu.vanier.template.simulation.Simulation.NATURAL_SPRING_CONSTANT;
import static edu.vanier.template.simulation.Simulation.RADIUS;
import java.util.ArrayList;

public class SquareDrum extends Formable {
    
    private final int side;
    
    public SquareDrum(int side) {
        this.side = side;
    }
    
    private int getMeshSide() {
        return (int)Math.floor(density*side);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public Point[][] formMesh() {
        Point[][] points = new Point[getMeshSide()][getMeshSide()];
        for (int j = 0; j < getMeshSide(); j++){
            for(int i = 0; i < getMeshSide(); i++) {
                if(j == 0 || j == getMeshSide() - 1) {
                    //This puts two points on the edges and sets their onEdge value to true
                    points[i][j] = new Point(RADIUS, 1, 0, NATURAL_MASS);
                    points[i][j].setOnEdge(true);
                } else if(i == 0 || i == getMeshSide()-1) {
                    //This puts two points on the edges and sets their onEdge value to true
                    points[i][j] = new Point(RADIUS, 1, 0, NATURAL_MASS);
                    points[i][j].setOnEdge(true);
                    
                } else{
                    points[i][j] = new Point(RADIUS, 1, 0,NATURAL_MASS);
                    points[i][j].setOnEdge(false);
                }
                points[i][j].setup(2*RADIUS*i, 2*RADIUS*j);
            }
        }
        this.mesh = points;
        return points;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public ArrayList<Spring> formDrum() {
        ArrayList<Spring> springs = new ArrayList<>();
        for(int i = 0; i < getMeshSide(); i++){
            for(int j = 0; j < getMeshSide()-1; j++){
                try {
                    springs.add(new Spring(mesh[i][j], mesh[i][j+1],2*NATURAL_SPRING_CONSTANT));
                } catch(ArrayIndexOutOfBoundsException e) {}
                try {
                    springs.add(new Spring(mesh[i][j], mesh[i][j-1],2*NATURAL_SPRING_CONSTANT));
                } catch(ArrayIndexOutOfBoundsException e) {}
                try {
                    springs.add(new Spring(mesh[i][j], mesh[i+1][j],2*NATURAL_SPRING_CONSTANT));
                } catch(ArrayIndexOutOfBoundsException e) {}
                try {
                    springs.add(new Spring(mesh[i][j], mesh[i-1][j],2*NATURAL_SPRING_CONSTANT));
                } catch(ArrayIndexOutOfBoundsException e) {}
            }
        }
        this.drum = springs;
        return springs;
    }
    /**
     * {@inheritDoc}
     */
    @Override
    protected int particleCount(double density) {
        return ((int)Math.floor(density*side))^2;
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
                return distribution.getStops()[0] + i*(distribution.getStops()[1] - distribution.getStops()[0])/(getMeshSide() - 1);
            }
            case VERTICAL_GRADIENT -> {
                return distribution.getStops()[0] + j*(distribution.getStops()[1] - distribution.getStops()[0])/(getMeshSide() - 1);
            }
            case RADIAL_GRADIENT -> {
                int ring;
                int ringTotal = (int)Math.ceil(getMeshSide()/2);
                if(i < getMeshSide()/2) {
                    ring = i;
                } else {
                    ring = getMeshSide() - i;
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
    public void formMass(Distribution mass) {
        for(int i = 0; i < getMeshSide(); i++) {
            for(int j = 0; j < getMeshSide(); j++) {
                mesh[i][j].setMass(distributeIndex(mass, i, j));
            }
        }
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void formDecay(Distribution decay) {
        for(int i = 0; i < getMeshSide(); i++) {
            for(int j = 0; j < getMeshSide(); j++) {
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