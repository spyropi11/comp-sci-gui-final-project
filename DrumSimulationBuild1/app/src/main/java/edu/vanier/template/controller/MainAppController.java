package edu.vanier.template.controller;

import edu.vanier.template.drumshapes.*;
import edu.vanier.template.elements.Point;
import edu.vanier.template.simulation.Simulation;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainAppController  {
    
    
    
  final private Stage stage = new Stage();
  private Scene scene;
  private BorderPane currentRoot;

   
    
    
    

    
    public void initialize() { 
    System.out.println("Initialising the process");
        

        
      
    }

   
    public void handleLoadDrum(ActionEvent event) throws IOException {
    
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/LoadSavedDrum.fxml"));

        //Instantiate the controller   (Controller is where we do our event handling)
        LoadSavedDrumController mainSaveController = new LoadSavedDrumController();

        //Set the controller to the loader
        loader.setController(mainSaveController);

        //load the FXML
        Pane root = loader.load();
        
        Scene scene = new Scene(root, 500, 500);
        //--> Step 3) Load the scene into stage (window)
        stage.setScene(scene);

        stage.setTitle("Drum Simulation.");
        // Resize the stage so the size matches the scene
        stage.sizeToScene();
        //--> Step 4) Show the window.
        stage.show();
        
    }

    public void handleCreateNewDrum(ActionEvent event) throws IOException, Exception {

        //stage.close();
        
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
    
    public void createSquareDrum(int length) throws IOException{
        
        //stage.close();
        
        Simulation simulation = new Simulation(new SquareDrum(length));
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            
            simulation.translate(event.getCode());
            
        });
        
        simulation.setCloseSim(stage);
        
        //stage.getScene().getRoot().
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2NewDream.fxml"));
        CreateNewDrumController mainController = new CreateNewDrumController(stage);
        loader.setController(mainController);
        
        BorderPane root = loader.load();
        root.setCenter(simulation.getRoot());
        
        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);

        stage.setTitle("Drum Simulation.");
        stage.sizeToScene();
        stage.show();
        
    }
    
    public void createRectangleDrum(int width, int length) throws IOException{
        
        //stage.close();
        
        Simulation simulation = new Simulation(new RectangleDrum(width,length));
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            
            simulation.translate(event.getCode());
            
        });
        
        simulation.setCloseSim(stage);
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2NewDream.fxml"));
        CreateNewDrumController mainController = new CreateNewDrumController(stage);
        loader.setController(mainController);
        
        BorderPane root = loader.load();
        root.setCenter(simulation.getRoot());
        
        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);

        stage.setTitle("Drum Simulation.");
        stage.sizeToScene();
        stage.show();
        
    }
    
    public void createParallelogramDrum(int width, int height, double angle) throws IOException{
        
        //stage.close();
        
        Simulation simulation = new Simulation(new ParallelogramDrum(width, height, angle));
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            
            simulation.translate(event.getCode());
            
        });
        
        simulation.setCloseSim(stage);
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2NewDream.fxml"));
        CreateNewDrumController mainController = new CreateNewDrumController(stage);
        loader.setController(mainController);
        
        BorderPane root = loader.load();
        root.setCenter(simulation.getRoot());
        
        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);

        stage.setTitle("Drum Simulation.");
        stage.sizeToScene();
        stage.show();
        
    }
    
    public void createTrapazoidDrum(int longBase, int shortBase, int height, double angle) throws IOException{
        
        //stage.show();
        
        
        Simulation simulation = new Simulation(new TrapezoidDrum(longBase, shortBase, height, angle));
        
        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            
            simulation.translate(event.getCode());
            
        });
        
        simulation.setCloseSim(stage);
        
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2NewDream.fxml"));
        CreateNewDrumController mainController = new CreateNewDrumController(stage);
        loader.setController(mainController);
        
        BorderPane root = loader.load();
        root.setCenter(simulation.getRoot());
        
        Scene scene = new Scene(root, 700, 700);
        stage.setScene(scene);

        stage.setTitle("Drum Simulation.");
        stage.sizeToScene();
        stage.show();
        
    }
    
    
     
}
