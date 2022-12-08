package edu.vanier.template.controller;

import edu.vanier.template.drumshapes.*;
import edu.vanier.template.drumshapes.Formable.*;
import static edu.vanier.template.drumshapes.Formable.Arrangement.*;
import edu.vanier.template.save.SaveHandler;
import edu.vanier.template.simulation.Simulation;
import java.io.IOException;
import java.util.Objects;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
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
     * paneSim is the fx:id of the pane in the sceneBuilder that's going to be
     * used to display the simulation
     */
    @FXML
    public Pane paneSim;

    public Stage stage;

    Boolean rectangleChosen = false;
    Boolean squareChosen = false;
    Boolean parallelogramChosen = false;
    Boolean trapezoidChosen = false;

    Boolean UniformMassDChosen = true;
    Boolean HorizontalMassDChosen = false;
    Boolean VerticalMassDChosen = false;
    Boolean RadialMassDChosen = false;

    Boolean cartesianChosen = true;
    Boolean crossedThinChosen = false;
    Boolean crossedThickChosen = false;
    Boolean parallelChosen = false;
    Boolean triangularChosen = false;

    public static double spreadValue;
    public static double amplitudeValue;
    public static double deltaTimeValue;
    public static double densityValue;
    
    public static final double DEFAULTMASS = 1.0;
    public static final double[] defaultStops = {DEFAULTMASS};
    public static Distribution distributionValue = new Distribution(Distribution.Surface.UNIFORM, defaultStops);
    public static Arrangement arrangementValue;
    
    public double massOneDC = 1.0;
    public double massTwoDC = 1.0;

    Simulation simulation = new Simulation();

    public CreateNewDrumController(Stage stage) {
        this.stage = stage;
    }

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

    @FXML
    Button showLegend;

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
    Menu shapesMenu, massMenu, textureMenu, resetMenu, recordingMenu;

    @FXML
    Label spreadLabel;
    @FXML
    Label powerLabel;
    @FXML
    Label timeLabel;

    @FXML
    CheckBox displayCamera;

    @FXML
    BorderPane root;

    //Slider Number value 1
    int numSlider;

    //Slider number value 2
    int numSlider2;

    //Slider number value 3
    double numSlider3;

    SaveHandler currentSaveHandler;

    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void initialize() {
        showLegend.setVisible(false);
        disableSettings();
        menuDuringSettings();

        btnConfirm.setDisable(true);

        btnStopRecord.setDisable(true);
        btnStartRecord.setOnAction((event) -> {
            try {
                new SaveRecordingController(stage, this);
            } catch(IOException e) {}
        });
        btnStopRecord.setOnAction((event) -> {
            try {
                simulation.physics.endRecording();
                currentSaveHandler.upload();
                btnStopRecord.setDisable(true);
                btnStartRecord.setDisable(false);
            } catch(IOException e) {e.printStackTrace();}
        });
        stage.setOnCloseRequest((event) -> {
            if(!Objects.isNull(currentSaveHandler)) {
                btnStopRecord.fire();
            }
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
        });

        slider2.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            numSlider2 = (int) slider2.getValue();
            numLabel2.setText(Integer.toString(numSlider2) + "M");

            spreadValue = (double) newValue;
        });

        slider3.valueProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            numSlider3 = (double) slider3.getValue();
            numLabel3.setText(Double.toString(numSlider3) + "s");

            deltaTimeValue = (double) newValue;
        });

        spinner.setValueFactory(
                new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 170)
        );

        resetWave.setOnAction((event) -> {
            simulation.physics.resetWaves();
        });

        resetCamera.setOnAction((event) -> {
            simulation.physics.resetCamera();
        });
        
        displayCamera.setSelected(true);
        displayCamera.selectedProperty().addListener((observable, oldValue, newValue) -> {
            try {
                simulation.getCamX().setVisible(newValue);
                simulation.getCamY().setVisible(newValue);
                simulation.getCamZup().setVisible(newValue);
                simulation.getCamZdown().setVisible(newValue);
                simulation.getCameraCentre().setVisible(newValue);
            } catch(NullPointerException e) {}
        });
        displayCamera.setDisable(true);

    }

    void disableSettings() {
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

    void menuDuringSim() {
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
            try {
                simulation.physics.stopTimer();
            } catch(NullPointerException e) {

            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene2NewDream.fxml"));
            CreateNewDrumController mainController = new CreateNewDrumController(stage);
            loader.setController(mainController);
            root = loader.load();
            Scene scene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
            stage.setScene(scene);
            stage.setTitle("Drum Simulation.");
            stage.sizeToScene();
            stage.show();
        } catch (IOException ex) {}
    }

    @FXML
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void handleLoadSavedDrum(ActionEvent event) {
        try {
            new DownloadSaveController(stage, this);
        } catch(IOException e) {}
    }

    public void rectangleChosen(ActionEvent event) {
        label1.setVisible(true);
        textF1.setEditable(true);
        textF1.setVisible(true);

        label2.setVisible(true);
        textF2.setEditable(true);
        textF2.setVisible(true);

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
        try {
            UniformMassSettingController UC = new UniformMassSettingController(stage,this);
            this.UniformMassDChosen = true;
            this.HorizontalMassDChosen = false;
            this.VerticalMassDChosen = false;
            this.RadialMassDChosen = false;
        } catch (IOException ex) {
            
        }
    }

    public void handleHorizontalMassDChosen(ActionEvent event) {
        try {
            GradientMassSettingController GC = new GradientMassSettingController(stage,this);
            this.UniformMassDChosen = false;
            this.HorizontalMassDChosen = true;
            this.VerticalMassDChosen = false;
            this.RadialMassDChosen = false;
        } catch (IOException ex) {

        }
    }

    public void handleVerticalMassDChosen(ActionEvent event) {
        try {
            System.out.println("mass chosen");
            GradientMassSettingController GC = new GradientMassSettingController(stage,this);
            this.UniformMassDChosen = false;
            this.HorizontalMassDChosen = false;
            this.VerticalMassDChosen = true;
            this.RadialMassDChosen = false;
        } catch (IOException ex) {
            
        }
    }

    public void handleRadialMassDChosen(ActionEvent event) {
        try {
            GradientMassSettingController GC = new GradientMassSettingController(stage,this);
            this.UniformMassDChosen = false;
            this.HorizontalMassDChosen = false;
            this.VerticalMassDChosen = false;
            this.RadialMassDChosen = true;
        } catch (IOException ex) {
            
        }

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
        showLegend.setVisible(true);
        Alert invalidAlert = new Alert(Alert.AlertType.WARNING);
        try {
            if (cartesianChosen) {
                arrangementValue = CARTESIAN;
            } else if (crossedThinChosen) {
                arrangementValue = CROSSED_THIN;
            } else if (crossedThickChosen) {
                arrangementValue = CROSSED_THICK;
            } else if (parallelChosen) {
                arrangementValue = PARALLEL;
            } else if (triangularChosen) {
                arrangementValue = TRIANGULAR;
            }

            if (UniformMassDChosen) {
                double[] stops = new double[]{massOneDC};
                distributionValue = new Distribution(Distribution.Surface.UNIFORM, stops);
            } else if (HorizontalMassDChosen) {
                double[] stops = new double[]{massOneDC, massTwoDC};
                distributionValue = new Distribution(Distribution.Surface.HORIZONTAL_GRADIENT, stops);
            } else if (VerticalMassDChosen) {
                double[] stops = new double[]{massOneDC, massTwoDC};
                distributionValue = new Distribution(Distribution.Surface.VERTICAL_GRADIENT, stops);
            } else if (RadialMassDChosen) {
                double[] stops = new double[]{massOneDC, massTwoDC};
                distributionValue = new Distribution(Distribution.Surface.RADIAL_GRADIENT, stops);
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
                invalidAlert.setHeaderText("Error: no shape selected");
                invalidAlert.setContentText("Please select a shape before confirming.");
                invalidAlert.showAndWait();
            }
        } catch (NumberFormatException | NullPointerException e) {
                invalidAlert.setHeaderText("Error: invalid input");
                invalidAlert.setContentText("Please enter valid numbers before confirming");
                invalidAlert.showAndWait();
        } catch(ArithmeticException e) {
            invalidAlert.setHeaderText("Error: particle cap exceeded");
            invalidAlert.setContentText(e.getMessage());
            invalidAlert.showAndWait();
        }
    }

    public void createSquareDrum(int length) throws ArithmeticException {
        Formable formable = new SquareDrum(length);
        formable.checkCap();
        formable.setMassDistribution(distributionValue);
        formable.setArrangement(arrangementValue);

        simulation.getPhysics().stopTimer();
        setSimulation(new Simulation(formable));

        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            simulation.translate(event.getCode());
        });

        simulation.setCloseSim(stage);
        root.setCenter(simulation.getRoot());
        root.getCenter().toBack();
        btnConfirm.setVisible(false);
        simulation.physics.startTimer();
    }

    public void createRectangleDrum(int width, int length) throws ArithmeticException {
        Formable formable = new RectangleDrum(width, length);
        formable.checkCap();
        formable.setMassDistribution(distributionValue);
        formable.setArrangement(arrangementValue);

        simulation.getPhysics().stopTimer();
        setSimulation(new Simulation(formable));

        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            simulation.translate(event.getCode());
        });

        simulation.setCloseSim(stage);
        root.setCenter(simulation.getRoot());
        root.getCenter().toBack();
        btnConfirm.setVisible(false);
        simulation.physics.startTimer();
    }

    public void createParallelogramDrum(int width, int height, int angle) throws ArithmeticException {
        Formable formable = new ParallelogramDrum(width, height, angle);
        formable.checkCap();
        formable.setMassDistribution(distributionValue);
        formable.setArrangement(arrangementValue);

        simulation.getPhysics().stopTimer();
        setSimulation(new Simulation(formable));

        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            simulation.translate(event.getCode());
        });

        simulation.setCloseSim(stage);
        root.setCenter(simulation.getRoot());
        root.getCenter().toBack();
        btnConfirm.setVisible(false);
        simulation.physics.startTimer();
    }

    public void createTrapazoidDrum(int longBase, int shortBase, int height, int angle) throws ArithmeticException {
        Formable formable = new TrapezoidDrum(longBase, shortBase, height, angle);
        formable.checkCap();
        formable.setMassDistribution(distributionValue);
        formable.setArrangement(arrangementValue);

        simulation.getPhysics().stopTimer();
        setSimulation(new Simulation(formable));

        stage.addEventHandler(KeyEvent.KEY_PRESSED, (event) -> {
            simulation.translate(event.getCode());
        });

        simulation.setCloseSim(stage);
        root.setCenter(simulation.getRoot());
        root.getCenter().toBack();
        btnConfirm.setVisible(false);
        simulation.physics.startTimer();
    }

     public void legendSelected(ActionEvent event){

             Alert camInfo = new Alert(Alert.AlertType.INFORMATION);
                camInfo.setHeaderText("Legend for camera controls");
                camInfo.setContentText( "W/S: translation on green axis " + "\n" +
                        "A/D : translation on purple axis " + "\n"
                        + "X/C : rotates about purple axis " + "\n" +
                        "V/B : rotates about green axis" + "\n" +
                        " N/M : rotates about normal axis" + "\n" +
                        "J : zoom in" +"\n" + "K : zooms out");
                camInfo.showAndWait();
    }

    public Simulation getSimulation() {
        return this.simulation;
    }

    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
        this.simulation.setController(this);
    }
    
    public void setDisableDuringPlayBack(boolean disable) {
        slider3.setDisable(disable);
        slider2.setDisable(disable);
        slider.setDisable(disable);
        resetWave.setDisable(disable);
        recordingMenu.setDisable(disable);
        spreadLabel.setDisable(disable);
        powerLabel.setDisable(disable);
        timeLabel.setDisable(disable);
        numLabel.setDisable(disable);
        numLabel2.setDisable(disable);
        numLabel3.setDisable(disable);
    }

    public double getMassOneDC() {
        return this.massOneDC;
    }

    public void setMassOneDC(double massOneDC) {
        this.massOneDC = massOneDC;
    }

    public double getMassTwoDC() {
        return this.massTwoDC;
    }

    public void setMassTwoDC(double massTwoDC) {
        this.massTwoDC = massTwoDC;
    }
    
    public void enableCameraControl() {
        displayCamera.setDisable(false);
    }

}