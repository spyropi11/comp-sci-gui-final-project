package edu.vanier.template.ui;

import edu.vanier.template.drumshapes.RectangleDrum;
import edu.vanier.template.controller.MainAppController;
import edu.vanier.template.simulation.Simulation;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainApp extends Application {

    public int totalPoints = 5;
    public double time = 0;

    /**
     * @param stage
     * 
     */
    @Override
    public void start(Stage stage) throws Exception {
        
    
        // Load FXML file on Netbeans
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindowDrumSim.fxml"));

        //Instantiate the controller   (Controller is where we do our event handling)
        MainAppController mainController = new MainAppController();

        //Set the controller to the loader
        loader.setController(mainController);

        //load the FXML
        Pane root = loader.load();
        
        
        Scene scene = new Scene(root, 500, 500);
        //--> Step 3) Load the scene into stage (window)
        stage.setScene(scene);

        stage.setTitle("Drum Simulation");
        // Resize the stage so the size matches the scene
        stage.sizeToScene();
        //--> Step 4) Show the window.
        stage.show();
        

        
         //Simulation testSimulationRectangle = new Simulation(stage, new RectangleDrum(50, 50));
        
    }
    
    public static void main(String[] args) {
        
        launch(args);
        
    }
    
    
    
}