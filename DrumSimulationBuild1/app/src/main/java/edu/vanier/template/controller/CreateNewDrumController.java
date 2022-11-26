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
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
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
    MenuButton menuButton1;
    
    
    @FXML
    Label numLabel;
    
    @FXML
    Label numLabel2;
    
    @FXML
    TextField textF1;
    
    @FXML
    TextField textF2;
    
    @FXML
    TextField textF3;
    
    @FXML
    TextField textF4;
      
    @FXML
    TextField textF5;
       
    @FXML
    TextField textF6;
    
    
    @FXML
    Slider slider;
    
    @FXML 
    Slider slider2;
    
    @FXML
    Button btnSaveButton;
    
    @FXML
    Button btnConfirm;
    MenuItem square;
    
    @FXML
    MenuItem parallelogram;
    
    @FXML 
    MenuItem trapezoid;
    
    @FXML
    MenuItem rectangle;
    Button btnLoadDrum;
    Button btnCreateNewDrum;
    
    //Slider Number Value
    int numSlider;
    
    //Slider number value 2
    int numSlider2;
    
    
    double arcWidth = 0;
    double arcHeight = 0;
    
    public void initialize() {  
        textF1.setEditable(false);
        textF2.setEditable(false);
        textF3.setEditable(false);

        textF4.setEditable(false);
        textF5.setEditable(false);
        textF6.setEditable(false);
     
    
    }
     public void rectangleChosen(ActionEvent event)  {
         
         
         
         textF3.setEditable(false);
         textF4.setEditable(false);
         textF5.setEditable(false);
         textF6.setEditable(false);
      
         
     }
     
     
      public void squareChosen(ActionEvent event)  {
         
         
         
         textF1.setEditable(false);
         textF3.setEditable(false);
         textF4.setEditable(false);
         textF5.setEditable(false);
         textF6.setEditable(false);
      
         
     }
      
       public void paraChosen(ActionEvent event)  {
         
         
         textF2.setEditable(false);
       
         textF5.setEditable(false);
         textF6.setEditable(false);
      
         
     }
       
       public void trapChosen(ActionEvent event)  {
         
         textF1.setEditable(false);
         textF2.setEditable(false);
        
      
         
     } 
     
     
     
     
     
     /**
      * Whenever the user clicks on the confirm button this method gets the width and length
      * 
      * @param event 
      */
     
     public void handleBtnConfirm(ActionEvent event)  {
         
     
        try {


            int power = 0;
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
   
    
    numSlider2 = (int)slider2.getValue();
    numLabel2.setText(Integer.toString(numSlider2) + " M"); 
       
    
    
        slider.valueProperty().addListener( new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                numSlider = (int)slider.getValue();
                numLabel.setText(Integer.toString(numSlider) + " N"); 
                
                
                
            }
             
        });
        
        slider2.valueProperty().addListener( new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                numSlider2 = (int)slider2.getValue();
                numLabel2.setText(Integer.toString(numSlider2) + " M"); 
                
                
                
            }
             
        });


        
        
    }
    
    
    
       
}
