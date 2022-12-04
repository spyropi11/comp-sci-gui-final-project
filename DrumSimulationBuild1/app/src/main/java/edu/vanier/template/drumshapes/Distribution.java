package edu.vanier.template.drumshapes;
/**
 * The Distribution class represents a distribution of values over a set of points, and may be uniform or a gradient.
 */
public class Distribution {
    /**
     * The allowed types of distributions.
     */
    public static enum Surface {
        UNIFORM,
        HORIZONTAL_GRADIENT,
        VERTICAL_GRADIENT,
        RADIAL_GRADIENT
    }
    /**
     * The kind of distribution this is.
     */
    private final Surface surface;
    /**
     * The range of values in the distribution.
     */
    private final double[] stops;
    /**
     * Creates a distribution of values, which may be applied to a set.
     * @param surface The type of distribution.
     * @param stops The range of values.
     * @throws ArrayIndexOutOfBoundsException Other than a uniform distribution, which must be a single element, the stops must have only two values.
     */
    public Distribution(Surface surface, double... stops) throws ArrayIndexOutOfBoundsException {
        this.surface = surface;
        this.stops = stops;
        if(surface == Surface.UNIFORM && stops.length != 1) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if(surface == Surface.HORIZONTAL_GRADIENT && stops.length != 2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if(surface == Surface.VERTICAL_GRADIENT && stops.length != 2) {
            throw new ArrayIndexOutOfBoundsException();
        }
        if(surface == Surface.RADIAL_GRADIENT && stops.length != 2) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }
    /**
     * @return The type of distribution.
     */
    public Surface getSurface() {
        return surface;
    }
    /**
     * @return The range of values.
     */
    public double[] getStops() {
        return stops;
    }
}