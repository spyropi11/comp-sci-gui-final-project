package edu.vanier.template.simulation;

import edu.vanier.template.controller.CreateNewDrumController;
import edu.vanier.template.drumshapes.Formable;
import edu.vanier.template.elements.*;
import edu.vanier.template.linear.CameraLine;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
     * Boolean denoting when the camera line is displayed.
     */
    private boolean display = true;

    /**
    * Width of root pane.
    */
    private final double WIDTH;
    /**
     * Height of root pane.
     */
    private final double HEIGHT;
    /**
     * Initial radius of each point.
     */
    public final static double RADIUS = 2;
    /**
     * Number of points horizontally.
     */
    public static int MESH_WIDTH;
    /**
     * Number of points vertically.
     */
    public static int MESH_HEIGHT;
    
    /**
     * This number dictates how much less or more the camera 
     * will, zoom, move, or rotate
     */
    public static double magnificationConstant = 3;

    /**
     * Initiates a wave simulation.
     * @param stage The stage of the simulation.
     * @param formable The shape of the drum.
     *
     */
    public Simulation(Formable formable) {
        // We'll have to eventually get rid of this stage parameter and instead of displaying the sim on a stage, we attach the pane used onto another stage with all the ui controls.


        WIDTH = 700;
        HEIGHT = 700;

        root = new Pane();
        root.setPrefWidth(700);
        root.setPrefHeight(700);
        physics = new Physics(this);

        Point[][] points = formable.formMesh();
        MESH_WIDTH = points.length;
        MESH_HEIGHT = points[0].length;
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

        //Scene scene = new Scene(root, WIDTH,HEIGHT);
        cameraLine.setStrokeWidth(1);
        root.getChildren().add(cameraLine);
        //scene.setFill(Color.AZURE);

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




        //stage.setTitle("Drum Sim 2D");
        //stage.setScene(scene);
        //stage.sizeToScene();
        //stage.show();

        physics.startTimer();
        physics.setMouseClicked();

        
        physics.translate(MESH_WIDTH/2, MESH_HEIGHT/2);
        physics.zoom(1.03);
        physics.rotate(0.5, Physics.Axis.ALPHA);

    }

    public void translate(KeyCode keyCode){

        switch(keyCode){

                case W -> physics.translate(0, -3*magnificationConstant);

                case S -> physics.translate(0, 3*magnificationConstant);

                case D -> physics.translate(3*magnificationConstant, 0);

                case A -> physics.translate(-3*magnificationConstant, 0);

                case J -> physics.zoom(1.01 + magnificationConstant*(0.01));

                case K -> physics.zoom(0.99 - magnificationConstant*(0.01));

                case M -> physics.rotate(-0.01*magnificationConstant, Physics.Axis.N);

                case N -> physics.rotate(0.01*magnificationConstant, Physics.Axis.N);

                case B -> physics.rotate(-0.02*magnificationConstant, Physics.Axis.BETA);

                case V -> physics.rotate(0.02*magnificationConstant, Physics.Axis.BETA);

                case C -> physics.rotate(-0.02*magnificationConstant, Physics.Axis.ALPHA);

                case X -> physics.rotate(0.02*magnificationConstant, Physics.Axis.ALPHA);

            }

    }

    public void setCloseSim(Stage stage){
        stage.setOnCloseRequest((WindowEvent windowEvent) -> {
            System.out.println("Simulation ended");
            physics.stopTimer();
            Platform.exit();
        });


    }

    public Pane getRoot() {
        return root;
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
