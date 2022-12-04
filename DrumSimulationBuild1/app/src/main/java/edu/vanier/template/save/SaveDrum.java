package edu.vanier.template.save;

import edu.vanier.template.drumshapes.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class SaveDrum {
    
    private final File drumFile;
    
    private Formable formable;
    
    public SaveDrum(File drumFile) {
        this.drumFile = drumFile;
    }
    
    public void download() throws IOException {
        try(FileReader fr = new FileReader(drumFile)) {
            char shape = (char)fr.read();
            switch(shape) {
                case '1' -> {
                    String side = "";
                    boolean cont = true;
                    do {
                        char next = (char)fr.read();
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
                case '2' -> {
                    String width = "";
                    String height = "";
                    boolean cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            width += next;
                        }
                    } while(cont);
                    cont = true;
                    do {
                        char next = (char)fr.read();
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
                case '3' -> {
                    String widthSide = "";
                    String heightSide = "";
                    String angle = "";
                    boolean cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            widthSide += next;
                        }
                    } while(cont);
                    cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            heightSide += next;
                        }
                    } while(cont);
                    cont = true;
                    do {
                        char next = (char)fr.read();
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
                case '4' -> {
                    String baseOne = "";
                    String baseTwo = "";
                    String heightSide = "";
                    String angle = "";
                    boolean cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            baseOne += next;
                        }
                    } while(cont);
                    cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            baseTwo += next;
                        }
                    } while(cont);
                    cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            heightSide += next;
                        }
                    } while(cont);
                    cont = true;
                    do {
                        char next = (char)fr.read();
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
            char texture = (char)fr.read();
            switch(texture) {
                case '1' -> {
                    formable.setArrangement(Formable.Arrangement.CARTESIAN);
                }
                case '2' -> {
                    formable.setArrangement(Formable.Arrangement.PARALLEL);
                }
                case '3' -> {
                    formable.setArrangement(Formable.Arrangement.TRIANGULAR);
                }
                case '4' -> {
                    formable.setArrangement(Formable.Arrangement.CROSSED_THIN);
                }
                case '5' -> {
                    formable.setArrangement(Formable.Arrangement.CROSSED_THICK);
                }
            }
            char surface = (char)fr.read();
            switch(surface) {
                case '1' -> {
                    String value = "";
                    boolean cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            value += next;
                        }
                    } while(cont);
                    double[] stops = {Double.parseDouble(value)};
                    formable.setMassDistribution(new Distribution(Distribution.Surface.UNIFORM, stops));
                }
                case '2' -> {
                    String value1 = "";
                    boolean cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            value1 += next;
                        }
                    } while(cont);
                    String value2 = "";
                    cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            value2 += next;
                        }
                    } while(cont);
                    double[] stops = {Double.parseDouble(value1), Double.parseDouble(value2)};
                    formable.setMassDistribution(new Distribution(Distribution.Surface.HORIZONTAL_GRADIENT, stops));
                }
                case '3' -> {
                    String value1 = "";
                    boolean cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            value1 += next;
                        }
                    } while(cont);
                    String value2 = "";
                    cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            value2 += next;
                        }
                    } while(cont);
                    double[] stops = {Double.parseDouble(value1), Double.parseDouble(value2)};
                    formable.setMassDistribution(new Distribution(Distribution.Surface.VERTICAL_GRADIENT, stops));
                }
                case '4' -> {
                    String value1 = "";
                    boolean cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            value1 += next;
                        }
                    } while(cont);
                    String value2 = "";
                    cont = true;
                    do {
                        char next = (char)fr.read();
                        if(next == ' ') {
                            cont = false;
                        } else {
                            value2 += next;
                        }
                    } while(cont);
                    double[] stops = {Double.parseDouble(value1), Double.parseDouble(value2)};
                    formable.setMassDistribution(new Distribution(Distribution.Surface.RADIAL_GRADIENT, stops));
                }
            }
        }
        if(Objects.isNull(formable)) {
            throw new IOException("No shape parsed.");
        }
    }
    
    public void upload(Formable formable) throws IOException {
        this.formable = formable;
        try(FileWriter fw = new FileWriter(drumFile)) {
            if(formable instanceof SquareDrum square) {
                fw.append('1')
                  .append(Integer.toString(square.getSide()))
                  .append(' ');
            } else if(formable instanceof RectangleDrum rectangle) {
                fw.append('2')
                  .append(Integer.toString(rectangle.getWidth()))
                  .append(' ')
                  .append(Integer.toString(rectangle.getHeight()))
                  .append(' ');
            } else if(formable instanceof ParallelogramDrum parallelogram) {
                fw.append('3')
                  .append(Integer.toString(parallelogram.getWidthSide()))
                  .append(' ')
                  .append(Integer.toString(parallelogram.getHeightSide()))
                  .append(' ')
                  .append(Integer.toString(parallelogram.getAngle()))
                  .append(' ');
            } else if(formable instanceof TrapezoidDrum trapezoid) {
                fw.append('4')
                  .append(Integer.toString(trapezoid.getBaseOne()))
                  .append(' ')
                  .append(Integer.toString(trapezoid.getBaseTwo()))
                  .append(' ')
                  .append(Integer.toString(trapezoid.getHeightSide()))
                  .append(' ')
                  .append(Integer.toString(trapezoid.getAngle()))
                  .append(' ');
            } else {
                throw new IOException("Formable is not a shape.");
            }
            switch(formable.getTexture()) {
                case CARTESIAN -> {
                    fw.append('1');
                }
                case PARALLEL -> {
                    fw.append('2');
                }
                case TRIANGULAR -> {
                    fw.append('3');
                }
                case CROSSED_THIN -> {
                    fw.append('4');
                }
                case CROSSED_THICK -> {
                    fw.append('5');
                }
            }
            switch(formable.getMass().getSurface()) {
                case UNIFORM -> {
                    fw.append('1')
                      .append(Double.toString(formable.getMass().getStops()[0]))
                      .append(' ');
                }
                case HORIZONTAL_GRADIENT -> {
                    fw.append('2')
                      .append(Double.toString(formable.getMass().getStops()[0]))
                      .append(' ')
                      .append(Double.toString(formable.getMass().getStops()[1]))
                      .append(' ');
                }
                case VERTICAL_GRADIENT -> {
                    fw.append('3')
                      .append(Double.toString(formable.getMass().getStops()[0]))
                      .append(' ')
                      .append(Double.toString(formable.getMass().getStops()[1]))
                      .append(' ');
                }
                case RADIAL_GRADIENT -> {
                    fw.append('4')
                      .append(Double.toString(formable.getMass().getStops()[0]))
                      .append(' ')
                      .append(Double.toString(formable.getMass().getStops()[1]))
                      .append(' ');
                }
            }
            fw.flush();
        }
    }
    
}