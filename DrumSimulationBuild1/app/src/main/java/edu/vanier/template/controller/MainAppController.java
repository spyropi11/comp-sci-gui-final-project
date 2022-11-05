/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author keethen
 */
public class MainAppController  {
    
    
    
  private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Rectangle rect1;
    
    @FXML
    TextField textF1;
    
    @FXML
    TextField textF2;
    
    
    
    MenuItem square;
    
    @FXML
    MenuItem rectangle;

    Button btnLoadDrum;

    Button btnCreateNewDrum;

    
    public void initialize() {

        
        
        System.out.println("Initialising the process");
        

        //  btnLoadFile.setOnAction((event) ->{System.out.println("loading the file")};) ;
      //   btnCreateNewDrum.setOnAction((e) -> {
         
          
         
      //   });

    }

    public void handleLoadDrum(ActionEvent event) {

    }

    public void handleCreateNewDrum(ActionEvent event) throws IOException, Exception {

     
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene2NewDream.fxml"));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
         
        
        
       double arcWidth = Double.parseDouble(textF1.getText());
       double arcHeight = Double.parseDouble(textF2.getText());
        
      
       

    }
    
    
  
    

    
    
    
}
