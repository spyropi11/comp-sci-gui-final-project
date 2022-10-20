package edu.vanier.template.tests;

import edu.vanier.template.elements.*;
import edu.vanier.template.linear.Matrix;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Tester {

    private final Physics physics = new Physics(this);
    
    /**
    * When defining the spring constant, we can make it a multiple of this constant.
    * For example, if the animation only looks good when k is in the 1000s, then we can make this constant = 1000.
    * Or, if the animation only looks good when k is 0.0001, 0.00005, 0.0002, etc., we can make this constant = 0.00001.
    */
    private final double NATURAL_SPRING_CONSTANT = 2;
    /**
    * When defining the mass, we can make it a multiple of this constant.
    * For example, if the animation only looks good when m is in the 1000s, then we can make this constant = 1000.
    * Or, if the animation only looks good when m is 0.0001, 0.0005, 0.0002, etc., we can make this constant = 0.0001.
    */
    private final double NATURAL_MASS = 0.8;
    
    private final double NATURAL_DECAY = 0.1;
    
    private double CAMERA_LINE_LENGTH = 15;
    private double CAMERA_LINE_LIMIT = 30;
    private double CAMERA_LINE_DIST = 0.02;
    
    private double WIDTH;
    private double HEIGHT;
    private double RADIUS;
    
    //These are number of points, not pixels.
    private int MESH_WIDTH;
    private int MESH_HEIGHT;
    
    private Pane root;
    private Line cameraLine = new Line();
    
    public Tester(Stage stage) throws Exception {
        
        WIDTH = 700;
        HEIGHT = 700;
        RADIUS = 2;
        
        double amplitude = 100;
        int iShift = 200/2;
        double spread = 200; 
        
        MESH_WIDTH = 100;
        MESH_HEIGHT = 100;
        
        //Create points and add them to mesh.
        Point[][] points = new Point[MESH_WIDTH][MESH_HEIGHT];
        for(int i = 0; i < MESH_WIDTH; i++) {
            for(int j = 0; j < MESH_HEIGHT; j++) {
                points[i][j] = new Point(RADIUS, 6, 0, NATURAL_MASS);
                points[i][j].setDecay(NATURAL_DECAY);
                points[i][j].setup(setX(i), setY(j));
                if(i==0 || i==(MESH_WIDTH-1) || j==0 || j==(MESH_HEIGHT-1)) {
                    points[i][j].setOnEdge(true);
                }
                else {
                    points[i][j].setOnEdge(false);
                }
                physics.getDrummer().addToMesh(points[i][j], i, j);
            }
        }
        
        //Setting pulses (just some random position values)
        for(int i = 1; i < 15; i++) {
            for(int j = 1; j < 15; j++) {
                if(i>5 && i<10 && j>5 && j<10) {
                    points[i][j].setPosition(40);
                    points[i+84][j+84].setPosition(-40);
                }
                else {
                    points[i][j].setPosition(20);
                    points[i+84][j+84].setPosition(-20);
                }
            }
        }
        
        //Create springs (this code block is for a "cartesian plane mesh model", with horizontal and vertical lines) and add them to drum.
        for(int i = 0; i < MESH_WIDTH; i++) {
            for(int j = 0; j < MESH_HEIGHT; j++) {
                Point point = points[i][j];
                if(i > 0) {
                    physics.getDrummer().addSpring(point, points[i-1][j], NATURAL_SPRING_CONSTANT);
                }
                if(i < (MESH_WIDTH-1)) {
                    physics.getDrummer().addSpring(point, points[i+1][j], NATURAL_SPRING_CONSTANT);
                }
                if(j > 0) {
                    physics.getDrummer().addSpring(point, points[i][j-1], NATURAL_SPRING_CONSTANT);
                }
                if(j < (MESH_HEIGHT-1)) {
                    physics.getDrummer().addSpring(point, points[i][j+1], NATURAL_SPRING_CONSTANT);
                }
            }
        }
        
        root = new Pane();
        /* This commented-out loop generates the spheres.
        for(Point[] pointLine : points) {
            root.getChildren().addAll(Arrays.asList(pointLine));
        }
        */
        root.getChildren().addAll(physics.getDrummer().getDrum());
        cameraLine.setStrokeWidth(1);
        root.getChildren().add(cameraLine);
        
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
        
        physics.rotate(1.2, Physics.Axis.ALPHA);
        physics.rotate(-0.05, Physics.Axis.BETA);
        physics.rotate(-0.05, Physics.Axis.N);
        physics.zoom(1.5);
        
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.AZURE);
        
        stage.setTitle("Drum Sim");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
        
        physics.startTimeline();
        
        stage.setOnCloseRequest((WindowEvent windowEvent) -> {
            physics.stopTimeline();
            Platform.exit();
        });
        
    }
    
    public double setX(int i) {
        return (WIDTH/2)-RADIUS*MESH_WIDTH+2*RADIUS*i;
    }
    
    public double setY(int j) {
        return (HEIGHT/2)-RADIUS*MESH_HEIGHT+2*RADIUS*j;
    }
    
    public Pane getRoot() {
        return root;
    }
    
    public void displayCameraLine(Pane root, double oX, double oY, boolean display) {
        if(!display){return;}
        
        cameraLine.setStartX(oX);
        cameraLine.setStartY(oY);
        
        double a0 = physics.getAlpha()[0]/Point.norm(physics.getAlpha());
        double a1 = physics.getAlpha()[1]/Point.norm(physics.getAlpha());
        double a2 = physics.getAlpha()[2]/Point.norm(physics.getAlpha());
        double b0 = physics.getBeta()[0]/Point.norm(physics.getBeta());
        double b1 = physics.getBeta()[1]/Point.norm(physics.getBeta());
        double b2 = physics.getBeta()[2]/Point.norm(physics.getBeta());
        
        double[][] m = new double[2][3];
        
        if(a0 != 0) {
            if(b0 != 0) {
                if(b1 != ((a1*b0)/a0)) {
                    double f = b1/b0 - a1/a0;
                    m[0][0] = 1/a0 - (a1/a0)*(-1/(f*a0));
                    m[0][1] = -1/(f*a0);
                    m[0][2] = 0;
                    m[1][0] = -a1/(f*a0*b0);
                    m[1][1] = 1/(f*b0);
                    m[1][2] = 0;
                }
                else {
                    double c = b2/b0 - a2/a0;
                    m[0][0] = 1/a0 + (a2/a0)*(1/a0*c);
                    m[0][1] = 0;
                    m[0][2] = -1/(a0*c);
                    m[1][0] = -a2/(a0*b0*c);
                    m[1][1] = 0;
                    m[1][2] = 1/(b0*c);
                }
            }
            else {
                if(b1 != 0) {
                    m[0][0] = 1/a0;
                    m[0][1] = 0;
                    m[0][2] = 0;
                    m[1][0] = -a1/(a0*b1);
                    m[1][1] = 1/b1;
                    m[1][2] = 0;
                }
                else {
                    m[0][0] = 1/a0;
                    m[0][1] = 0;
                    m[0][2] = 0;
                    m[1][0] = -a2/(a0*b2);
                    m[1][1] = 0;
                    m[1][2] = 1/b2;
                }
            }
        }
        else {
            if(b0 != 0) {
                if(a1 != 0) {
                    m[0][0] = -(b1/a1*b0);
                    m[0][1] = 1/a1;
                    m[0][2] = 0;
                    m[1][0] = 1/b0;
                    m[1][1] = 0;
                    m[1][2] = 0;
                }
                else {
                    m[0][0] = -b2/(a2*b0);
                    m[0][1] = 0;
                    m[0][2] = 1/a2;
                    m[1][0] = 1/b1;
                    m[1][1] = 0;
                    m[1][2] = 0;
                }
            }
            else {
                if(a1 != 0) {
                    if(b1 != 0) {
                        m[0][0] = 0;
                        m[0][1] = (a2*b2 - a1*b2 - a2*b1)/(a1*(a2*b2 - a1*b2));
                        m[0][2] = b1/(a2*b2 - a1*b2);
                        m[1][0] = 0;
                        m[1][1] = -a2/(a1*b2 - a2*b1);
                        m[1][2] = a1/(a1*b2 - a2*b1);
                    }
                    else {
                        m[0][0] = 0;
                        m[0][1] = 1/a1;
                        m[0][2] = 0;
                        m[1][0] = 0;
                        m[1][1] = -a2/(a1*b2);
                        m[1][2] = 1/b2;
                    }
                }
                else {
                    m[0][0] = 0;
                    m[0][1] = -b2/(b1*a1);
                    m[0][2] = 1/a2;
                    m[1][0] = 0;
                    m[1][1] = 1/b1;
                    m[1][2] = 0;
                }
            }
        }
        
        Matrix matrix = new Matrix(m);
        double[] projN = matrix.act(physics.getN());
        
        double[] end = {CAMERA_LINE_LENGTH*projN[0], CAMERA_LINE_LENGTH*projN[1]};
        
        double[] nend = new double[2];
        
        nend[0] = CAMERA_LINE_LIMIT*Math.tanh(CAMERA_LINE_DIST*end[0]);
        nend[1] = CAMERA_LINE_LIMIT*Math.tanh(CAMERA_LINE_DIST*end[1]);
        
        if(Point.crossProduct(physics.getAlpha(), physics.getBeta())[2]>=0) {
            cameraLine.setEndX(nend[0] + oX);
            cameraLine.setEndY(nend[1] + oY);
        }
        else {
            cameraLine.setEndX(-nend[0] + oX);
            cameraLine.setEndY(-nend[1] + oY);
        }
        
        Stop[] stops = new Stop[] {
            new Stop(0, Color.YELLOW),
            new Stop(1, Color.GREEN)
        };
        LinearGradient gradient = new LinearGradient(cameraLine.getStartX(), cameraLine.getStartY(), cameraLine.getEndX(), cameraLine.getEndY(), false, CycleMethod.NO_CYCLE, stops);
        cameraLine.setStroke(gradient);
    }
    
}