package drumshapes;

public class Distribution {
    
    public static enum Surface {
        UNIFORM,
        HORIZONTAL_GRADIENT,
        VERTICAL_GRADIENT,
        RADIAL_GRADIENT
    }
    
    private final Surface surface;
    
    private final double[] stops;
    
    public Distribution(Surface surface, double[] stops) throws ArrayIndexOutOfBoundsException {
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
    
    public Surface getSurface() {
        return surface;
    }
    
    public double[] getStops() {
        return stops;
    }
    
}