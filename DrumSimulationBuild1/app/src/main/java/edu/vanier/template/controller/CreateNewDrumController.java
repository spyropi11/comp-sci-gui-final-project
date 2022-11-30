package edu.vanier.template.controller;

import edu.vanier.template.drumshapes.Distribution;
import edu.vanier.template.drumshapes.Formable;
import edu.vanier.template.drumshapes.ParallelogramDrum;
import edu.vanier.template.drumshapes.RectangleDrum;
import edu.vanier.template.drumshapes.SquareDrum;
import edu.vanier.template.drumshapes.TrapezoidDrum;
import edu.vanier.template.simulation.Simulation;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class CreateNewDrumController {

    /**
     * paneSim is the fx id of the pane in the sceneBuilder thats going to be
     * used to display the simulation
     *
     */
    @FXML
    public Pane paneSim;

    public Stage stage;

    Boolean rectangleChosen = false;
    Boolean squareChosen = false;
    Boolean parallelogramChosen = false;
    Boolean trapezoidChosen = false;
    
    Simulation simulation = new Simulation();

    public CreateNewDrumController(Stage stage) {

        this.stage = stage;

    }

    //Button menu
    @FXML
    MenuButton menuButton1;

    //Labels
    @FXML
    Label label1;

    @FXML
    Label label2;

    @FXML
    Label label3;

    @FXML
    Label label4;

    @FXML
    Label label5;

    @FXML
    Label label6;

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

    //@FXML
    //TextField textF3;  modified 

    @FXML
    TextField textF4;

    @FXML
    TextField textF5;

    @FXML
    TextField textF6;
    
    //Spinner
    @FXML
    Spinner spinner;
    
    @FXML
    Spinner spinner2;
    
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
    @FXML
    MenuItem uniformDecay;
    MenuItem horizontalDecay;
    MenuItem verticalDecay;
    MenuItem radialDecay;

    //MenuBar + Texture
    @FXML
    MenuItem cartesian;
    MenuItem parallel;
    MenuItem triangular;
    MenuItem thin;
    MenuItem thick;
    
    //MenuItem + Rest
    @FXML
    MenuItem resetWave;
    MenuItem resetCamera;

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
        //textF3.setEditable(false);

        textF4.setEditable(false);
        textF5.setEditable(false);
        textF6.setEditable(false);

        numSlider = (int) slider.getValue();
        numLabel.setText(Integer.toString(numSlider) + " N");

        numSlider2 = (int) slider2.getValue();
        numLabel2.setText(Integer.toString(numSlider2) + " M");

        numSlider3 = (int) slider3.getValue();
        numLabel3.setText(Integer.toString(numSlider3) + " s");

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                numSlider = (int) slider.getValue();
                numLabel.setText(Integer.toString(numSlider) + " N");

            }

        });

        slider2.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                numSlider2 = (int) slider2.getValue();
                numLabel2.setText(Integer.toString(numSlider2) + " M");

            }

        });

        slider3.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                numSlider3 = (int) slider3.getValue();
                numLabel3.setText(Integer.toString(numSlider3) + " P");
                
                simulation.setDELTATIME((double)newValue);
            }

        });
        
        spinner.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 175)
        );
        
        spinner2.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100)
        );

    }

    public void rectangleChosen(ActionEvent event) {
         label1.setVisible(true);
        textF1.setEditable(true);
         textF1.setVisible(true);
        
        
         label2.setVisible(true);
        textF2.setEditable(true);
        textF2.setVisible(true);
        //ToDo
        label3.setVisible(false);
        spinner.setVisible(false);
        if (spinner.getValue() != null )  {

            spinner.getValueFactory().setValue(0);
            
        }

         label4.setVisible(false);
        textF4.setVisible(false);
        if (!textF4.getText().isEmpty()) {

            textF4.setText("");
        }

         label5.setVisible(false);
        textF5.setVisible(false);
        if (!textF5.getText().isEmpty()) {

            textF5.setText("");
        }

         label6.setVisible(false);
        textF6.setVisible(false);
        if (!textF6.getText().isEmpty()) {

            textF6.setText("");
        }

        this.rectangleChosen = true;
        this.parallelogramChosen = false;
        this.squareChosen = false;
        this.trapezoidChosen = false;

    }

    public void squareChosen(ActionEvent event) {

         label1.setVisible(false);
        textF1.setVisible(false);
        if (!textF1.getText().isEmpty()) {

            textF1.setText("");
        }

         label2.setVisible(true);
        textF2.setEditable(true);
        textF2.setVisible(true);
        if (!textF2.getText().isEmpty()) {

            textF2.setText("");
        }

        label3.setVisible(false);
        spinner.setVisible(false);
        if (spinner.getValue() != null )  {

            spinner.getValueFactory().setValue(0);
            
        }

         label4.setVisible(false);
        textF4.setVisible(false);
        if (!textF4.getText().isEmpty()) {

            textF4.setText("");
        }

         label5.setVisible(false);
        textF5.setVisible(false);
        if (!textF5.getText().isEmpty()) {

            textF5.setText("");
        }

         label6.setVisible(false);
        textF6.setVisible(false);
        if (!textF6.getText().isEmpty()) {

            textF6.setText("");
        }

        this.rectangleChosen = false;
        this.parallelogramChosen = false;
        this.squareChosen = true;
        this.trapezoidChosen = false;

    }

    public void paraChosen(ActionEvent event) {
 
        label1.setVisible(true);
        textF1.setEditable(true);
        textF1.setVisible(true);
        if (!textF1.getText().isEmpty()) {

            textF1.setText("");
        }

         label2.setVisible(false);
        textF2.setVisible(false);
        if (!textF2.getText().isEmpty()) {

            textF2.setText("");
        }

        spinner.setVisible(true);
         label3.setVisible(true);
        spinner.setEditable(true);
        if (spinner.getValue() != null )  {

            spinner.getValueFactory().setValue(0);
            
        }
        textF4.setVisible(true);
         label4.setVisible(true);
        textF4.setEditable(true);
        if (!textF4.getText().isEmpty()) {

            textF4.setText("");
        }

         label5.setVisible(false);
        textF5.setVisible(false);
        if (!textF5.getText().isEmpty()) {

            textF5.setText("");
        }

         label6.setVisible(false);
        textF6.setVisible(false);
        if (!textF6.getText().isEmpty()) {

            textF6.setText("");
        }

        this.rectangleChosen = false;
        this.parallelogramChosen = true;
        this.squareChosen = false;
        this.trapezoidChosen = false;

    }

    public void trapChosen(ActionEvent event) {

         label1.setVisible(false);
        textF1.setVisible(false);
        if (!textF1.getText().isEmpty()) {

            textF1.setText("");
        }
         label2.setVisible(false);
        textF2.setVisible(false);
        if (!textF2.getText().isEmpty()) {

            textF2.setText("");
        }
        
        spinner.setVisible(true);
         label3.setVisible(true);
        spinner.setEditable(true);
         if (spinner.getValue() != null )  {

            spinner.getValueFactory().setValue(0);
            
        }
        
        textF4.setVisible(true);
         label4.setVisible(true);
        textF4.setEditable(true);
        if (!textF4.getText().isEmpty()) {

            textF4.setText("");
        }
        textF5.setVisible(true);
         label5.setVisible(true);
        textF5.setEditable(true);
        if (!textF5.getText().isEmpty()) {

            textF5.setText("");
        }
        textF6.setVisible(true);
         label6.setVisible(true);
        textF6.setEditable(true);
        if (!textF6.getText().isEmpty()) {

            textF6.setText("");
        }

        this.rectangleChosen = false;
        this.parallelogramChosen = false;
        this.squareChosen = false;
        this.trapezoidChosen = true;

    }

    /**
     * Whenever the user clicks on the confirm button this method gets the width
     * and length
     *
     * @param event
     */
    public void handleBtnConfirm(ActionEvent event) throws IOException {

        if (squareChosen) {

            createSquareDrum(Integer.parseInt(textF2.getText()));

        } else if (rectangleChosen) {

            createRectangleDrum(Integer.parseInt(textF1.getText()), Integer.parseInt(textF2.getText()));

        } else if (parallelogramChosen) {

            createParallelogramDrum(Integer.parseInt(textF1.getText()), Integer.parseInt(textF4.getText()), Double.parseDouble((String) spinner.getValue()));

        } else if (trapezoidChosen) {

            createTrapazoidDrum(Integer.parseInt(textF6.getText()), Integer.parseInt(textF5.getText()), Integer.parseInt(textF4.getText()), Double.parseDouble((String) spinner.getValue()));
        } else {

            System.out.println("Please select a shape before confirming");

        }

    }

    public void createSquareDrum(int length) throws IOException {

        //stage.close();
        simulation.startSimulation(new SquareDrum(length));

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

    public void createRectangleDrum(int width, int length) throws IOException {

        //stage.close();
        simulation.startSimulation(new RectangleDrum(width, length));

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

    public void createParallelogramDrum(int width, int height, double angle) throws IOException {

        //stage.close();
        simulation.startSimulation(new ParallelogramDrum(width, height, angle));

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

    public void createTrapazoidDrum(int longBase, int shortBase, int height, double angle) throws IOException {

        //stage.show();
        simulation.startSimulation(new TrapezoidDrum(longBase, shortBase, height, angle));

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
