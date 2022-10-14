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
     * @param stage
     * 
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        double WIDTH = 700;
        double HEIGHT = 700;
        
        //Create points
        Point p1 = new Point(50,10,0,5,true);
        Point p2 = new Point(50,10,100,5,false);
        Point p3 = new Point(50,10,0,5,false);
        Point p4 = new Point(50,10,0,5,false);
        Point p5 = new Point(50,10,0,5,true);
        
        //Add points to mesh
        physics.getDrummer().addToMesh(p1,p2,p3,p4,p5);
        
        //Create springs
        Spring s12 = new Spring(p1,p2,2*NATURAL_SPRING_CONSTANT);
        Spring s23 = new Spring(p2,p3,2*NATURAL_SPRING_CONSTANT);
        Spring s34 = new Spring(p3,p4,2*NATURAL_SPRING_CONSTANT);
        Spring s45 = new Spring(p4,p5,2*NATURAL_SPRING_CONSTANT);
        
        //Add springs to drum
        physics.getDrummer().addSprings(s12, s23, s34, s45);
        
        //Set x coordinates
        p1.setup(50);
        p2.setup(150);
        p3.setup(250);
        p4.setup(350);
        p5.setup(450);
        
        Group group = new Group();
        
        group.getChildren().add(p1);
        group.getChildren().add(p2);
        group.getChildren().add(p3);
        group.getChildren().add(p4);
        group.getChildren().add(p5);
        
        Camera camera = new PerspectiveCamera(true);
        Scene scene = new Scene(group, WIDTH,HEIGHT);

        camera.translateZProperty().set(-1500);
        camera.translateXProperty().set(250);
        
        camera.setNearClip(1);
        camera.setFarClip(10000);
        
        scene.setCamera(camera);
        scene.setFill(Color.AZURE);
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            
            switch(event.getCode()){
                
                case W: 
                    camera.translateZProperty().set(camera.getTranslateZ() + 100);
                    break;
                    
                case S:
                    camera.translateZProperty().set(camera.getTranslateZ() - 100);
                    break;
                    
                case D:
                    camera.translateXProperty().set(camera.getTranslateX() + 25);
                    break;
                    
                case A:
                    camera.translateXProperty().set(camera.getTranslateX() - 25);
                    break;
                    
                case R:
                    camera.translateYProperty().set(camera.getTranslateY() - 25);
                    break;
                    
                case F:
                    camera.translateYProperty().set(camera.getTranslateY() + 25);
                    break;
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