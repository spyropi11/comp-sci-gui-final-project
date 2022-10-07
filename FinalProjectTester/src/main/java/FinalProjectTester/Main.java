/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalProjectTester;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.event.EventType;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.stage.Stage;
import javafx.scene.Scene;
import static javafx.scene.input.KeyCode.S;
import static javafx.scene.input.KeyCode.W;
import static javafx.scene.input.KeyCode.D;
import static javafx.scene.input.KeyCode.A;
import static javafx.scene.input.KeyCode.R;
import static javafx.scene.input.KeyCode.F;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Sphere;
import javafx.stage.WindowEvent;

/**
 *
 * @author Spyros
 */
public class Main extends Application{
    
    public int totalPoints = 3;
    public double time = 0;

    /**
     * @param stage
     * 
     */
    

    @Override
    public void start(Stage stage) throws Exception {
        
        double WIDTH = 700;
        double HEIGHT = 700;
        
        ArrayList<Point> array = new ArrayList<>();
        
        for (int i = 0; i < totalPoints; i++){
            
            array.add(new Point(50, 0, 2.5, 5));
            
        }
        
        array.get(1).setPosition(100);
         
        Group group = new Group();
        
        for (int i = 0; i < totalPoints; i++){
            
           group.getChildren().add(array.get(i));
           array.get(i).translateXProperty().set(i*100);
            
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
        
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(
        new TimerTask() { 
            
            
            @Override
            public void run() {
                
                for (int i = 1; i < totalPoints -1; i++){
                    
                    
                    
                    //time = time + 5;
                    
                    //if (time%100 == 0){
                        
                    array.get(i).translateYProperty().set(array.get(i).getPosition());
                    System.out.println(array.get(1).getPosition());
                    
                    array.get(i).updateCurrentVelocity(array, i-1, i+1);
                    array.get(i).updateNextPosition();
                        
                    
                    
                    //}
                    
                    
                    
                    
                    
                    
                }
                
            }
            
            

        }, 0,10);
        
        stage.setOnCloseRequest((WindowEvent windowEvent) -> {
            timer.cancel();
        });
    }
    
    public static void main(String[] args) {
        
        launch(args);
        
    }
    
    
    
    
    
    
}
