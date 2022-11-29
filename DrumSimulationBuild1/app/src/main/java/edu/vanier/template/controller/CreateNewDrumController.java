
package edu.vanier.template.controller;

import edu.vanier.template.drumshapes.Distribution;
import edu.vanier.template.drumshapes.Formable;
import edu.vanier.template.drumshapes.RectangleDrum;
import edu.vanier.template.simulation.Simulation;
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
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class CreateNewDrumController implements Initializable {

  /**
   *  paneSim is the fx id of the pane in the sceneBuilder thats going to be used to display the simulation
   *
   */
 @FXML
 public Pane paneSim;

    public Stage stage;

    Boolean rectangleChosen = false;
    Boolean squareChosen = false;
    Boolean parallelogramChosen = false;
    Boolean trapezoidChosen = false;
    
    MainAppController MAC = new MainAppController();

    public CreateNewDrumController(Stage stage){

        this.stage = stage;

    }
    
    //Button menu
    @FXML
    MenuButton menuButton1;

    //Labels
    @FXML
    Label numLabel;

    @FXML
    Label numLabel2;
    
    @FXML
    Label numLabel3;

    //Text
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

    //Sliders
    @FXML
    Slider slider;

    @FXML
    Slider slider2;
    
    @FXML
    Slider slider3;
    
    //Bottom button
    @FXML
    Button btnSaveButton;

    @FXML
    Button btnConfirm;
    
    //Shapes + MenuBar
    @FXML
    MenuItem square;

    @FXML
    MenuItem rectangle;
    
    @FXML
    MenuItem parallelogram;

    @FXML
    MenuItem trapezoid;
 
    
    //Start Screen
    @FXML
    Button btnLoadDrum;
    
    @FXML
    Button btnCreateNewDrum;
    
    //MenuBar + file
    @FXML
    MenuItem loadSavedDrum;
    
    //MenuBar + Mass
    @FXML
    MenuItem uniformMass;
    MenuItem horizontalMass;
    MenuItem verticalMass;
    MenuItem radialMass;
    
    //MenuBar + decay
    MenuItem uniformDecay;
    MenuItem horizontalDecay;
    MenuItem verticalDecay;
    MenuItem radialDecay;
    
    //MenuBar + Texture
    MenuItem cartesian;
    MenuItem parallel;
    MenuItem triangular;
    MenuItem thin;
    MenuItem thick;
       
    //Slider Number value 1
    int numSlider;

    //Slider number value 2
    int numSlider2;
    
    //Slider number value 3
     int numSlider3;

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


        textF1.setEditable(true);
        textF2.setEditable(true);
        textF3.setEditable(false);
        textF4.setEditable(false);
        textF5.setEditable(false);
        textF6.setEditable(false);

        this.rectangleChosen = true;
        this.parallelogramChosen = false;
        this.squareChosen = false;
        this.trapezoidChosen = false;

     }


      public void squareChosen(ActionEvent event)  {



        textF1.setEditable(false);
        textF2.setEditable(true);
        textF3.setEditable(false);
        textF4.setEditable(false);
        textF5.setEditable(false);
        textF6.setEditable(false);
         
        this.rectangleChosen = false;
        this.parallelogramChosen = false;
        this.squareChosen = true;
        this.trapezoidChosen = false;

     }

       public void paraChosen(ActionEvent event)  {


        textF1.setEditable(true);
        textF2.setEditable(false);
        textF3.setEditable(true);
        textF4.setEditable(true);
        textF5.setEditable(false);
        textF6.setEditable(false);

        this.rectangleChosen = false;
        this.parallelogramChosen = true;
        this.squareChosen = false;
        this.trapezoidChosen = false;

     }

       public void trapChosen(ActionEvent event)  {

        textF1.setEditable(false);
        textF2.setEditable(false);
        textF3.setEditable(true);
        textF4.setEditable(true);
        textF5.setEditable(true);
        textF6.setEditable(true);

        this.rectangleChosen = false;
        this.parallelogramChosen = false;
        this.squareChosen = false;
        this.trapezoidChosen = true;


     }





     /**
      * Whenever the user clicks on the confirm button this method gets the width and length
      *
      * @param event
      */

     public void handleBtnConfirm(ActionEvent event) throws IOException  {

         if (squareChosen){
             
             MAC.createSquareDrum(Integer.parseInt(textF2.getText()));
             
         }else if(rectangleChosen){
             
             MAC.createRectangleDrum(Integer.parseInt(textF1.getText()),Integer.parseInt(textF2.getText()));
             
         }else if(parallelogramChosen){
             
             MAC.createParallelogramDrum(Integer.parseInt(textF1.getText()), Integer.parseInt(textF4.getText()), Double.parseDouble(textF3.getText()));
             
         }else if(trapezoidChosen){
             
             MAC.createTrapazoidDrum(Integer.parseInt(textF6.getText()), Integer.parseInt(textF5.getText()), Integer.parseInt(textF4.getText()), Integer.parseInt(textF3.getText()));
         }else{
             
             System.out.println("Please select a shape before confirming");
             
         }
         

     }

     


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        numSlider = (int)slider.getValue();
        numLabel.setText(Integer.toString(numSlider) + " N");


        numSlider2 = (int)slider2.getValue();
        numLabel2.setText(Integer.toString(numSlider2) + " M");
        
        numSlider3 = (int)slider3.getValue();
        numLabel3.setText(Integer.toString(numSlider3) + " P");



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
        
        slider3.valueProperty().addListener( new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                numSlider3 = (int)slider3.getValue();
                numLabel3.setText(Integer.toString(numSlider3) + " P"); 
                
                
                
            }
             
        });





    }




}
