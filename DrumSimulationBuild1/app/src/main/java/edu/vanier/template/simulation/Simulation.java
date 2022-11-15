package edu.vanier.template.simulation;

import edu.vanier.template.drumshapes.Formable;
import edu.vanier.template.elements.*;
import edu.vanier.template.linear.CameraLine;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Simulation {

    /**
    * Binds a Physics object to this Simulation object.
    */
    private final Physics physics;
    /**
    * When defining the spring constant, we can make it a multiple of this constant.
    * For example, if the animation only looks good when k is in the 1000s, then we can make this constant = 1000.
    * Or, if the animation only looks good when k is 0.0001, 0.00005, 0.0002, etc., we can make this constant = 0.00001.
    */
    public final static double NATURAL_SPRING_CONSTANT = 1;
    /**
    * When defining the mass, we can make it a multiple of this constant.
    * For example, if the animation only looks good when m is in the 1000s, then we can make this constant = 1000.
    * Or, if the animation only looks good when m is 0.0001, 0.0005, 0.0002, etc., we can make this constant = 0.0001.
    */
    public final static double NATURAL_MASS = 1;
    /**
    * When defining the decay, we can make it a multiple of this constant.
    * For example, if the animation only looks good when decay is in the 1000s, then we can make this constant = 1000.
    * Or, if the animation only looks good when decay is 0.0001, 0.0005, 0.0002, etc., we can make this constant = 0.0001.
    */
    public final static double NATURAL_DECAY = 0.1;
    /**
    * Root node of scene.
    */
    private final Pane root;
    /**
    * Line that makes orientation of the drum when displayed clearer.
    */
    private final CameraLine cameraLine;
    /**
    * Length of the camera line.
    */
    public final static double CAMERA_LINE_LENGTH = 15;
    /**
    * Maximum length of the camera line.
    */
    public final static double CAMERA_LINE_LIMIT = 30;
    /**
    * Constant involved in calculating length of camera line when displayed on screen.
    */
    public final static double CAMERA_LINE_DIST = 0.02;
    /**
     * Boolean denoting when the camera line is displayed.
     */
    private boolean display = true;
    
    /**
    * Width of drum.
    */
    private double WIDTH;
    private double HEIGHT;
    public final static double RADIUS = 2;
    
    
    //These are number of points, not pixels.
    public int MESH_WIDTH = 50;
    public int MESH_HEIGHT = 20;
    
    /**
     * @param stage The stage of the simulation
     * @param formable 
     * 
     */
    public Simulation(Stage stage, Formable formable) {
        // We'll have to eventually get rid of this stage parameter and instead of displaying the sim on a stage, we attach the pane used onto another stage with all the ui controls.
        
        WIDTH = 700;
        HEIGHT = 700;
        
        root = new Pane();
        root.setPrefWidth(700);
        root.setPrefHeight(700);
        physics = new Physics(this);
        
        Point[][] points = formable.formMesh();
        ArrayList<Spring> drum = formable.formDrum();
        
        physics.setPoints(points);
        physics.getDrummer().addToMesh(points);
        for(Spring spring : drum) {
            physics.getDrummer().addSprings(spring);
        }
        for(Point[] pointList : points) {
            root.getChildren().addAll(Arrays.asList(pointList));
            for(Point point : pointList) {
                point.setup(point.getX() + WIDTH/2, point.getY() + HEIGHT/2);
            }
        }
        
        cameraLine = new CameraLine(physics);
        
        Scene scene = new Scene(root, WIDTH,HEIGHT);
        cameraLine.setStrokeWidth(1);
        root.getChildren().add(cameraLine);
        scene.setFill(Color.AZURE);
        
        //Set camera and camera origin.
        double oX = root.getPrefWidth()/2;
        double oY = root.getPrefHeight()/2;
        physics.setOrigin(oX, oY, 0);
        physics.setCameraCentre(oX, oY);
        Sphere cameraCentre = new Sphere(4, 4);
        cameraCentre.setTranslateX(oX);
        cameraCentre.setTranslateY(oY);
        PhongMaterial cameraMaterial = new PhongMaterial();
        cameraMaterial.setSpecularColor(Color.YELLOW);
        cameraMaterial.setDiffuseColor(Color.YELLOW);
        cameraCentre.setMaterial(cameraMaterial);
        root.getChildren().add(cameraCentre);
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            
            switch(event.getCode()){
                
                case W -> physics.translate(0, 3);
                    
                case S -> physics.translate(0, -3);
                    
                case D -> physics.translate(-3, 0);
                    
                case A -> physics.translate(3, 0);
                    
                case J -> physics.zoom(1.01);
                    
                case K -> physics.zoom(0.99);
                    
                case M -> physics.rotate(-0.01, Physics.Axis.N);
                    
                case N -> physics.rotate(0.01, Physics.Axis.N);
                    
                case B -> physics.rotate(0.02, Physics.Axis.BETA);
                    
                case V -> physics.rotate(-0.02, Physics.Axis.BETA);
                    
                case C -> physics.rotate(0.02, Physics.Axis.ALPHA);
                    
                case X -> physics.rotate(-0.02, Physics.Axis.ALPHA);
                    
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
    
    public Pane getRoot() {
        return root;
    }
    
    public double setX(int i) {
        return (WIDTH/2)-RADIUS*MESH_WIDTH+2*RADIUS*i;
    }
    
    public double setY(int j) {
        return (HEIGHT/2)-RADIUS*MESH_HEIGHT+2*RADIUS*j;
    }
    
    public CameraLine getCameraLine() {
        return cameraLine;
    }
    
    public boolean getDisplay() {
        return display;
    }
    
    public void setDisplay(boolean display) {
        this.display = display;
    }
    
}
