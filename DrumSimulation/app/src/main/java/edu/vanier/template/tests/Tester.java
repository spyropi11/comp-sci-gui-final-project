package edu.vanier.template.tests;

import edu.vanier.template.elements.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This is a JavaFX project template to be used for creating GUI applications.
 * JavaFX 18 is already linked to this project in the build.gradle file.
 * @link: https://openjfx.io/javadoc/18/
 * @see: Build Scripts/build.gradle
 * @author Sleiman Rabah.
 */
public class Tester extends Application {

    private final Physics physics = new Physics();
    
    /**
    * When defining the spring constant, we can make it a multiple of this constant.
    * For example, if the animation only looks good when k is in the 1000s, then we can make this constant = 1000.
    * Or, if the animation only looks good when k is 0.0001, 0.00005, 0.0002, etc., we can make this constant = 0.00001.
    */
    private final double NATURAL_SPRING_CONSTANT = 1;
    /**
    * When defining the mass, we can make it a multiple of this constant.
    * For example, if the animation only looks good when m is in the 1000s, then we can make this constant = 1000.
    * Or, if the animation only looks good when m is 0.0001, 0.0005, 0.0002, etc., we can make this constant = 0.0001.
    */
    private final double NATURAL_MASS = 1;
    
    /**
     * @param stage
     * 
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        double WIDTH = 700;
        double HEIGHT = 700;
        
        double amplitude = 100;
        int iShift = 200/2;
        double spread = 200; 
        
        //Create points
        
        //Add points to mesh
        
        //Set x and y coordinates
        
        //Create springs
        
        //Add springs to drum
        
        
        Group group = new Group();
        
        Scene scene = new Scene(group, WIDTH,HEIGHT);

        scene.setFill(Color.AZURE);
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            
            switch(event.getCode()){
                
                case W:
                    physics.translate(0, 10);
                    break;
                    
                case S:
                    physics.translate(0, -10);
                    break;
                    
                case D:
                    physics.translate(10, 0);
                    break;
                    
                case A:
                    physics.translate(-10, 0);
                    break;
                    
                case J:
                    physics.zoom(5, 5);
                    break;
                    
                case K:
                    physics.zoom(0.2, 0.2);
                    break;
                    
                case M:
                    physics.rotate(-0.1, Physics.Axis.N);
                    break;
                    
                case N:
                    physics.rotate(0.1, Physics.Axis.N);
                    break;
                    
                case B:
                    physics.rotate(0.1, Physics.Axis.BETA);
                    break;
                    
                case V:
                    physics.rotate(-0.1, Physics.Axis.BETA);
                    break;
                    
                case C:
                    physics.rotate(0.1, Physics.Axis.ALPHA);
                    break;
                    
                case X:
                    physics.rotate(-0.1, Physics.Axis.ALPHA);
                    
            }
            
        });
        
        stage.setTitle("Drum Sim 1D");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        
        physics.startTimer();
        
        stage.setOnCloseRequest((WindowEvent windowEvent) -> {
            physics.stopTimer();
            Platform.exit();
        });
        
    }
    
    public static void main(String[] args) {
        
        launch(args);
        
    }
    
}