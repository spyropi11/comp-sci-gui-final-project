package edu.vanier.template.save;

import edu.vanier.template.drumshapes.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

public class SaveDrum {
    
    public Formable download(String filePath) throws IOException {
        Formable formable = null;
        File file = new File(filePath);
        try(FileReader fr = new FileReader(file)) {
            int shape = fr.read();
            switch(shape) {
                case 1 -> {
                    String side = "";
                    boolean cont = true;
                    do {
                        int next = fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            side += next;
                        }
                    } while(cont);
                    try {
                        formable = new SquareDrum(Integer.parseInt(side));
                    } catch(NumberFormatException e) {
                        throw new NumberFormatException("Side length is invalid.");
                    }
                }
                case 2 -> {
                    String width = "";
                    String height = "";
                    boolean cont = true;
                    do {
                        int next = fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            width += next;
                        }
                    } while(cont);
                    cont = true;
                    do {
                        int next = fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            height += next;
                        }
                    } while(cont);
                    try {
                        formable = new RectangleDrum(Integer.parseInt(width), Integer.parseInt(height));
                    } catch(NumberFormatException e) {
                        throw new NumberFormatException("Width or height is invalid.");
                    }
                }
                case 3 -> {
                    String widthSide = "";
                    String heightSide = "";
                    String angle = "";
                    boolean cont = true;
                    do {
                        int next = fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            widthSide += next;
                        }
                    } while(cont);
                    cont = true;
                    do {
                        int next = fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            heightSide += next;
                        }
                    } while(cont);
                    cont = true;
                    do {
                        int next = fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            angle += next;
                        }
                    } while(cont);
                    try {
                        formable = new ParallelogramDrum(Integer.parseInt(widthSide), Integer.parseInt(heightSide), Integer.parseInt(angle));
                    } catch(NumberFormatException e) {
                        throw new NumberFormatException("Width, height, or angle is invalid.");
                    }
                }
                case 4 -> {
                    String baseOne = "";
                    String baseTwo = "";
                    String heightSide = "";
                    String angle = "";
                    boolean cont = true;
                    do {
                        int next = fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            baseOne += next;
                        }
                    } while(cont);
                    cont = true;
                    do {
                        int next = fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            baseTwo += next;
                        }
                    } while(cont);
                    cont = true;
                    do {
                        int next = fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            heightSide += next;
                        }
                    } while(cont);
                    cont = true;
                    do {
                        int next = fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            angle += next;
                        }
                    } while(cont);
                    try {
                        formable = new TrapezoidDrum(Integer.parseInt(baseOne), Integer.parseInt(baseTwo), Integer.parseInt(heightSide), Integer.parseInt(angle));
                    } catch(NumberFormatException e) {
                        throw new NumberFormatException("Base one, base two, height, or angle is invalid.");
                    }
                }
            }
            boolean cont = true;
            String density = "";
            do {
                int next = fr.read();
                if(next == ' ') {
                    cont = false;
                } else {
                    density += next;
                }
            } while(cont);
            formable.setDensity(Double.parseDouble(density));
            int arrangement = fr.read();
            switch(arrangement) {
                case 1 -> {
                    formable.setArrangement(Formable.Arrangement.CARTESIAN);
                }
                case 2 -> {
                    formable.setArrangement(Formable.Arrangement.PARALLEL);
                }
                case 3 -> {
                    formable.setArrangement(Formable.Arrangement.TRIANGULAR);
                }
                case 4 -> {
                    formable.setArrangement(Formable.Arrangement.CROSSED_THIN);
                }
                case 5 -> {
                    formable.setArrangement(Formable.Arrangement.CROSSED_THICK);
                }
            }
            int surface = fr.read();
            switch(surface) {
                case 1 -> {
                    String value = "" + fr.read();
                    double[] stops = {Double.parseDouble(value)};
                    formable.setMassDistribution(new Distribution(Distribution.Surface.UNIFORM, stops));
                }
                case 2 -> {
                    String value1 = "" + fr.read();
                    String value2 = "" + fr.read();
                    double[] stops = {Double.parseDouble(value1), Double.parseDouble(value2)};
                    formable.setMassDistribution(new Distribution(Distribution.Surface.HORIZONTAL_GRADIENT, stops));
                }
                case 3 -> {
                    String value1 = "" + fr.read();
                    String value2 = "" + fr.read();
                    double[] stops = {Double.parseDouble(value1), Double.parseDouble(value2)};
                    formable.setMassDistribution(new Distribution(Distribution.Surface.VERTICAL_GRADIENT, stops));
                }
                case 4 -> {
                    String value1 = "" + fr.read();
                    String value2 = "" + fr.read();
                    double[] stops = {Double.parseDouble(value1), Double.parseDouble(value2)};
                    formable.setMassDistribution(new Distribution(Distribution.Surface.RADIAL_GRADIENT, stops));
                }
            }
        }
        if(!Objects.isNull(formable)) {
            return formable;
        }
        throw new IOException("No shape parsed.");
    }
    
}