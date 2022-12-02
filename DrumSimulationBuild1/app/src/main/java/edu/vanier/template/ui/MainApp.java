package edu.vanier.template.ui;

import edu.vanier.template.drumshapes.RectangleDrum;
import edu.vanier.template.controller.MainAppController;
import edu.vanier.template.drumshapes.ParallelogramDrum;
import edu.vanier.template.drumshapes.SquareDrum;
import edu.vanier.template.drumshapes.TrapezoidDrum;
import edu.vanier.template.elements.Physics;
import edu.vanier.template.simulation.Simulation;
import java.io.File;
import java.net.URL;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MainApp extends Application {

    public int totalPoints = 5;
    public double time = 0;
    
    public static String musicFilePath = MainApp.class.getResource("/Music/QUANDALE.mp3").toString();
   
    public static Media media = new Media(musicFilePath); 
    
    public static MediaPlayer mediaPlayer = new MediaPlayer(media);  

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


        Scene scene = new Scene(root, 500, 500);
        
        //--> Step 3) Load the scene into stage (window)
        stage.setScene(scene);

        mediaPlayer.setAutoPlay(true);
        
        stage.setTitle("Drum Sim");
        // Resize the stage so the size matches the scene
        stage.sizeToScene();
        //--> Step 4) Show the window.
        stage.show();
        

    }

    public static void main(String[] args) {

        launch(args);

    }

    public MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }
    
    


}
