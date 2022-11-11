package edu.vanier.template.controller;

import edu.vanier.template.elements.Point;
import edu.vanier.template.tests.Tester;
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
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MainAppController  {
    
    
    
  private Stage stage = new Stage();
  private Scene scene;
  private Parent root;

   
    
    
    

    
    public void initialize() { 
    System.out.println("Initialising the process");
        

        
       //  btnLoadFile.setOnAction((event) ->{System.out.println("loading the file")};) ;
      //   btnCreateNewDrum.setOnAction((e) -> {
         
          
         
     //});
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


     FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2NewDream.fxml"));

        //Instantiate the controller   (Controller is where we do our event handling)
        CreateNewDrumController mainController = new CreateNewDrumController();

        //Set the controller to the loader
        loader.setController(mainController);

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
     
}
