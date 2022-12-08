package edu.vanier.template.drumshapes;

import edu.vanier.template.drumshapes.ConstructFormable.Shape;
import edu.vanier.template.elements.Point;
import edu.vanier.template.elements.Spring;
import edu.vanier.template.simulation.Simulation;
import java.util.ArrayList;

/**
 * The Formable abstract class represents an arbitrary shape the drum can take on, along with properties and methods used to
 * generate the points and springs in the drum, in a particular arrangement.
 */
public abstract class Formable {
    /**
     * Constant denoting the maximum number of points possible that the application can handle.
     */
    private static final int particleCountCap = 10000;
    /**
     * The arrangement of springs in the drum.
     */
    protected Arrangement texture = Arrangement.CARTESIAN;
    /**
     * The mesh created by this formable.
     */
    protected Point[][] mesh;
    /**
     * The drum created by this formable.
     */
    protected ArrayList<Spring> drum;
    /**
     * The mass distribution of this mesh.
     */
    protected Distribution mass;

    /**
     * Possibilities for the arrangement of springs in a drum.
     */
    public static enum Arrangement {
        CARTESIAN,
        PARALLEL,
        TRIANGULAR,
        CROSSED_THIN,
        CROSSED_THICK
    }
    /**
     * Forms a shape for the drum with a default mass distribution.
     */
    public Formable() {
        double[] defaultMass = {Simulation.NATURAL_MASS};
        mass = new Distribution(Distribution.Surface.UNIFORM, defaultMass);
    }
    /**
     * Forms a shape from a save file.
     * @param form
     * @return A formable
     */
    public static Formable construct(ConstructFormable form) {
        Formable construct = null;
        switch(form.shape) {
            case SQUARE -> {
                construct = new SquareDrum(form.parameters[0]);
            }
            case RECTANGLE -> {
                construct = new RectangleDrum(form.parameters[0], form.parameters[1]);
            }
            case PARALLELOGRAM -> {
                construct = new ParallelogramDrum(form.parameters[0], form.parameters[1], form.parameters[2]);
            }
            case TRAPEZOID -> {
                construct = new TrapezoidDrum(form.parameters[0], form.parameters[1], form.parameters[2], form.parameters[3]);
            }
        }
        construct.setArrangement(form.texture);
        construct.setMassDistribution(new Distribution(form.mass, form.stops));
        return construct;
    }
    /**
     * Creates a ConstructFormable equivalent of this instance.
     * @return 
     */
    public ConstructFormable deconstruct() {
        Shape shape;
        int[] parameters;
        if(this instanceof SquareDrum square) {
            shape = Shape.SQUARE;
            parameters = new int[1];
            parameters[0] = square.getSide();
        } else if(this instanceof RectangleDrum rectangle) {
            shape = Shape.RECTANGLE;
            parameters = new int[2];
            parameters[0] = rectangle.getWidth();
            parameters[1] = rectangle.getHeight();
        } else if(this instanceof ParallelogramDrum parallelogram) {
            shape = Shape.PARALLELOGRAM;
            parameters = new int[3];
            parameters[0] = parallelogram.getWidthSide();
            parameters[1] = parallelogram.getHeightSide();
            parameters[2] = parallelogram.getAngle();
        } else if(this instanceof TrapezoidDrum trapezoid) {
            shape = Shape.TRAPEZOID;
            parameters = new int[4];
            parameters[0] = trapezoid.getBaseOne();
            parameters[1] = trapezoid.getBaseTwo();
            parameters[2] = trapezoid.getHeightSide();
            parameters[3] = trapezoid.getAngle();
        } else {
            return null;
        }
        return new ConstructFormable(shape, parameters, texture, mass.getSurface(), mass.getStops());
    }
    /**
     * Forms a mesh of points.
     * @return
     */
    public Point[][] formMesh() {
        generateMesh();
        generateMass();
        return mesh;
    }
    /**
     * Forms a collection of springs that connect the points in the drum.
     * @return
     */
    public ArrayList<Spring> formDrum() {
        generateDrum();
        return drum;
    }
    /**
     * Generates the mesh of points in the selected shape.
     */
    protected abstract void generateMesh();
    /**
     * Generates the drum of springs in the selected shape.
     *
     */
    protected abstract void generateDrum();
    /**
     * Determines the total number of points in a mesh of given dimension.
     * @return The 'area' of the mesh.
     */
    protected abstract int particleCount();
    /**
     * Checks if the number of points in the mesh would be greater than the cap. If the particle count is too high, the animation won't run well.
     */
    public void checkCap() throws ArithmeticException {
        if(particleCount() > particleCountCap) {
            throw new ArithmeticException("Exceeded maximum point count. Please reduce the parameter values.");
        }
    }
    /**
     * Sets the distribution of mass among the points in the mesh.
     * @param mass
     */
    public void setMassDistribution(Distribution mass) {
        this.mass = mass;
    }
    /**
     * Sets the arrangement of springs in the drum.
     * @param texture
     */
    public void setArrangement(Arrangement texture) {
        this.texture = texture;
    }
    /**
     * Sets the masses of the points in the mesh.
     */
    protected abstract void generateMass();
    /**
     * Returns indices of points to be bound by springs in the arrangement of this drum's texture.
     * @param i Horizontal index of point.
     * @param j Vertical index of point.
     * @return The indices of the points to be connected.
     */
    protected int[][] bindSpring(int i, int j) {
        switch(texture) {
            case CARTESIAN -> {
                int[][] indices = {
                    {i, j + 1},
                    {i, j - 1},
                    {i + 1, j},
                    {i - 1, j}
                };
                return indices;
            }
            case PARALLEL -> {
                int[][] indices = {
                    {i + 1, j},
                    {i - 1, j},
                    {i + 1, j + 1},
                    {i - 1, j - 1}
                };
                return indices;
            }
            case TRIANGULAR -> {
                int[][] indices = {
                    {i, j + 1},
                    {i, j - 1},
                    {i + 1, j},
                    {i - 1, j},
                    {i + 1, j + 1},
                    {i - 1, j - 1}
                };
                return indices;
            }
            case CROSSED_THIN -> {
                int[][] indices = {
                    {i, j + 1},
                    {i, j - 1},
                    {i + 1, j + 1},
                    {i + 1, j - 1},
                    {i - 1, j + 1},
                    {i - 1, j - 1},
                };
                return indices;
            }
            case CROSSED_THICK -> {
                int[][] indices = {
                    {i, j + 1},
                    {i, j - 1},
                    {i + 1, j},
                    {i - 1, j},
                    {i + 1, j + 1},
                    {i + 1, j - 1},
                    {i - 1, j + 1},
                    {i - 1, j - 1},
                };
                return indices;
            }
            default -> {return null;}
        }
    }
    /**
     * @return The arrangement of springs, which affects the transmission of waves.
     */
    public Arrangement getTexture() {
        return texture;
    }
    /**
     * @return The distribution of mass amongst the points, which may be uniform or a gradient.
     */
    public Distribution getMass() {
        return mass;
    }
}