package edu.vanier.template.tests;

import edu.vanier.template.elements.*;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
        
        //These are #points, not pixels.
        int MESH_WIDTH = 10;
        int MESH_HEIGHT = 10;
        
        //Create points and add them to mesh.
        Point[] points = new Point[MESH_WIDTH*MESH_HEIGHT];
        for(int i = 0; i < MESH_WIDTH; i++) {
            for(int j = 0; j < MESH_HEIGHT; j++) {
                points[MESH_HEIGHT*i+j] = new Point(8, 6, 0, NATURAL_MASS);
                points[10*i+j].setup(setX(i), setY(j));
                if(i==0 || i==(MESH_WIDTH-1) || j==0 || j==(MESH_HEIGHT-1)) {
                    points[10*i+j].setOnEdge(true);
                }
                else {
                    points[10*i+j].setOnEdge(false);
                }
            }
        }
        
        points[44].setPosition(20);
        points[45].setPosition(20);
        points[54].setPosition(20);
        points[55].setPosition(20);
        
        physics.getDrummer().addToMesh(points);
        
        //Create springs (this code block is for a "cartesian plane mesh model", with horizontal and vertical lines) and add them to drum.
        for(int i = 0; i < MESH_WIDTH; i++) {
            for(int j = 0; j < MESH_HEIGHT; j++) {
                Point point = physics.getDrummer().getPoint(setX(i), setY(j));
                ArrayList<Point> connected = new ArrayList<>(0);
                try{
                    connected.add(physics.getDrummer().getPoint(setX(i-1), setY(j)));
                }
                catch(NullPointerException e) {}
                try{
                    connected.add(physics.getDrummer().getPoint(setX(i+1), setY(j)));
                }
                catch(NullPointerException e) {}
                try{
                    connected.add(physics.getDrummer().getPoint(setX(i), setY(j-1)));
                }
                catch(NullPointerException e) {}
                try{
                    connected.add(physics.getDrummer().getPoint(setX(i), setY(j+1)));
                }
                catch(NullPointerException e) {}
                for(Point otherPoint : connected) {
                    physics.getDrummer().addSpring(point, otherPoint, NATURAL_SPRING_CONSTANT);
                }
            }
        }
        
        Group group = new Group();
        group.getChildren().addAll(Arrays.asList(points));
        
        //Set camera and camera origin.
        double oX = (setX(0)+setX(MESH_WIDTH))/2;
        double oY = (setY(0)+setY(MESH_HEIGHT))/2;
        physics.setOrigin(oX, oY, 0);
        physics.setCameraCentre(oX, oY);
        Sphere cameraCentre = new Sphere(4, 4);
        cameraCentre.setTranslateX(oX);
        cameraCentre.setTranslateY(oY);
        PhongMaterial cameraMaterial = new PhongMaterial();
        cameraMaterial.setSpecularColor(Color.YELLOW);
        cameraMaterial.setDiffuseColor(Color.YELLOW);
        cameraCentre.setMaterial(cameraMaterial);
        group.getChildren().add(cameraCentre);
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            
            switch(event.getCode()){
                
                case W -> physics.translate(0, 5);
                    
                case S -> physics.translate(0, -5);
                    
                case D -> physics.translate(-5, 0);
                    
                case A -> physics.translate(5, 0);
                    
                case J -> physics.zoom(1.1);
                    
                case K -> physics.zoom(0.9);
                    
                case M -> physics.rotate(-0.02, Physics.Axis.N);
                    
                case N -> physics.rotate(0.02, Physics.Axis.N);
                    
                case B -> physics.rotate(0.04, Physics.Axis.BETA);
                    
                case V -> physics.rotate(-0.04, Physics.Axis.BETA);
                    
                case C -> physics.rotate(0.04, Physics.Axis.ALPHA);
                    
                case X -> physics.rotate(-0.04, Physics.Axis.ALPHA);
                    
            }
            
        });
        
        Scene scene = new Scene(group, WIDTH, HEIGHT);
        scene.setFill(Color.AZURE);
        
        stage.setTitle("Drum Sim");
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
    
    public double setX(int i) {
        return (700/2)-80+16*i;
    }
    
    public double setY(int j) {
        return (700/2)-80+16*j;
    }
    
}