package edu.vanier.template.controller;

import edu.vanier.template.drumshapes.*;
import edu.vanier.template.drumshapes.Formable.*;
import static edu.vanier.template.drumshapes.Formable.Arrangement.*;
import edu.vanier.template.simulation.Simulation;
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
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

    Boolean UniformMassDChosen = false;
    Boolean HorizontalMassDChosen = false;
    Boolean VerticalMassDChosen = false;
    Boolean RadialMassDChosen = false;

    Boolean cartesianChosen = false;
    Boolean crossedThinChosen = false;
    Boolean crossedThickChosen = false;
    Boolean parallelChosen = false;
    Boolean triangularChosen = false;

    public static double spreadValue;
    public static double amplitudeValue;
    public static double deltaTimeValue;
    public static double densityValue;

    public static final double[] defaultStops = {1};
    public static Distribution distributionValue = new Distribution(Distribution.Surface.UNIFORM, defaultStops);
    public static Arrangement arrangementValue;

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

    //Sliders
    @FXML
    Slider slider;

    @FXML
    Slider slider2;

    @FXML
    Slider slider3;

    //Bottom button
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
    MenuItem uniformMass, horizontalMass, verticalMass, radialMass;

    //MenuBar + decay
    @FXML
    MenuItem uniformDecay, horizontalDecay, verticalDecay, radialDecay;

    //MenuBar + Texture
    @FXML
    MenuItem cartesian, parallel, triangular, thin, thick;

    //MenuItem + Rest
    @FXML
    MenuItem resetWave;
    @FXML
    MenuItem resetCamera;

    //MenuItem + Record
    @FXML
    MenuItem btnStartRecord;
    @FXML
    MenuItem btnStopRecord;
    
    @FXML
    Label customizationLabel;
    
    @FXML
    Menu shapesMenu, massMenu, textureMenu, resetMenu, recordingMenu;
    
    @FXML
    Label spreadLabel;
    @FXML
    Label powerLabel;
    @FXML
    Label timeLabel;
    
    @FXML
    BorderPane root;

    //Slider Number value 1
    int numSlider;

    //Slider number value 2
    int numSlider2;

    //Slider number value 3
    double numSlider3;

    double arcWidth = 0;
    double arcHeight = 0;

    public void initialize() {
        disableSettings();
        menuDuringSettings();
        
        btnConfirm.setDisable(true);
        
        btnStopRecord.setDisable(true);
        btnStartRecord.setOnAction((event) -> {
            //TODO
            btnStopRecord.setDisable(false);
            btnStartRecord.setDisable(true);
        });
        btnStopRecord.setOnAction((event) -> {
            //TODO
            btnStopRecord.setDisable(true);
            btnStartRecord.setDisable(false);
        });
        
        UniformMassDChosen = true;
        cartesianChosen = true;
        
        numSlider = (int) slider.getValue();
        numLabel.setText(Integer.toString(numSlider) + "N");

        numSlider2 = (int) slider2.getValue();
        numLabel2.setText(Integer.toString(numSlider2) + "M");

        numSlider3 = (double) slider3.getValue();
        numLabel3.setText(Double.toString(numSlider3) + "s");

        slider.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            numSlider = (int) slider.getValue();
            numLabel.setText(Integer.toString(numSlider) + "N");
            
            amplitudeValue = (double) newValue;
            System.out.println("slider was changed" + amplitudeValue);
        });

        slider2.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            numSlider2 = (int) slider2.getValue();
            numLabel2.setText(Integer.toString(numSlider2) + "M");
            
            spreadValue = (double) newValue;
            System.out.println("slider was changed" + spreadValue);
        });

        slider3.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            numSlider3 = (double) slider3.getValue();
            numLabel3.setText(Double.toString(numSlider3) + "s");
            
            deltaTimeValue = (double) newValue;
            System.out.println("slider was changed" + deltaTimeValue);
        });

        spinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 175)
        );
        
        resetWave.setOnAction((event) -> {
            simulation.physics.resetWaves();
        });
        
        resetCamera.setOnAction((event) -> {
            simulation.physics.resetCamera();
        });

    }
    
    private void disableSettings() {
        textF1.setEditable(false);
        textF2.setEditable(false);
        textF4.setEditable(false);
        textF5.setEditable(false);
        textF6.setEditable(false);
        textF1.setVisible(false);
        textF2.setVisible(false);
        textF4.setVisible(false);
        textF5.setVisible(false);
        textF6.setVisible(false);
        spinner.setVisible(false);
        label1.setVisible(false);
        label2.setVisible(false);
        label3.setVisible(false);
        label4.setVisible(false);
        label5.setVisible(false);
        label6.setVisible(false);
    }
    
    private void menuDuringSim() {
        shapesMenu.setDisable(true);
        massMenu.setDisable(true);
        textureMenu.setDisable(true);
        resetMenu.setDisable(false);
        recordingMenu.setDisable(false);
        slider.setDisable(false);
        slider2.setDisable(false);
        slider3.setDisable(false);
        spreadLabel.setDisable(false);
        powerLabel.setDisable(false);
        timeLabel.setDisable(false);
        numLabel.setDisable(false);
        numLabel2.setDisable(false);
        numLabel3.setDisable(false);
    }
    
    private void menuDuringSettings() {
        shapesMenu.setDisable(false);
        massMenu.setDisable(false);
        textureMenu.setDisable(false);
        resetMenu.setDisable(true);
        recordingMenu.setDisable(true);
        slider.setDisable(true);
        slider2.setDisable(true);
        slider3.setDisable(true);
        spreadLabel.setDisable(true);
        powerLabel.setDisable(true);
        timeLabel.setDisable(true);
        numLabel.setDisable(true);
        numLabel2.setDisable(true);
        numLabel3.setDisable(true);
    }
    
    @FXML
    public void handleCreateNewDrum(ActionEvent event) {
        try {
            stage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2NewDream.fxml"));
            CreateNewDrumController mainController = new CreateNewDrumController(stage);
            loader.setController(mainController);
            root = loader.load();
            Scene scene = new Scene(root, root.getPrefWidth(), root.getPrefHeight());
            stage.setScene(scene);
            stage.setTitle("Drum Simulation.");
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {
            System.out.println("Exception loading Scene2NewDream.fxml");
        }
    }

    public void rectangleChosen(ActionEvent event) {
        label1.setVisible(true);
        textF1.setEditable(true);
        textF1.setVisible(true);

        label2.setVisible(true);
        textF2.setEditable(true);
        textF2.setVisible(true);
        //TODO
        label3.setVisible(false);
        spinner.setVisible(false);
        if (spinner.getValue() != null) {

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
        
        btnConfirm.setDisable(false);
        
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
        if (spinner.getValue() != null) {

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
        
        btnConfirm.setDisable(false);
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
        
        spinner.setDisable(false);
        spinner.setVisible(true);
        label3.setVisible(true);
        spinner.setEditable(true);
        if (spinner.getValue() != null) {

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
        
        btnConfirm.setDisable(false);
        
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
        
        spinner.setDisable(false);
        spinner.setVisible(true);
        label3.setVisible(true);
        spinner.setEditable(true);
        if (spinner.getValue() != null) {

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
        
        btnConfirm.setDisable(false);
        
    }

    public void handleUniformMassDChosen(ActionEvent event) {

        this.UniformMassDChosen = true;
        this.HorizontalMassDChosen = false;
        this.VerticalMassDChosen = false;
        this.RadialMassDChosen = false;

    }

    public void handleHorizontalMassDChosen(ActionEvent event) {

        this.UniformMassDChosen = false;
        this.HorizontalMassDChosen = true;
        this.VerticalMassDChosen = false;
        this.RadialMassDChosen = false;

    }

    public void handleVerticalMassDChosen(ActionEvent event) {

        this.UniformMassDChosen = false;
        this.HorizontalMassDChosen = false;
        this.VerticalMassDChosen = true;
        this.RadialMassDChosen = false;

    }

    public void handleRadialMassDChosen(ActionEvent event) {

        this.UniformMassDChosen = false;
        this.HorizontalMassDChosen = false;
        this.VerticalMassDChosen = false;
        this.RadialMassDChosen = true;

    }

    //Textures
    public void handleCartesianChosen(ActionEvent event) {

        cartesianChosen = true;
        crossedThinChosen = false;
        crossedThickChosen = false;
        parallelChosen = false;
        triangularChosen = false;

    }

    public void handleCrossedThinChosen(ActionEvent event) {

        cartesianChosen = false;
        crossedThinChosen = true;
        crossedThickChosen = false;
        parallelChosen = false;
        triangularChosen = false;

    }

    public void handleCrossedThickChosen(ActionEvent event) {

        cartesianChosen = false;
        crossedThinChosen = false;
        crossedThickChosen = true;
        parallelChosen = false;
        triangularChosen = false;

    }

    public void handleParallelChosen(ActionEvent event) {

        cartesianChosen = false;
        crossedThinChosen = false;
        crossedThickChosen = false;
        parallelChosen = true;
        triangularChosen = false;

    }

    public void handleTriangularChosen(ActionEvent event) {

        cartesianChosen = false;
        crossedThinChosen = false;
        crossedThickChosen = false;
        parallelChosen = false;
        triangularChosen = true;
    }

    /**
     * Whenever the user clicks on the confirm button this method gets the width
     * and length
     *
     * @param event
     * @throws java.io.IOException
     */
    public void handleBtnConfirm(ActionEvent event) throws IOException {
        Alert saveAlert = new Alert(Alert.AlertType.INFORMATION);

        boolean validate = true;
        boolean validate2 = true;

        try {

            if (cartesianChosen) {

                arrangementValue = CARTESIAN;

                validate = false;

            } else if (crossedThinChosen) {

                arrangementValue = CROSSED_THIN;
                validate = false;

            } else if (crossedThickChosen) {

                arrangementValue = CROSSED_THICK;
                validate = false;

            } else if (parallelChosen) {

                arrangementValue = PARALLEL;
                validate = false;

            } else if (triangularChosen) {

                arrangementValue = TRIANGULAR;
                validate = false;

            }

            if (UniformMassDChosen) {

                double[] stops = new double[]{1};
                distributionValue = new Distribution(Distribution.Surface.UNIFORM, stops);
                validate = false;

            } else if (HorizontalMassDChosen) {

                double[] stops = new double[]{1, 15};
                distributionValue = new Distribution(Distribution.Surface.HORIZONTAL_GRADIENT, stops);
                validate = false;

            } else if (VerticalMassDChosen) {

                double[] stops = new double[]{1, 15};
                distributionValue = new Distribution(Distribution.Surface.VERTICAL_GRADIENT, stops);
                validate = false;

            } else if (RadialMassDChosen) {

                double[] stops = new double[]{1, 15};
                distributionValue = new Distribution(Distribution.Surface.RADIAL_GRADIENT, stops);
                validate = false;

            }

            if (squareChosen) {

                createSquareDrum(Integer.parseInt(textF2.getText()));
                disableSettings();
                menuDuringSim();

            } else if (rectangleChosen) {

                createRectangleDrum(Integer.parseInt(textF1.getText()), Integer.parseInt(textF2.getText()));
                disableSettings();
                menuDuringSim();

            } else if (parallelogramChosen) {

                createParallelogramDrum(Integer.parseInt(textF1.getText()), Integer.parseInt(textF4.getText()), (int) spinner.getValue());
                disableSettings();
                menuDuringSim();

            } else if (trapezoidChosen) {

                createTrapazoidDrum(Integer.parseInt(textF6.getText()), Integer.parseInt(textF5.getText()), Integer.parseInt(textF4.getText()), (int) spinner.getValue());
                disableSettings();
                menuDuringSim();

            } else {

                System.out.println("Please select a shape before confirming");
                saveAlert.setHeaderText(" error");
                saveAlert.setContentText("  Please select a shape before confirming ");
                saveAlert.showAndWait();

            }

        } catch (NumberFormatException | NullPointerException e) {
            
            if(!validate) {
                saveAlert.setHeaderText(" error");
                saveAlert.setContentText(" enter the appropriate values before pressing confirm");

                System.out.println(" enter the appropriate values before pressing confirm");
                saveAlert.showAndWait();
            } else {

                saveAlert.setHeaderText(" error");
                saveAlert.setContentText(" please select a mass distribution , texture please ");
                saveAlert.showAndWait();

            }
        } catch(ArithmeticException e) {
            saveAlert.setHeaderText("Error");
            saveAlert.setContentText(e.getMessage());
            saveAlert.showAndWait();
        }

    }

    public void createSquareDrum(int length) throws IOException, ArithmeticException {
        Formable formable = new SquareDrum(length);
        formable.setMassDistribution(distributionValue);
        formable.setArrangement(arrangementValue);

        simulation.getPhysics().stopTimer();
        setSimulation(new Simulation(formable));

        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            simulation.translate(event.getCode());
        });

        simulation.setCloseSim(stage);
        root.setCenter(simulation.getRoot());
        btnConfirm.setVisible(false);
    }

    public void createRectangleDrum(int width, int length) throws IOException, ArithmeticException {
        Formable formable = new RectangleDrum(width, length);
        formable.setMassDistribution(distributionValue);
        formable.setArrangement(arrangementValue);
        
        simulation.getPhysics().stopTimer();
        setSimulation(new Simulation(formable));

        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            simulation.translate(event.getCode());
        });

        simulation.setCloseSim(stage);
        root.setCenter(simulation.getRoot());
        btnConfirm.setVisible(false);
    }

    public void createParallelogramDrum(int width, int height, int angle) throws IOException, ArithmeticException {
        Formable formable = new ParallelogramDrum(width, height, angle);
        formable.setMassDistribution(distributionValue);
        formable.setArrangement(arrangementValue);
        
        simulation.getPhysics().stopTimer();
        setSimulation(new Simulation(formable));

        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            simulation.translate(event.getCode());
        });

        simulation.setCloseSim(stage);
        root.setCenter(simulation.getRoot());
        btnConfirm.setVisible(false);
    }

    public void createTrapazoidDrum(int longBase, int shortBase, int height, int angle) throws IOException, ArithmeticException {
        Formable formable = new TrapezoidDrum(longBase, shortBase, height, angle);
        formable.setMassDistribution(distributionValue);
        formable.setArrangement(arrangementValue);
        
        simulation.getPhysics().stopTimer();
        setSimulation(new Simulation(formable));

        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            simulation.translate(event.getCode());
        });

        simulation.setCloseSim(stage);
        root.setCenter(simulation.getRoot());
        btnConfirm.setVisible(false);
    }

    public Simulation getSimulation() {
        return this.simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

}
