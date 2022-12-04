package edu.vanier.template.ui;

import edu.vanier.template.controller.MainAppController;
import edu.vanier.template.save.SaveEnvelope;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MainApp extends Application {

    public int totalPoints = 5;
    public double time = 0;
    
    public static final String musicFilePath = MainApp.class.getResource("/Music/QUANDALE.mp3").toString();
   
    public static final Media media = new Media(musicFilePath); 
    
    public static final MediaPlayer mediaPlayer = new MediaPlayer(media);  

    /**
     * This is where the main stage gets executed
     * @param stage
     *
     */
    @Override
    public void start(Stage stage) throws Exception {

        // Load FXML file on Netbeans
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindowDrumSim.fxml"));
        

        //Instantiate the controller   (Controller is where we do our event handling)
        MainAppController mainController = new MainAppController(stage);

        //Set the controller to the loader
        loader.setController(mainController);

        //load the FXML
        Pane root = loader.load();


        Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
        
        stage.setScene(scene);

        mediaPlayer.setAutoPlay(true);
        
        stage.setTitle("Drum Sim");
        // Resize the stage so the size matches the scene
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        SaveEnvelope.createDirectory();
        launch(args);
    }

    public MediaPlayer getMediaPlayer() {
        return MainApp.mediaPlayer;
    }
    
}