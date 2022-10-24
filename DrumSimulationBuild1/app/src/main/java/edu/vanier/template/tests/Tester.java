package edu.vanier.template.tests;

import edu.vanier.template.elements.*;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
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
    
    public static int nOfPoints = 2500;
    public static Point[] points = new Point[nOfPoints];

    public static Point[] getPoints() {
        return points;
    }

    
    
    /**
     * @param stage
     * 
     */
    @Override
    public void start(Stage stage) throws Exception {
        
        double WIDTH = 700;
        double HEIGHT = 700;
        
        
        
        
        int counter = 0;
        
        //Create points
        
        
        
        counter = 0;
        for (int j = 0; j < Math.sqrt(nOfPoints); j++){
            
            for(int i = 0; i < Math.sqrt(nOfPoints); i++) {
                
                if(j == 0|| j == Math.sqrt(nOfPoints) - 1) {
                    //This puts two points on the edges and sets their onEdge value to true
                    points[counter] = new Point(2, 1, 0, NATURAL_MASS, true);
                    
                }
                
                else if(i==0 && j > 0 && j < Math.sqrt(nOfPoints) -1 || i==(Math.sqrt(nOfPoints)-1) && j > 0 && j < Math.sqrt(nOfPoints) -1) {
                    //This puts two points on the edges and sets their onEdge value to true
                    points[counter] = new Point(2, 1, 0, NATURAL_MASS, true);
                    
                }
                
                

                //2d Gausian initial condition (bell curve type shape) 
                //u(x,y,t=0)= Amplitude*exp(-((x+shift)^2+(y+ shift)^2)/(spread)
                else{
                    
                    
                    //points[counter] = new Point(2,1,(amplitude*Math.exp(-((Math.pow(i - shift, 2))+(Math.pow(j - shift, 2)))/spread)),NATURAL_MASS, false);
                    points[counter] = new Point(2,1,0,NATURAL_MASS, false);
                }
                
                
                
                //points[i].setMass(NATURAL_MASS*(1+i/50));
                
                counter++;
            }
            
        }
        
        
        //Add points to mesh
        physics.getDrummer().addToMesh(points);
        
        counter = 0;
        //Set x coordinates
        for(int j = 0; j < Math.sqrt(nOfPoints); j++){
            
            for(int i = 0; i < Math.sqrt(nOfPoints); i++) {
                
                //sets up points every 2 units (not sure if its pixels or not)
                points[counter].setup(i*4,j*4);
                System.out.println(counter);
                counter++;
            }
            
        }
        
        
        //Create springs
        Spring[] springs = new Spring[(99*100) + (99*100)];
        
        
        int pointCounter = 1;
        int springCounter = 0;
        for(int i = 0; i < Math.sqrt(points.length); i++){
            
            for(int j = 0; j < Math.sqrt(points.length)-1; j++){
                
                springs[springCounter] = new Spring(points[pointCounter], points[pointCounter-1],2*NATURAL_SPRING_CONSTANT);
                springCounter++;
                pointCounter++;
                
            }
            pointCounter++;
            
        }
        
        for(int i = 0; i < points.length - Math.sqrt(points.length); i++){
            
            springs[springCounter] = new Spring(points[i], points[i+ (int)Math.sqrt(points.length)], 2*NATURAL_SPRING_CONSTANT);
            springCounter++;
        }
        
        /*for(int i = 1; i < points.length; i++) {
            springs[i] = new Spring(points[i], points[i-1], 2*NATURAL_SPRING_CONSTANT);
        }*/
        
        //Add springs to drum
        physics.getDrummer().addSprings(springs);
        
        
        Group group = new Group();
        
        for(Point point : points) {
            group.getChildren().add(point);
        }
        
        Camera camera = new PerspectiveCamera(true);
        Scene scene = new Scene(group, WIDTH,HEIGHT);
        
        camera.translateZProperty().set(-700);
        camera.translateXProperty().set(150);
        camera.translateYProperty().set(-350);
        
        Rotate rotateDown = new Rotate(-20);
        rotateDown.setAxis(Rotate.X_AXIS);
        camera.getTransforms().add(rotateDown);
        
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
                    
                case T:
                    Rotate rotateTransformUp = new Rotate(-10, 0, 0, 0);
                    rotateTransformUp.setAxis(Rotate.X_AXIS);
                    camera.getTransforms().add(rotateTransformUp);
                    break;
                
                case G:
                    Rotate rotateTransformDown = new Rotate(10, 0, 0, 0);
                    rotateTransformDown.setAxis(Rotate.X_AXIS);
                    camera.getTransforms().add(rotateTransformDown);
                    break;
                
            }
            
        });
        
        
        
        
        
        stage.setTitle("Drum Sim 2D");
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