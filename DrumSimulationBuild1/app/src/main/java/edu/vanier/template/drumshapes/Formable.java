package edu.vanier.template.drumshapes;

import edu.vanier.template.elements.Point;
import edu.vanier.template.elements.Spring;
import java.util.ArrayList;

public abstract class Formable {

    /**
     * Constant denoting the maximum number of points possible that the application can handle.
     */
    public static final int particleCountCap = 10000;
    /**
     * Density of points in the mesh.
     */
    protected double density = 1;
    /**
     * The arrangement of springs in the drum.
     */
    protected Arrangement texture;
    /**
     * The mesh created by this formable.
     */
    protected Point[][] mesh;
    /**
     * The drum created by this formable.
     */
    protected ArrayList<Spring> drum;
    /**
     * Possibilities for the arrangement of springs in a drum.
     */
    public enum Arrangement {
        CARTESIAN,
        CROSSED,
        CROSSED_INDEPENDENT,
        HORIZONTAL_INDEPENDENT,
        VERTICAL_INDEPENDENT
    }
    /**
     * Creates a mesh of points in the selected shape.
     * @return An array of points in the mesh.
     */
    public abstract Point[][] formMesh();
    /**
     * Creates a drum of springs in the selected shape.
     * @return An array of springs in the drum.
     */
    public abstract ArrayList<Spring> formDrum();
    /**
     * Determines the total number of points in a mesh of given density.
     * @param density The density of the mesh.
     * @return The 'area' of the mesh.
     */
    protected abstract int particleCount(double density);
    /**
     * Sets the density of the mesh, if possible.
     * @param density The desired density of the mesh.
     * @throws ArithmeticException Throws if the density creates too many points for the application to handle.
     */
    public void setDensity(double density) throws ArithmeticException {
        if(particleCount(density) > particleCountCap) {
            throw new ArithmeticException("Exceeded maximum point count. Reduce density or other parameters.");
        }
        this.density = density;
    }
    /**
     * Sets the masses of the points in the mesh.
     * @param mass The particular distribution of masses.
     */
    public abstract void formMass(Distribution mass);
    /**
     * Sets the decay constants of the points in the mesh.
     * @param decay The particular distribution of decays decay constants.
     */
    public abstract void formDecay(Distribution decay);
    /**
     * Sets the spring constants of the springs in the drum.
     * @param strength The particular distribution of spring constants.
     */
    public abstract void formStrength(Distribution strength);
}