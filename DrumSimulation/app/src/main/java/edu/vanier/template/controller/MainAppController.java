/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.template.controller;

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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author keethen
 */
public class MainAppController  {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    Button btnLoadDrum;

    Button btnCreateNewDrum;
    
   
     ImageView img;
    Image myImg = new Image(getClass().getResourceAsStream("rectangle.jpg"));

 
    public void initialize(){
        
        System.out.println("Initialising the process");
     //  btnLoadFile.setOnAction((event) ->{System.out.println("loading the file")};) ;
     
     
        
    }
  
    public void handleLoadDrum(ActionEvent event){

       
    }
   
    public void handleCreateNewDrum(ActionEvent event) throws IOException{
       
        
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene2NewDream.fxml"));
        stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

        

    }

    
      /* public void displayImage(){
        
        img.setImage(myImg);
        
    }
    */
    

    
    
    
    
    
    
    
    
    
}
