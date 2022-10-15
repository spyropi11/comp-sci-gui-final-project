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
        
        double amplitude = 15;
        int iShift = 200/2;
        double spread = 4; 
        
        //Create points
        
        //!remember to set the size to a variable
        Point[] points = new Point[200];
        for(int i = 0; i < points.length; i++) {
            if(i==0 || i==(points.length-1)) {
                //This puts two points on the edges and sets their onEdge value to true
                points[i] = new Point(2, 8, 0, NATURAL_MASS, true);
            }
            
            /*
            // plucking points
            else if(i==98 || i==101) {
                points[i] = new Point(2, 8, 20, NATURAL_MASS, false);
            }
            
            //plucking points
            else if(i==99 || i==100) {
                points[i] = new Point(2, 8, 40, NATURAL_MASS, false);
            }
            */
            
            //Gausian initial condition (bell curve type shape)
            else{
                
                points[i] = new Point(2,8,(amplitude*Math.exp(((-1)*Math.pow((i - iShift), 2))/spread)),NATURAL_MASS, false);
            }
            //points[i].setMass(NATURAL_MASS*(1+i/50));
        }
        
        //Add points to mesh
        physics.getDrummer().addToMesh(points);
        
        //Set x coordinates
        for(int i = 0; i < points.length; i++) {
            //sets up points every 2 units (not sure if its pixels or not)
            points[i].setup(i*2 + 25);
        }
        
        //Create springs
        Spring[] springs = new Spring[points.length];
        for(int i = 1; i < points.length; i++) {
            springs[i] = new Spring(points[i], points[i-1], 2*NATURAL_SPRING_CONSTANT);
        }
        
        //Add springs to drum
        physics.getDrummer().addSprings(springs);
        
        
        Group group = new Group();
        
        for(Point point : points) {
            group.getChildren().add(point);
        }
        
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