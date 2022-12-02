package edu.vanier.template.save;

import com.opencsv.bean.CsvBindByPosition;
import edu.vanier.template.elements.Physics;
import javafx.scene.input.MouseButton;
import javafx.scene.robot.Robot;

public class PulseInstance {
    
    private final Robot robot = new Robot();
    
    @CsvBindByPosition(position = 0)
    int counter;
    @CsvBindByPosition(position = 0)
    int pointI;
    @CsvBindByPosition(position = 0)
    int pointJ;
    @CsvBindByPosition(position = 0)
    double spread;
    @CsvBindByPosition(position = 0)
    double amplitude;
    
    public void createPulse(Physics physics) {
        //TODO
        try {
            robot.mouseMove(physics.getDrummer().getPoint(pointI, pointJ).getTranslateX(), physics.getDrummer().getPoint(pointI, pointJ).getTranslateX());
            robot.mouseClick(MouseButton.PRIMARY);
        } catch(NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
    
}