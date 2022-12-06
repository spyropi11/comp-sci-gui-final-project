package edu.vanier.template.controller;

import edu.vanier.template.ui.MainApp;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MainAppController{
    
    private final Stage stage;
    private Scene scene;
    private BorderPane currentRoot;

    public static final String musicFilePath = MainAppController.class.getResource("/Music/lofi.mp3").toString();
   
    public static final Media media = new Media(musicFilePath); 
    
    public static MediaPlayer mediaPlayer = new MediaPlayer(media); 
    
    public MainAppController(Stage stage) {
        this.stage = stage;
    }
    
    public void initialize() { 
        
    }
    
    public void handleStart(ActionEvent event) throws IOException {

        MainApp.mediaPlayer.stop();
        
        mediaPlayer.setAutoPlay(true);
        
        stage.close();
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2NewDream.fxml"));

     
        
        //Instantiate the controller   (Controller is where we do our event handling)
        CreateNewDrumController mainController = new CreateNewDrumController(stage);

        //Set the controller to the loader
        loader.setController(mainController);

        //load the FXML
        BorderPane root = loader.load();
        
        scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());

        //--> Step 3) Load the scene into stage (window)
        stage.setScene(scene);
        
        stage.setTitle("Drum Simulation.");
        // Resize the stage so the size matches the scene
        stage.sizeToScene();
        //--> Step 4) Show the window.
        stage.show();
        
    }
}