/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.template.controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author keethen
 */
public class CreateNewDrumController {
    
      @FXML
    Rectangle rect1;
    
    @FXML
    TextField textF1;
    
    @FXML
    TextField textF2;
    
    @FXML
    Slider slider;
    
    @FXML
    Button btnSaveButton;
    
    @FXML
    Button btnConfirm;
    
    
    
    MenuItem square;
    
    @FXML
    MenuItem rectangle;

    Button btnLoadDrum;

    Button btnCreateNewDrum;

    
    
     public void initialize() {

        
        
        System.out.println("Initialising the process");
        

    /*   btnConfirm.setOnAction((e) -> {
         
           double arcWidth = 0;
     double arcHeight = 0;
     
    
     arcWidth = Double.parseDouble(textF1.getText());
       arcHeight = Double.parseDouble(textF2.getText());
        
            
       
        
        System.out.println(arcWidth);
       System.out.println(arcHeight);
         
         
        });

*/       

    }
     
     
     
     public void handleBtnConfirm(ActionEvent event) throws IOException, Exception {
         
         
          double arcWidth = 0;
     double arcHeight = 0;
     
    
     arcWidth = Double.parseDouble(textF1.getText());
       arcHeight = Double.parseDouble(textF2.getText());
        
            
       
        
        System.out.println(arcWidth);
       System.out.println(arcHeight);
         
         
         
     }
    
    
    
    
   
    
    
    
    
    
    
    
    
    
    
    
}
