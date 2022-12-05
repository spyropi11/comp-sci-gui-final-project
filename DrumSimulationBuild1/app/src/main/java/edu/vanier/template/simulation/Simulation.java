package edu.vanier.template.simulation;

import edu.vanier.template.drumshapes.Formable;
import edu.vanier.template.elements.*;
import edu.vanier.template.linear.CameraAxis;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public final class Simulation {


    /**
    * Binds a Physics object to this Simulation object.
    */
    public Physics physics;
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
     * DELTATIME must be between 0.01 and 0.1
     */
    public double DELTATIME = 0.01;
    /**
    * Root node of scene.
    */
    private Pane root;
    
    
    
    private CameraAxis camX;
    private CameraAxis camY;
    private CameraAxis camZup;
    private CameraAxis camZdown;
    
    public double oX;
    public double oY;
    
    
    /**
     * Boolean denoting when the camera line is displayed.
     */
    private boolean display = true;

    /**
    * Width of root pane.
    */
    private double WIDTH;
    /**
     * Height of root pane.
     */
    private double HEIGHT;
    /**
     * Initial radius of each point.
     */
    public static double RADIUS = 2;
    /**
     * Number of points horizontally.
     */
    public int MESH_WIDTH;
    /**
     * Number of points vertically.
     */
    public int MESH_HEIGHT;
    
    /**
     * This number dictates how much less or more the camera 
     * will, zoom, move, or rotate
     */
    public static double magnificationConstant = 3;

    public Formable formable;

    public Simulation() {
        
        this.root = new Pane();
        this.root.setPrefWidth(700);
        this.root.setPrefHeight(700);
        setDELTATIME(0.01);
        this.physics = new Physics(this);
    }
    
    /**
     * Initiates a wave simulation.
     * @param formable The shape of the drum.
     *
     */
    public Simulation(Formable formable) {
        
        this.formable = formable;
        this.root = new Pane();
        
        WIDTH = 700;
        HEIGHT = 700;

        this.root.setPrefWidth(700);
        this.root.setPrefHeight(700);

        Point[][] points = formable.formMesh();
        MESH_WIDTH = points.length;
        MESH_HEIGHT = points[0].length;
        ArrayList<Spring> drum = formable.formDrum();

        this.physics = new Physics(this);
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

        camX = new CameraAxis(physics, CameraAxis.Axis.X);
        camY = new CameraAxis(physics, CameraAxis.Axis.Y);
        camZup = new CameraAxis(physics, CameraAxis.Axis.Zup);
        camZdown = new CameraAxis(physics, CameraAxis.Axis.Zdown);
        
        camX.setStrokeWidth(2);
        root.getChildren().add(camX);
        camY.setStrokeWidth(2);
        root.getChildren().add(camY);
        camZup.setStrokeWidth(2);
        root.getChildren().add(camZup);
        camZdown.setStrokeWidth(2);
        root.getChildren().add(camZdown);
        
        //Set camera and camera origin.
        oX = root.getPrefWidth()/2;
        oY = root.getPrefHeight()/2;
        physics.setOrigin(oX, oY, 0);
        Sphere cameraCentre = new Sphere(4);
        cameraCentre.setTranslateX(oX);
        cameraCentre.setTranslateY(oY);
        PhongMaterial cameraMaterial = new PhongMaterial();
        cameraMaterial.setSpecularColor(Color.YELLOW);
        cameraMaterial.setDiffuseColor(Color.YELLOW);
        cameraCentre.setMaterial(cameraMaterial);
        root.getChildren().add(cameraCentre);
        
        
        camX.setStartX(oX);
        camX.setStartY(oY);
        camY.setStartX(oX);
        camY.setStartY(oY);
        camZup.setStartX(oX);
        camZup.setStartY(oY);
        camZdown.setStartX(oX);
        camZdown.setStartY(oY);
        
        root.widthProperty().addListener((observable, oldValue, newValue) -> {
            oX = (double)newValue/2;
            camX.setStartX(oX);
            camX.setEndX(camX.xV + oX);
            camY.setStartX(oX);
            camY.setEndX(camY.xV + oX);
            camZup.setStartX(oX);
            camZup.setEndX(camZup.xV + oX);
            camZdown.setStartX(oX);
            camZdown.setEndX(camZdown.xV + oX);
            cameraCentre.setTranslateX(oX);
        });
        root.heightProperty().addListener((observable, oldValue, newValue) -> {
            Simulation.this.oY = (double)newValue/2;
            camX.setStartY(oY);
            camX.setEndY(camX.yV + oY);
            camY.setStartY(oY);
            camY.setEndY(camY.yV + oY);
            camZup.setStartY(oY);
            camZup.setEndY(camZup.yV + oY);
            camZdown.setStartY(oY);
            camZdown.setEndY(camZdown.yV + oY);
            cameraCentre.setTranslateY(oY);
        });
        
        physics.setMouseClicked();
        
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
            physics.stopTimer();
        });
    }

    public Pane getRoot() {
        return this.root;
    }

    public void setRoot(Pane root) {
        this.root = root;
    }
    
    public CameraAxis getCamX() {
        return camX;
    }
    
    public CameraAxis getCamY() {
        return camY;
    }
    
    public CameraAxis getCamZup() {
        return camZup;
    }
    
    public CameraAxis getCamZdown() {
        return camZdown;
    }

    public boolean getDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public double getDELTATIME() {
        return DELTATIME;
    }

    public void setDELTATIME(double DELTATIME) {
        this.DELTATIME = DELTATIME;
    }

    public Physics getPhysics() {
        return this.physics;
    }

    public void setPhysics(Physics physics) {
        this.physics = physics;
    }
    
}