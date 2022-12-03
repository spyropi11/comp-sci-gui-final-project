package edu.vanier.template.controller;

import edu.vanier.template.drumshapes.*;
import edu.vanier.template.elements.Point;
import edu.vanier.template.simulation.Simulation;
import edu.vanier.template.ui.MainApp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainAppController{
    
    
    
  private final Stage stage;
  private Scene scene;
  private BorderPane currentRoot;

    public String musicFilePath = getClass().getResource("/Music/QUANDALE.mp3").toString();
   
    public Media media = new Media(musicFilePath); 
    
    public  MediaPlayer mediaPlayer = new MediaPlayer(media); 
    
  
    public MainAppController(Stage stage) {
        this.stage = stage;
    }
    

    
    public void initialize() { 
    System.out.println("Initialising the process");
        

        
      
    }

   
    public void handleStart(ActionEvent event) throws IOException, Exception {

        MainApp.mediaPlayer.stop();
        
        stage.close();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2NewDream.fxml"));

     
        
        //Instantiate the controller   (Controller is where we do our event handling)
        CreateNewDrumController mainController = new CreateNewDrumController(stage);

        //Set the controller to the loader
        loader.setController(mainController);

        //load the FXML
        BorderPane root = loader.load();
        

        Scene scene = new Scene(root, 700, 700);

        //--> Step 3) Load the scene into stage (window)
        stage.setScene(scene);

        stage.setTitle("Drum Simulation.");
        // Resize the stage so the size matches the scene
        stage.sizeToScene();
        //--> Step 4) Show the window.
        stage.show();
         
     
     
    }
     
}
