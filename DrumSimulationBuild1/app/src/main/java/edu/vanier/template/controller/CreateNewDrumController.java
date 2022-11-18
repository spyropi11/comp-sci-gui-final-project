/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vanier.template.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author keethen
 */
public class CreateNewDrumController implements Initializable {
    
    @FXML
    Label numLabel;
    
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
    
    //Slider Number Value
    int numSlider;
    
     public void initialize() {  
     System.out.println("Initialising the process");

    /*   
    btnConfirm.setOnAction((e) -> {
         
     double arcWidth = 0;
     double arcHeight = 0;
     
    
     arcWidth = Double.parseDouble(textF1.getText());
     arcHeight = Double.parseDouble(textF2.getText());
        
            
       
        
     System.out.println(arcWidth);
     System.out.println(arcHeight);
         
         
     });

      */       
    }
     
     
     
     public void handleBtnConfirm(ActionEvent event)  {
     
         try {
             
             
             int power = 0;
         
     double arcWidth = 0;
     double arcHeight = 0;
     
     power = (int)slider.getValue();
     
     arcWidth = Double.parseDouble(textF1.getText());
     arcHeight = Double.parseDouble(textF2.getText());
       
            
       
       System.out.println("Power: " + power);
       
       System.out.println("Width: " + arcWidth);
       System.out.println("Height: "+ arcHeight);
       System.out.println(); //space
         
             
         }  catch( Exception x) {
                 
                 System.out.println("You havent entered a proper value please try again!");
                 
                 } 
         
     
         
     }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    numSlider = (int)slider.getValue();
    numLabel.setText(Integer.toString(numSlider) + " N"); 
        
        slider.valueProperty().addListener( new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                numSlider = (int)slider.getValue();
                numLabel.setText(Integer.toString(numSlider) + " N"); 
            }
             
        });
    }
       
}
